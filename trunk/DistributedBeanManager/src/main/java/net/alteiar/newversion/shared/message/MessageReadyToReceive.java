package net.alteiar.newversion.shared.message;

import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

public class MessageReadyToReceive implements IUniqueObject {
	private UniqueID guid;

	public MessageReadyToReceive() {
	}

	public MessageReadyToReceive(UniqueID guid) {
		this.guid = guid;
	}

	@Override
	public UniqueID getId() {
		return guid;
	}
}
