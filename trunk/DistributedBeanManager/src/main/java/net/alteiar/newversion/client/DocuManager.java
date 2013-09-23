package net.alteiar.newversion.client;

import java.io.IOException;
import java.util.HashMap;

import net.alteiar.newversion.shared.SendingKey;
import net.alteiar.newversion.shared.chunk.ChunkObjectReceived;
import net.alteiar.newversion.shared.chunk.ChunkObjectSend;
import net.alteiar.newversion.shared.message.MessageCreateValue;
import net.alteiar.newversion.shared.message.MessageReadyToReceive;
import net.alteiar.newversion.shared.message.MessageSplitEnd;
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

		SendingKey key = new SendingKey(send.getGuid(), conn);

		if (!sendingObject.containsKey(key)) {
			sendingObject.put(key, encap);
			sendTCPMessage(conn, encap.getNextChunk());
		} else {
			// need to wait
			System.out.println("already sending " + key);
		}
	}

	private void sendTCPMessage(final Connection conn, final IUniqueObject obj) {

		ThreadPoolUtils.getClientPool().execute(new MyRunnable() {
			@Override
			public void run() {
				while ((conn.getTcpWriteBufferSize() + getMaxChunkSize()) >= bufferSize) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Logger.getLogger(getClass())
								.debug("Erreur durant l'attente du buffer d'envoie",
										e);
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
			conn.sendTCP(reply);
		} catch (Exception ex) {
			Logger.getLogger(getClass()).warn(
					"Erreur durant l'envoie d'un élément " + reply, ex);
		}
	}

	@Override
	public final void received(Connection conn, Object obj) {
		try {
			if (obj instanceof MessageReadyToReceive) {
				// When we are sending object we wait for ready message before
				// sending other
				IUniqueObject reply = messageReadyReceived(conn,
						(MessageReadyToReceive) obj);
				sendTCPMessage(conn, reply);

			} else if (obj instanceof MessageCreateValue) {
				MessageCreateValue msg = (MessageCreateValue) obj;
				IUniqueObject reply = messageObjectReceived(msg);
				sendTCPMessage(conn, reply);
			} else if (obj instanceof MessageSplitEnd) {
				messageObjectEndReceived((MessageSplitEnd) obj);
			} else if (obj instanceof RequestObject) {
				UniqueID id = ((RequestObject) obj).getGuid();
				ChunkObjectSend send = getObjectSend(id);

				if (send != null) {
					sendObject(conn, send);
				} else {
					System.out.println("request unknow object: " + id);
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(getClass()).warn(
					"Erreur lors de la reception de donnée " + obj, ex);
		}
	}

	private final IUniqueObject messageReadyReceived(Connection conn,
			MessageReadyToReceive obj) {
		IUniqueObject reply = null;
		Encaps sended = sendingObject.get(new SendingKey(obj.getId(), conn));

		if (sended == null) {
			System.out.println("impossible d'envoyer " + obj.getId());
		}
		if (sended.isFinish()) {
			reply = sended.getEndChunk();
			sendingObject.remove(new SendingKey(obj.getId(), conn));

			System.gc();
		} else {
			reply = sended.getNextChunk();
		}

		return reply;
	}

	private final MessageReadyToReceive messageObjectReceived(
			MessageCreateValue obj) {

		ChunkObjectReceived received = receivingObject.get(obj.getId());
		if (received == null) {
			received = new ChunkObjectReceived();
			receivingObject.put(obj.getId(), received);
		}

		byte[] bytes = obj.getBytes();
		received.addChunk(bytes);

		return new MessageReadyToReceive(obj.getId());
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
