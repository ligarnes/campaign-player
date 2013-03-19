package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.client.DocumentClient;
import net.alteiar.client.bean.BeanEncapsulator;

public interface IDocumentRemote extends Remote {
	DocumentClient buildProxy() throws RemoteException;

	void addDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException;

	void removeDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException;

	DocumentPath getPath() throws RemoteException;

	void closeDocument() throws RemoteException;

	BeanEncapsulator getBean() throws RemoteException;

	public void setBeanValue(String propertyName, Object newValue)
			throws RemoteException;
}
