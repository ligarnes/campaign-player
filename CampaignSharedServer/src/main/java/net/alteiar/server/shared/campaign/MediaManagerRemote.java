package net.alteiar.server.shared.campaign;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

import net.alteiar.SerializableFile;
import net.alteiar.server.shared.observer.campaign.MediaObservableRemote;
import net.alteiar.shared.tool.SynchronizedHashMap;

public class MediaManagerRemote extends MediaObservableRemote implements
		IMediaManagerRemote {
	private static final long serialVersionUID = 1L;

	private final SynchronizedHashMap<Long, SerializableFile> images;
	private Long currentKey;

	public MediaManagerRemote() throws RemoteException {
		super();

		images = new SynchronizedHashMap<Long, SerializableFile>();
		currentKey = 0L;
	}

	@Override
	public Long addImage(SerializableFile image) throws RemoteException {
		images.put(currentKey, image);
		notifyMediaAdded(currentKey);
		currentKey++;

		return currentKey - 1;
	}

	@Override
	public SerializableFile getImage(Long guid) throws RemoteException {
		SerializableFile image = images.get(guid);
		if (image == null) {
			image = null;
		}
		return image;
	}

	@Override
	public HashMap<Long, SerializableFile> getAllImages()
			throws RemoteException {
		HashMap<Long, SerializableFile> transfert = new HashMap<Long, SerializableFile>();

		images.incCounter();
		for (Entry<Long, SerializableFile> entry : images.entrySet()) {
			transfert.put(entry.getKey(), entry.getValue());
		}
		images.decCounter();

		return transfert;
	}
}
