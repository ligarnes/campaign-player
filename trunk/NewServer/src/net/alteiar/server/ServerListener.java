package net.alteiar.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerListener extends Remote {
	void documentAdded(Long guid) throws RemoteException;

	void documentRemoved(Long guid) throws RemoteException;
}
