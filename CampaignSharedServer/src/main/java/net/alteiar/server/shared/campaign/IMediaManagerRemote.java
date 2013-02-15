package net.alteiar.server.shared.campaign;

import java.rmi.RemoteException;
import java.util.HashMap;

import net.alteiar.images.TransfertImage;
import net.alteiar.server.shared.observer.IGUIDRemote;
import net.alteiar.server.shared.observer.campaign.IMediaObserverRemote;

public interface IMediaManagerRemote extends IGUIDRemote {

	// Listeners
	void addMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException;

	void removeMediaManagerListener(IMediaObserverRemote listener)
			throws RemoteException;

	Long addImage(TransfertImage image) throws RemoteException;

	TransfertImage getImage(Long guid) throws RemoteException;

	HashMap<Long, TransfertImage> getAllImages() throws RemoteException;
}
