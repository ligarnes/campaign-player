package net.alteiar.newversion.shared.request;

import net.alteiar.shared.UniqueID;

public class RequestObject {
	private UniqueID guid;

	public RequestObject() {

	}

	public RequestObject(UniqueID guid) {
		this.guid = guid;
	}

	public UniqueID getGuid() {
		return guid;
	}
}
