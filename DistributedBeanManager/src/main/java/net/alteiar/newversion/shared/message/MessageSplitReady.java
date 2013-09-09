package net.alteiar.newversion.shared.message;

import net.alteiar.shared.IUniqueObject;
import net.alteiar.shared.UniqueID;

public class MessageSplitReady implements IUniqueObject {
	private UniqueID guid;

	public MessageSplitReady() {
	}

	public MessageSplitReady(UniqueID guid) {
		this.guid = guid;
	}

	@Override
	public UniqueID getId() {
		return guid;
	}
}
