package net.alteiar.server.shared.campaign;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

import net.alteiar.images.TransfertImage;
import net.alteiar.server.shared.observer.campaign.MediaObservableRemote;
import net.alteiar.shared.tool.SynchronizedHashMap;

public class MediaManagerRemote extends MediaObservableRemote implements
		IMediaManagerRemote {
	private static final long serialVersionUID = 1L;

	private final SynchronizedHashMap<Long, TransfertImage> images;
	private Long currentKey;

	public MediaManagerRemote() throws RemoteException {
		super();

		images = new SynchronizedHashMap<Long, TransfertImage>();
		currentKey = 0L;
	}

	@Override
	public synchronized Long addImage(TransfertImage image)
			throws RemoteException {
		images.put(currentKey, image);
		notifyMediaAdded(currentKey);
		currentKey++;

		return currentKey - 1;
	}

	@Override
	public TransfertImage getImage(Long guid) throws RemoteException {
		TransfertImage image = images.get(guid);
		if (image == null) {
			image = null;
		}
		return image;
	}

	@Override
	public HashMap<Long, TransfertImage> getAllImages() throws RemoteException {
		HashMap<Long, TransfertImage> transfert = new HashMap<Long, TransfertImage>();

		images.incCounter();
		for (Entry<Long, TransfertImage> entry : images.entrySet()) {
			transfert.put(entry.getKey(), entry.getValue());
		}
		images.decCounter();

		return transfert;
	}
}
