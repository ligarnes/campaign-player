package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.client.bean.BasicBean;

public interface IDocumentRemote extends Remote {

	void addDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException;

	void removeDocumentListener(IDocumentRemoteListener listener)
			throws RemoteException;

	String getFilename() throws RemoteException;

	void closeDocument() throws RemoteException;

	BasicBean getBean() throws RemoteException;

	void setBeanValue(String propertyName, Object newValue)
			throws RemoteException;
}
