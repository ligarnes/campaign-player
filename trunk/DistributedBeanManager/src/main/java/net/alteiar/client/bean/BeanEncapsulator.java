package net.alteiar.client.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import net.alteiar.shared.UniqueID;

public class BeanEncapsulator implements VetoableChangeListener {
	private final PropertyChangeSupport propertyChangeSupportRemote;
	private final BasicBean bean;

	private final ArrayList<BeanChange> changed;
	private transient HashMap<String, Long> propertyTimestamp;

	public BeanEncapsulator(BasicBean bean) {
		this.bean = bean;
		propertyChangeSupportRemote = new PropertyChangeSupport(this);
		propertyTimestamp = new HashMap<String, Long>();

		changed = new ArrayList<BeanChange>();
		this.bean.addVetoableChangeListener(this);
	}

	public BeanEncapsulator() {
		this.bean = null;
		propertyChangeSupportRemote = new PropertyChangeSupport(this);
		changed = new ArrayList<BeanChange>();
	}

	public UniqueID getId() {
		return this.bean.getId();
	}

	/**
	 * this method is call when the server notify a value change
	 * 
	 * @param propertyName
	 * @param newValue
	 */
	public void valueChange(String propertyName, Object newValue, Long timestamp) {
		Long previous = propertyTimestamp.get(propertyName);

		if (previous != null) {
			Timestamp prev = new Timestamp(previous);
			Timestamp current = new Timestamp(timestamp);
			if (!prev.after(current)) {
				System.out.println("change inversion");
				return;
			}
		}

		synchronized (changed) {
			changed.add(new BeanChange(propertyName, newValue));
		}

		try {
			Boolean isInvoke = false;
			BeanInfo info = Introspector.getBeanInfo(bean.getClass());
			for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
				if (descriptor.getName().equals(propertyName)) {
					descriptor.getWriteMethod().invoke(bean, newValue);
					isInvoke = true;
				}
			}

			if (!isInvoke) {
				for (MethodDescriptor descriptor : info.getMethodDescriptors()) {
					if (descriptor.getName().equals(propertyName)) {
						descriptor.getMethod().invoke(bean, newValue);
					}
				}
			}
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

	/**
	 * this method is call when the bean we encapsulate change
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		BeanChange changeNotification = new BeanChange(evt);
		if (changed.contains(changeNotification)) {
			synchronized (changed) {
				changed.remove(changeNotification);
			}
		} else {
			propertyChangeSupportRemote.firePropertyChange(evt);
			throw new PropertyVetoException("remote change", evt);
		}
	}

	public BasicBean getBean() {
		return this.bean;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupportRemote.removePropertyChangeListener(listener);
	}

	public void beanRemoved() {
		bean.removeVetoableChangeListener(this);
		bean.beanRemoved();
	}

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
}
