package net.alteiar.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.shared.UniqueID;

public interface ServerListener extends Remote {
	void documentAdded(UniqueID guid) throws RemoteException;

	void documentRemoved(UniqueID guid) throws RemoteException;
}
