package net.alteiar.server.document;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDocumentListener extends Remote {
	public void documentClosed() throws RemoteException;
}
