package net.alteiar.newversion.shared.request;

import net.alteiar.shared.UniqueID;

public class RequestDelete {
	private UniqueID bean;

	public RequestDelete() {

	}

	public RequestDelete(UniqueID bean) {
		this.bean = bean;
	}

	public UniqueID getBeanId() {
		return bean;
	}
}
