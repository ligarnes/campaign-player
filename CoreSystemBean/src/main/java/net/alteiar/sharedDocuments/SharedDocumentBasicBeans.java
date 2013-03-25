package net.alteiar.sharedDocuments;

import java.beans.PropertyVetoException;
import java.io.Serializable;

public abstract class SharedDocumentBasicBeans extends AuthorizationBasicBeans
		implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PROP_DOCUMENT_NAME_PROPERTY = "documentName";

	private String documentName;

	public SharedDocumentBasicBeans() {
		super();
	}

	public SharedDocumentBasicBeans(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		String oldValue = documentName;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName);
			this.documentName = documentName;
			propertyChangeSupport.firePropertyChange(
					PROP_DOCUMENT_NAME_PROPERTY, oldValue, documentName);
		} catch (PropertyVetoException e) {
			// TODO Remote refuse
			// e.printStackTrace();
		}
	}
}
