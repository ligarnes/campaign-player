package net.alteiar.newversion.shared.chunk;

import java.util.Arrays;

import net.alteiar.newversion.shared.message.MessageCreateValue;
import net.alteiar.newversion.shared.message.MessageSplitEnd;
import net.alteiar.shared.UniqueID;

public class ChunkObjectSend {
	private final String classname;
	private UniqueID guid;
	private final byte[] data;

	private final int chunkSize;
	private final int chunkCount;

	public ChunkObjectSend(String classname, UniqueID guid, byte[] data,
			int chunkSize) {
		this.classname = classname;
		this.guid = guid;

		this.data = data;

		this.chunkSize = chunkSize;

		chunkCount = (int) Math.ceil(data.length / (double) chunkSize);
	}

	public void setGuid(UniqueID guid) {
		this.guid = guid;
	}

	public UniqueID getGuid() {
		return guid;
	}

	private byte[] getChunkByte(int num) {
		int begin = num * chunkSize;
		int end = Math.min(begin + chunkSize, data.length);

		return Arrays.copyOfRange(data, begin, end);
	}

	public boolean haveNext(int chunk) {
		return chunk < chunkCount;
	}

	public MessageCreateValue getChunk(int num) {
		return new MessageCreateValue(guid, getChunkByte(num));
	}

	public MessageSplitEnd getEndChunk() {
		return new MessageSplitEnd(guid, classname);
	}
}
