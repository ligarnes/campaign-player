package net.alteiar.client.test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

public abstract class BasicBeans implements Serializable {
	private static final long serialVersionUID = 1L;

	protected final PropertyChangeSupport propertyChangeSupportRemote;
	protected final VetoableChangeSupport vetoableChangeSupport;

	protected final PropertyChangeSupport propertyChangeSupportClient;

	public BasicBeans() {
		propertyChangeSupportRemote = new PropertyChangeSupport(this);
		vetoableChangeSupport = new VetoableChangeSupport(this);

		propertyChangeSupportClient = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.removePropertyChangeListener(listener);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoableChangeSupport.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoableChangeSupport.removeVetoableChangeListener(listener);
	}
}
