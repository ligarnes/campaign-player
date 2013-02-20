package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class IDocumentListener extends UnicastRemoteObject implements
		Remote {
	private static final long serialVersionUID = 1L;

	protected IDocumentListener() throws RemoteException {
		super();
	}

	public abstract void documentClosed() throws RemoteException;
}
