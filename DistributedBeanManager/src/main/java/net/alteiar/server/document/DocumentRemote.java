package net.alteiar.server.document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

import net.alteiar.client.DocumentClient;
import net.alteiar.client.bean.BeanEncapsulator;

public class DocumentRemote extends UnicastRemoteObject implements
		IDocumentRemote {
	private static final long serialVersionUID = 1L;

	private final DocumentPath path;
	private final BeanEncapsulator bean;

	private final HashSet<IDocumentRemoteListener> listeners;

	public DocumentRemote(BeanEncapsulator bean) throws RemoteException {
		super();
		this.path = new DocumentPath("", "");
		this.bean = bean;

		listeners = new HashSet<IDocumentRemoteListener>();
	}

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

	protected void notifyBeanChanged(String propertyName, Object newValue) {
		HashSet<IDocumentRemoteListener> copy = new HashSet<IDocumentRemoteListener>();
		synchronized (listeners) {
			copy = (HashSet<IDocumentRemoteListener>) listeners.clone();
		}

		for (IDocumentRemoteListener listener : copy) {
			// TODO need to multithread that
			try {
				listener.beanValueChanged(propertyName, newValue);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public DocumentClient buildProxy() throws RemoteException {
		return new DocumentClient(this);
	}
}
