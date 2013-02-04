package net.alteiar.server.shared.observer.campaign;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMediaObserverRemote extends Remote {
	void mediaAdded(Long added) throws RemoteException;

	void mediaRemoved(Long guid) throws RemoteException;
}
