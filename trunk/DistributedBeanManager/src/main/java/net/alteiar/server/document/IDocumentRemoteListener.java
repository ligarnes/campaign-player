package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDocumentRemoteListener extends Remote {

	void beanValueChanged(String propertyName, Object newValue, long timestamp)
			throws RemoteException;

	void documentClosed() throws RemoteException;
}
