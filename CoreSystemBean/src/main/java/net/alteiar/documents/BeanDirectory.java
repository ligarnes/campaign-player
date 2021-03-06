package net.alteiar.documents;

import java.util.HashSet;

import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.ElementList;

public class BeanDirectory extends BeanBasicDocument {
	private static final long serialVersionUID = 1L;

	public static final String PROP_DOCUMENTS_PROPERTY = "documents";

	public static final String METH_ADD_DOCUMENT_METHOD = "addDocumentRemote";
	public static final String METH_REMOVE_DOCUMENT_METHOD = "removeDocumentRemote";

	@ElementList
	private HashSet<UniqueID> documents;

	public BeanDirectory() {
	}

	private static UniqueID getDirectoryId(BeanDirectory parent) {
		UniqueID dirId = null;
		if (parent != null) {
			dirId = parent.getId();
		}
		return dirId;
	}

	public BeanDirectory(BeanDirectory parent, String name) {
		this(getDirectoryId(parent), name);
	}

	public BeanDirectory(UniqueID parent, String name) {
		super(parent, name);
		documents = new HashSet<>();
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	public HashSet<UniqueID> getDocuments() {
		return (HashSet<UniqueID>) documents.clone();
	}

	public void setDocuments(HashSet<UniqueID> documents) {
		HashSet<UniqueID> oldValue = this.documents;
		if (notifyRemote(PROP_DOCUMENTS_PROPERTY, oldValue, documents)) {
			this.documents = documents;
			notifyLocal(PROP_DOCUMENTS_PROPERTY, oldValue, documents);
		}
	}

	public void addDocument(BeanBasicDocument document) {
		this.addDocumentRemote(document.getId());
	}

	public void addDocumentRemote(UniqueID document) {
		if (!documents.contains(document) && !document.equals(getId())) {
			if (notifyRemote(METH_ADD_DOCUMENT_METHOD, null, document)) {
				this.documents.add(document);
				notifyLocal(METH_ADD_DOCUMENT_METHOD, null, document);
			}
		}
	}

	public void removeDocument(BeanBasicDocument document) {
		this.removeDocumentRemote(document.getId());
	}

	public void removeDocumentRemote(UniqueID document) {
		if (documents.contains(document)) {
			if (notifyRemote(METH_REMOVE_DOCUMENT_METHOD, null, document)) {
				this.documents.remove(document);
				notifyLocal(METH_REMOVE_DOCUMENT_METHOD, null, document);
			}
		}
	}

	@Override
	public String toString() {
		return "BeanDirectory [documentName=" + getDocumentName()
				+ ", documents=" + documents + "]";
	}
}
