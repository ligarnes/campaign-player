package net.alteiar.client.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public abstract class BasicBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PROP_ID_PROPERTY = "id";

	private final VetoableChangeSupport vetoableRemoteChangeSupport;
	protected final PropertyChangeSupport propertyChangeSupport;

	@Element
	private UniqueID id;

	public BasicBean() {
		this.id = new UniqueID();

		vetoableRemoteChangeSupport = new VetoableChangeSupport(this);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	/**
	 * The unique id of the bean
	 * 
	 * @return the unique id
	 */
	public UniqueID getId() {
		return this.id;
	}

	/**
	 * this method must not be called, the id must'nt change
	 * 
	 * @param id
	 */
	public void setId(UniqueID id) {
		UniqueID oldValue = this.id;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_ID_PROPERTY,
					oldValue, id);
			this.id = id;
			propertyChangeSupport.firePropertyChange(PROP_ID_PROPERTY,
					oldValue, id);
		} catch (PropertyVetoException e) {
		}
	}

	public void beanRemoved() {
	}

	/**
	 * Add a listener to all change that happen on the bean
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Add a listener to all change that happen on a specific property of the
	 * bean
	 * 
	 * @param propertyName
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Remove a listener from the bean
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	protected Boolean notifyRemote(String propertyName, Object oldValue,
			Object newValue) {

		Boolean authorizedChanged = true;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(propertyName,
					oldValue, newValue);
		} catch (PropertyVetoException e) {
			authorizedChanged = false;
		}

		return authorizedChanged;
	}

	/**
	 * Add a vetoable change listener, should'nt be use except by the framework
	 * 
	 * @param listener
	 */
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoableRemoteChangeSupport.addVetoableChangeListener(listener);
	}

	/**
	 * Remove a vetoable change listener, should'nt be use except by the
	 * framework
	 * 
	 * @param listener
	 */
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
		BasicBean other = (BasicBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
