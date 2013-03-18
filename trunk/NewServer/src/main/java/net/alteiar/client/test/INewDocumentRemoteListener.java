package net.alteiar.client.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INewDocumentRemoteListener extends Remote {
	void beanValueChanged(String propertyName, Object newValue)
			throws RemoteException;

	void documentClosed();
}
