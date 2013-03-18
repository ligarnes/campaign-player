package net.alteiar.client.test.bean;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BeanEncapsulator implements Serializable, VetoableChangeListener {

	private final PropertyChangeSupport propertyChangeSupportRemote;

	private final BasicBeans bean;
	private final ArrayList<BeanChange> changed; 

	private class BeanChange {
		private final String method;
		private final Object newValue;

		public BeanChange(String method, Object newValue) {
			this.method = method;
			this.newValue = newValue;
		}

		public BeanChange(PropertyChangeEvent event) {
			this.method = event.getPropertyName();
			this.newValue = event.getNewValue();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((method == null) ? 0 : method.hashCode());
			result = prime * result
					+ ((newValue == null) ? 0 : newValue.hashCode());
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
			BeanChange other = (BeanChange) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (method == null) {
				if (other.method != null)
					return false;
			} else if (!method.equals(other.method))
				return false;
			if (newValue == null) {
				if (other.newValue != null)
					return false;
			} else if (!newValue.equals(other.newValue))
				return false;
			return true;
		}

		private BeanEncapsulator getOuterType() {
			return BeanEncapsulator.this;
		}
	}

	public BeanEncapsulator(BasicBeans bean) {
		this.bean = bean;
		propertyChangeSupportRemote = new PropertyChangeSupport(this);

		changed = new ArrayList<>();
		this.bean.addVetoableChangeListener(this);
	}

	public void valueChange(String propertyName, Object newValue) {
		synchronized (changed) {
			changed.add(new BeanChange(propertyName, newValue));
		}
		Method setter;
		try {
			setter = new PropertyDescriptor(propertyName, bean.getClass())
					.getWriteMethod();
			setter.invoke(bean, newValue);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		BeanChange changeNotification = new BeanChange(evt);
		if (!changed.contains(changeNotification)) {
			synchronized (changed) {
				changed.remove(changeNotification);
			}
		} else {
			propertyChangeSupportRemote.firePropertyChange(evt);
			throw new PropertyVetoException("need remoteChange", evt);
		}
	}

	public BasicBeans getBean() {
		return this.bean;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.removePropertyChangeListener(listener);
	}
}
