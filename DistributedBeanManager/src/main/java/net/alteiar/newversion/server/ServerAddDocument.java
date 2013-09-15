package net.alteiar.newversion.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.alteiar.newversion.client.DocuManager;
import net.alteiar.newversion.server.kryo.NetworkKryoInit;
import net.alteiar.newversion.shared.SendingKey;
import net.alteiar.newversion.shared.chunk.ChunkSendFactory;
import net.alteiar.newversion.shared.message.MessageCreateValue;
import net.alteiar.newversion.shared.message.MessageReadyToReceive;
import net.alteiar.newversion.shared.message.MessageSplitEnd;
import net.alteiar.newversion.shared.request.RequestObject;
import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerAddDocument extends Listener {
	private final ChunkSendFactory factory;
	private final Server server;

	private final int bufferSize;
	private final HashMap<SendingKey, Encaps> sendingUsers;
	private final HashMap<UniqueID, ObjectSender> sendingObjets;

	private final HashMap<UniqueID, Connection> pendingRequest;

	public ServerAddDocument(int portTcp) throws IOException {
		sendingUsers = new HashMap<>();
		sendingObjets = new HashMap<>();

		pendingRequest = new HashMap<>();

		bufferSize = DocuManager.BYTE_TO_1MO * 10;
		server = new Server(bufferSize, bufferSize);
		server.start();
		server.bind(portTcp, portTcp + 1);

		NetworkKryoInit init = new NetworkKryoInit();
		init.registerKryo(server.getKryo());

		server.addListener(this);

		factory = new ChunkSendFactory(server.getKryo());
	}

	public void stop() {
		server.close();
		server.stop();
	}

	private class ObjectSender {
		private final ArrayList<IUniqueObject> toSend;
		private boolean fullyReceived;

		public ObjectSender() {
			this.toSend = new ArrayList<>();
			fullyReceived = false;
		}

		public void fullyReceived() {
			fullyReceived = true;
		}

		public boolean isFullyReceived() {
			return fullyReceived;
		}

		public int getChunkCount() {
			return toSend.size();
		}

		public void addChunk(IUniqueObject obj) {
			synchronized (toSend) {
				toSend.add(obj);
				toSend.notifyAll();
			}
		}

		public IUniqueObject getChunk(int currentChunk) {
			if (currentChunk >= toSend.size()) {
				try {
					synchronized (toSend) {
						toSend.wait();
					}
				} catch (InterruptedException e) {
					Logger.getLogger(getClass()).warn("Problem with wait", e);
				}
			}

			return toSend.get(currentChunk);
		}

	}

	private class Encaps {
		private final ObjectSender toSend;
		private int currentChunk;

		public Encaps(ObjectSender toSend) {
			this.toSend = toSend;
			currentChunk = 0;

		}

		public boolean isFinish() {
			return (toSend.isFullyReceived())
					&& (currentChunk >= toSend.getChunkCount());
		}

		public IUniqueObject getNextChunk() {
			IUniqueObject next = toSend.getChunk(currentChunk);
			currentChunk++;

			return next;
		}
	}

	protected void sendObject(Connection conn, UniqueID guid, ObjectSender send) {
		Encaps obj = new Encaps(send);
		sendingUsers.put(new SendingKey(guid, conn), obj);

		sendTCPMessage(conn, obj);
	}

	private int getMaxChunkSize() {
		return factory.getChunkSize();
	}

	private void sendTCPMessage(final Connection conn, final Encaps send) {
		ThreadPoolUtils.getServerPool().execute(new MyRunnable() {
			@Override
			public void run() {
				IUniqueObject obj = send.getNextChunk();

				realSend(conn, obj);
			}

			@Override
			public String getTaskName() {
				return "Send messages";
			}
		});
	}

	private void realSend(Connection conn, IUniqueObject reply) {

		synchronized (conn) {
			while ((conn.getTcpWriteBufferSize() + getMaxChunkSize()) >= bufferSize) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Logger.getLogger(getClass()).warn(
							"Interruption when sleeping", e);
				}
			}

			try {
				conn.sendTCP(reply);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void connected(Connection conn) {
		conn.updateReturnTripTime();
	}

	@Override
	public final void received(Connection conn, Object obj) {

		try {
			if (obj instanceof MessageReadyToReceive) {
				Encaps reply = messageReadyReceived(conn,
						(MessageReadyToReceive) obj);
				sendTCPMessage(conn, reply);
			} else if (obj instanceof MessageCreateValue) {
				Object reply = messageObjectReceived((MessageCreateValue) obj);
				conn.sendTCP(reply);
			} else if (obj instanceof MessageSplitEnd) {
				messageObjectEndReceived((MessageSplitEnd) obj);
			} else if (obj instanceof RequestObject) {
				Connection dest = null;

				RequestObject request = (RequestObject) obj;
				pendingRequest.put(request.getGuid(), conn);

				int min = Integer.MAX_VALUE;
				for (Connection connDest : server.getConnections()) {

					if (conn.getID() != connDest.getID()
							&& connDest.getReturnTripTime() < min) {
						min = connDest.getReturnTripTime();
						dest = connDest;
					}
				}

				dest.sendTCP(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	private final Encaps messageReadyReceived(Connection conn,
			MessageReadyToReceive obj) {
		Encaps sended = sendingUsers.get(new SendingKey(obj.getId(), conn));

		if (sended.isFinish()) {
			sendingUsers.remove(new SendingKey(obj.getId(), conn));

			System.gc();
		}

		return sended;
	}

	private final MessageReadyToReceive messageObjectReceived(
			MessageCreateValue obj) {
		ObjectSender encaps = sendingObjets.get(obj.getId());

		if (encaps == null) {
			encaps = new ObjectSender();

			sendingObjets.put(obj.getId(), encaps);

			Connection dest = pendingRequest.get(obj.getId());
			if (dest != null) {
				// The object we received is for a specific player
				sendObject(dest, obj.getId(), encaps);
			} else {
				// The object we received is a new one, every one need to get it
				for (Connection conn : server.getConnections()) {
					sendObject(conn, obj.getId(), encaps);
				}
			}
		}
		encaps.addChunk(obj);

		return new MessageReadyToReceive(obj.getId());
	}

	private final void messageObjectEndReceived(MessageSplitEnd obj) {
		ObjectSender encaps = sendingObjets.get(obj.getId());

		encaps.addChunk(obj);
		encaps.fullyReceived();

		sendingObjets.remove(obj.getId());
	}
}
