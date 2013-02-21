package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDocumentRemote extends Remote {
	DocumentClient<?> buildProxy() throws RemoteException;

	DocumentPath getPath() throws RemoteException;

	void closeDocument() throws RemoteException;

	void addDocumentListener(IDocumentListener listener) throws RemoteException;

	void removeDocumentListener(IDocumentListener listener)
			throws RemoteException;
}
