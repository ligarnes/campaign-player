package net.alteiar.server.shared.campaign;

import java.rmi.RemoteException;
import java.util.HashMap;

import net.alteiar.SerializableFile;
import net.alteiar.server.shared.observer.IGUIDRemote;
import net.alteiar.server.shared.observer.campaign.IMediaObserverRemote;

public interface IMediaManagerRemote extends IGUIDRemote {

	// Listeners
	void addMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException;

	void removeMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException;

	Long addImage(SerializableFile image) throws RemoteException;

	SerializableFile getImage(Long guid) throws RemoteException;

	HashMap<Long, SerializableFile> getAllImages() throws RemoteException;
}
