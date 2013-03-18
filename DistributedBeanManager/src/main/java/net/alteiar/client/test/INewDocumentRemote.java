package net.alteiar.client.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentPath;

public interface INewDocumentRemote extends Remote {
	NewDocumentClient buildProxy() throws RemoteException;

	void addDocumentListener(INewDocumentRemoteListener listener)
			throws RemoteException;

	void removeDocumentListener(INewDocumentRemoteListener listener)
			throws RemoteException;

	DocumentPath getPath() throws RemoteException;

	void closeDocument() throws RemoteException;

	BeanEncapsulator getBean() throws RemoteException;

	public void setBeanValue(String propertyName, Object newValue)
			throws RemoteException;
}
