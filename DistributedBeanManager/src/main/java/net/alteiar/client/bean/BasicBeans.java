package net.alteiar.client.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

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

	public BasicBeans() {
		this.id = generateNextGUID();

		vetoableRemoteChangeSupport = new VetoableChangeSupport(this);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		Long oldValue = this.id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicBeans other = (BasicBeans) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
