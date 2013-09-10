package net.alteiar.newversion.client;

import java.io.IOException;
import java.util.HashMap;

import net.alteiar.newversion.shared.SendingKey;
import net.alteiar.newversion.shared.chunk.ChunkObjectReceived;
import net.alteiar.newversion.shared.chunk.ChunkObjectSend;
import net.alteiar.newversion.shared.message.MessageCreateValue;
import net.alteiar.newversion.shared.message.MessageSplitEnd;
import net.alteiar.newversion.shared.message.MessageSplitReady;
import net.alteiar.newversion.shared.request.RequestObject;
import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public abstract class DocuManager extends Listener {
	public static final int BYTE_TO_1MO = 1024000;

	private final int bufferSize;
	private final HashMap<SendingKey, Encaps> sendingObject;
	private final HashMap<UniqueID, ChunkObjectReceived> receivingObject;

	public DocuManager(int bufferSize) throws IOException {
		this.bufferSize = bufferSize;

		sendingObject = new HashMap<>();
		receivingObject = new HashMap<>();
	}

	private class Encaps {
		private final ChunkObjectSend send;
		private int currentChunk;

		public Encaps(ChunkObjectSend send) {
			this.send = send;
			currentChunk = 0;
		}

		public boolean isFinish() {
			return !send.haveNext(currentChunk);
		}

		public IUniqueObject getEndChunk() {
			return this.send.getEndChunk();
		}

		public IUniqueObject getNextChunk() {
			IUniqueObject chunk = this.send.getChunk(currentChunk);
			currentChunk++;

			return chunk;
		}
	}

	protected void sendObject(Connection conn, ChunkObjectSend send) {
		Encaps encap = new Encaps(send);
		sendingObject.put(new SendingKey(send.getGuid(), conn), encap);

		sendTCPMessage(conn, encap.getNextChunk());
	}

	private void sendTCPMessage(final Connection conn, final IUniqueObject obj) {

		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				while ((conn.getTcpWriteBufferSize() + getMaxChunkSize()) >= bufferSize) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Logger.getLogger(getClass()).debug(
								"Erreur durant l'attente", e);
					}
				}

				realSend(conn, obj);
			}

			@Override
			public String getTaskName() {
				return "Sending message";
			}
		});
	}

	private synchronized void realSend(Connection conn, IUniqueObject reply) {
		try {
			int size = conn.sendTCP(reply);
		} catch (Exception ex) {
			Logger.getLogger(getClass()).warn(
					"Erreur durant l'envoie d'un élément", ex);
		}
		/*
		 * System.out.println("sending " + reply.getId() + " " +
		 * humanReadableByteCount(size, true) + " to " +
		 * conn.getRemoteAddressTCP());
		 * 
		 * System.out.println("buffer Size: " +
		 * humanReadableByteCount(conn.getTcpWriteBufferSize(), true) + "/" +
		 * humanReadableByteCount(bufferSize, true));
		 * 
		 * Runtime runtime = Runtime.getRuntime(); Integer byteToMega = 1048576;
		 * 
		 * System.out.println("used : " + ((runtime.totalMemory() -
		 * runtime.freeMemory()) / byteToMega) + "mb ");
		 * System.out.println("  committed : " + (runtime.totalMemory() /
		 * byteToMega) + "mb ");
		 */
	}

	@Override
	public final void received(Connection conn, Object obj) {
		try {
			if (obj instanceof MessageSplitReady) {
				// When we are sending object we wait for ready message before
				// sending other
				IUniqueObject reply = messageReadyReceived(conn,
						(MessageSplitReady) obj);
				sendTCPMessage(conn, reply);

			} else if (obj instanceof MessageCreateValue) {
				IUniqueObject reply = messageObjectReceived((MessageCreateValue) obj);
				sendTCPMessage(conn, reply);
			} else if (obj instanceof MessageSplitEnd) {
				messageObjectEndReceived((MessageSplitEnd) obj);
			} else if (obj instanceof RequestObject) {
				UniqueID id = ((RequestObject) obj).getGuid();
				ChunkObjectSend send = getObjectSend(id);
				sendObject(conn, send);
			}
		} catch (Exception ex) {
			Logger.getLogger(getClass()).warn(
					"Erreur lors de la reception de donnée", ex);
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

	private final IUniqueObject messageReadyReceived(Connection conn,
			MessageSplitReady obj) {
		IUniqueObject reply = null;
		Encaps sended = sendingObject.get(new SendingKey(obj.getId(), conn));

		if (sended == null) {
			System.out.println("impossible d'envoyer " + obj.getId());
		}
		if (sended.isFinish()) {
			System.out.println("envoie terminer " + obj.getId());
			reply = sended.getEndChunk();
			sendingObject.remove(new SendingKey(obj.getId(), conn));

			System.gc();
		} else {
			reply = sended.getNextChunk();
		}

		return reply;
	}

	private final MessageSplitReady messageObjectReceived(MessageCreateValue obj) {
		ChunkObjectReceived received = receivingObject.get(obj.getId());
		if (received == null) {
			received = new ChunkObjectReceived();
			receivingObject.put(obj.getId(), received);
		}

		byte[] bytes = obj.getBytes();
		received.addChunk(bytes);

		return new MessageSplitReady(obj.getId());
	}

	private final void messageObjectEndReceived(MessageSplitEnd obj) {
		ChunkObjectReceived received = receivingObject.remove(obj.getId());

		String classname = obj.getClassname();
		received.setClassname(classname);

		IUniqueObject bean = received.getBean(getKryo());

		objectReceived(bean);
	}

	protected abstract ChunkObjectSend getObjectSend(UniqueID id);

	protected abstract int getMaxChunkSize();

	protected abstract <E extends IUniqueObject> void objectReceived(E bean);

	protected abstract Kryo getKryo();

	protected abstract void stop();

}
