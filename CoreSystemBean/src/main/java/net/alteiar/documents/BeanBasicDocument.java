package net.alteiar.documents;

import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public abstract class BeanBasicDocument extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_PARENT_PROPERTY = "parent";
	public static final String PROP_DOCUMENT_NAME_PROPERTY = "documentName";

	@Element(required = false)
	private UniqueID parent;

	@Element
	private String documentName;

	public BeanBasicDocument() {

	}

	public BeanBasicDocument(UniqueID parent, String documentname) {
		this.parent = parent;
		this.documentName = documentname;
	}

	public abstract boolean isDirectory();

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		String oldValue = documentName;
		if (notifyRemote(PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName)) {
			this.documentName = documentName;
			notifyLocal(PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName);
		}
	}

	public UniqueID getParent() {
		return parent;
	}

	public void setParent(UniqueID parent) {
		UniqueID oldValue = this.parent;
		if (notifyRemote(PROP_PARENT_PROPERTY, oldValue, parent)) {
			this.parent = parent;
			notifyLocal(PROP_PARENT_PROPERTY, oldValue, parent);
		}
	}
}
