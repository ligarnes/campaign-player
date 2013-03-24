package net.alteiar.client.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import net.alteiar.server.document.DocumentPath;

public abstract class BasicBeans implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PROP_DOCUMENT_PATH_PROPERTY = "documentPath";
	public static final String PROP_ID_PROPERTY = "id";

	protected final VetoableChangeSupport vetoableRemoteChangeSupport;
	protected final PropertyChangeSupport propertyChangeSupport;

	private static Long currentGUID = 0L;

	private static final Long generateNextGUID() {
		long guid = currentGUID;
		currentGUID++;
		return guid;
	}

	private Long id;
	private DocumentPath documentPath;

	public BasicBeans() {
		this.id = generateNextGUID();
		this.documentPath = new DocumentPath("", "");

		vetoableRemoteChangeSupport = new VetoableChangeSupport(this);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		DocumentPath oldValue = this.documentPath;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_ID_PROPERTY,
					oldValue, id);
			this.id = id;
			propertyChangeSupport.firePropertyChange(PROP_ID_PROPERTY,
					oldValue, id);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void setDocumentPath(DocumentPath path) {
		DocumentPath oldValue = this.documentPath;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_DOCUMENT_PATH_PROPERTY, oldValue, path);
			this.documentPath = path;
			propertyChangeSupport.firePropertyChange(
					PROP_DOCUMENT_PATH_PROPERTY, oldValue, path);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public DocumentPath getDocumentPath() {
		return this.documentPath;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoableRemoteChangeSupport.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoableRemoteChangeSupport.removeVetoableChangeListener(listener);
	}
}
