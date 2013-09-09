package net.alteiar.newversion.shared.message;

import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

public class MessageCreateValue implements IUniqueObject {
	private UniqueID guid;
	private byte[] values;

	public MessageCreateValue() {

	}

	public MessageCreateValue(UniqueID guid, byte[] values) {
		this.guid = guid;
		this.values = values;
	}

	@Override
	public UniqueID getId() {
		return guid;
	}

	public byte[] getBytes() {
		return values;
	}
}
