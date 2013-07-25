package net.alteiar.server.document;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashSet;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.thread.ThreadPoolServer;

import org.apache.log4j.Logger;

public class DocumentRemote extends UnicastRemoteObject implements
		IDocumentRemote {
	private static final long serialVersionUID = 1L;

	private final BeanEncapsulator beanEncapsulator;

	private final String filename;

	private final HashSet<IDocumentRemoteListener> listeners;

	public DocumentRemote(BasicBean bean) throws RemoteException {
		super();
		this.beanEncapsulator = new BeanEncapsulator(bean);
		this.filename = DocumentIO.validateFilename(bean.getId().toString());
		listeners = new HashSet<IDocumentRemoteListener>();
	}

	@Override
	public BasicBean getBean() {
		return beanEncapsulator.getBean();
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void setBeanValue(String propertyName, Serializable newValue)
			throws RemoteException {
		Long timestamp = new Date().getTime();
		beanEncapsulator.valueChange(propertyName, newValue, timestamp);
		notifyBeanChanged(propertyName, newValue, timestamp);
	}

	@Override
	public void closeDocument() throws RemoteException {
		notifyDocumentClosed();
	}

	// ////////////////// DOCUMENT LISTENER METHODS ///////////////////////
	@Override
	public void addDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected HashSet<IDocumentRemoteListener> getDocumentListeners() {
		HashSet<IDocumentRemoteListener> copy = new HashSet<IDocumentRemoteListener>();
		synchronized (listeners) {
			copy = (HashSet<IDocumentRemoteListener>) listeners.clone();
		}
		return copy;
	}

	protected void notifyBeanChanged(final String propertyName,
			final Object newValue, final long timestamp) {
		for (final IDocumentRemoteListener listener : getDocumentListeners()) {
			ThreadPoolServer.execute(new Runnable() {
				@Override
				public void run() {
					try {
						listener.beanValueChanged(propertyName, newValue,
								timestamp);
					} catch (RemoteException e) {
						Logger.getLogger(getClass())
								.error("Connexion perdu", e);
					}
				}
			});
		}
	}

	protected void notifyDocumentClosed() {
		for (final IDocumentRemoteListener listener : getDocumentListeners()) {
			if (listener != null) {
				ThreadPoolServer.execute(new Runnable() {
					@Override
					public void run() {
						try {
							listener.documentClosed();
						} catch (RemoteException e) {
							Logger.getLogger(getClass()).error(
									"Connexion perdu", e);
						}
					}
				});
			}
		}
	}

	// ////////////// EQUALS AND HASHCODE ///////////////
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((beanEncapsulator == null) ? 0 : beanEncapsulator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentRemote other = (DocumentRemote) obj;
		if (beanEncapsulator == null) {
			if (other.beanEncapsulator != null)
				return false;
		} else if (!beanEncapsulator.equals(other.beanEncapsulator))
			return false;
		return true;
	}
}
