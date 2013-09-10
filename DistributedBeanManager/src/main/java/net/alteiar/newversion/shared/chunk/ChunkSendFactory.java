package net.alteiar.newversion.shared.chunk;

import java.io.ByteArrayOutputStream;

import net.alteiar.shared.IUniqueObject;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class ChunkSendFactory {

	private final Kryo kryo;
	private final int chunkSize;

	public ChunkSendFactory(Kryo kryo) {
		this(kryo, 524288);
	}

	public ChunkSendFactory(Kryo kryo, int chunkSize) {
		this.kryo = kryo;
		this.chunkSize = chunkSize;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public synchronized ChunkObjectSend generateMessages(IUniqueObject bean) {
		ChunkObjectSend msg = null;
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Output output = new Output(stream);
			kryo.writeObject(output, bean);
			output.close(); // Also calls output.flush()

			// Serialization done, get bytes
			byte[] buffer = stream.toByteArray();
			msg = new ChunkObjectSend(bean.getClass().getName(), bean.getId(),
					buffer, chunkSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}
}
