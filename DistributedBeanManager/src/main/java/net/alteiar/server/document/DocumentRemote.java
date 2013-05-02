package net.alteiar.server.document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

import net.alteiar.client.DocumentClient;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.server.ServerDocuments;

public class DocumentRemote extends UnicastRemoteObject implements
		IDocumentRemote {
	private static final long serialVersionUID = 1L;

	private final BeanEncapsulator bean;
	private final DocumentPath path;

	private final HashSet<IDocumentRemoteListener> listeners;

	public DocumentRemote(DocumentPath path, BeanEncapsulator bean)
			throws RemoteException {
		super();
		this.bean = bean;
		this.path = path;
		listeners = new HashSet<IDocumentRemoteListener>();
	}

	@Override
	public BeanEncapsulator getBean() {
		return bean;
	}

	@Override
	public DocumentPath getPath() {
		return path;
	}

	@Override
	public void setBeanValue(String propertyName, Object newValue)
			throws RemoteException {
		bean.valueChange(propertyName, newValue);
		notifyBeanChanged(propertyName, newValue);
	}

	@Override
	public void closeDocument() throws RemoteException {
		notifyDocumentClosed();
	}

	@Override
	public DocumentClient buildProxy() throws RemoteException {
		return new DocumentClient(this);
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
			final Object newValue) {
		for (final IDocumentRemoteListener listener : getDocumentListeners()) {
			ServerDocuments.SERVER_THREAD_POOL.execute(new Runnable() {
				@Override
				public void run() {
					try {
						listener.beanValueChanged(propertyName, newValue);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	protected void notifyDocumentClosed() {
		for (final IDocumentRemoteListener listener : getDocumentListeners()) {
			if (listener != null) {
				ServerDocuments.SERVER_THREAD_POOL.execute(new Runnable() {
					@Override
					public void run() {
						try {
							listener.documentClosed();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
		result = prime * result + ((bean == null) ? 0 : bean.hashCode());
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
		if (bean == null) {
			if (other.bean != null)
				return false;
		} else if (!bean.equals(other.bean))
			return false;
		return true;
	}
}
