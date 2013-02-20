package net.alteiar.server.document;

import java.rmi.RemoteException;

import net.alteiar.client.DocumentClient;
import net.alteiar.server.IGUIDRemote;

public interface IDocumentRemote extends IGUIDRemote {
	DocumentClient<?> buildProxy() throws RemoteException;

	void closeDocument() throws RemoteException;

	void addDocumentListener(IDocumentListener listener) throws RemoteException;

	void removeDocumentListener(IDocumentListener listener) throws RemoteException;
}
