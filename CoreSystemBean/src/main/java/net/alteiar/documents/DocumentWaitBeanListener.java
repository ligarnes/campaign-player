package net.alteiar.documents;

import net.alteiar.WaitBeanListener;
import net.alteiar.shared.UniqueID;

public abstract class DocumentWaitBeanListener implements WaitBeanListener {

	private UniqueID id;

	public DocumentWaitBeanListener() {
	}

	public void setBeanId(UniqueID id) {
		this.id = id;
	}

	@Override
	public UniqueID getBeanId() {
		return id;
	}
}
