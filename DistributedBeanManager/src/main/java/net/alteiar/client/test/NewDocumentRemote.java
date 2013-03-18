package net.alteiar.client.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

import net.alteiar.server.document.DocumentPath;

public class NewDocumentRemote extends UnicastRemoteObject implements
		INewDocumentRemote {
	private static final long serialVersionUID = 1L;

	private final DocumentPath path;
	private final BeanEncapsulator bean;

	private final HashSet<INewDocumentRemoteListener> listeners;

	public NewDocumentRemote(BeanEncapsulator bean) throws RemoteException {
		super();
		this.path = new DocumentPath("", "");
		this.bean = bean;

		listeners = new HashSet<INewDocumentRemoteListener>();
	}

	@Override
	public void addDocumentListener(INewDocumentRemoteListener listener)
			throws RemoteException {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeDocumentListener(INewDocumentRemoteListener listener)
			throws RemoteException {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	protected void notifyBeanChanged(String propertyName, Object newValue) {
		HashSet<INewDocumentRemoteListener> copy = new HashSet<INewDocumentRemoteListener>();
		synchronized (listeners) {
			copy = (HashSet<INewDocumentRemoteListener>) listeners.clone();
		}

		for (INewDocumentRemoteListener listener : copy) {
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
		NewDocumentRemote other = (NewDocumentRemote) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public NewDocumentClient buildProxy() throws RemoteException {
		return new NewDocumentClient(this);
	}
}
