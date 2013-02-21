package net.alteiar.server.document.files;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentRemote;
import net.alteiar.shared.TransfertImage;

public class ImageRemote extends DocumentRemote implements IImageRemote {
	private static final long serialVersionUID = 1L;

	private final TransfertImage transfertImage;

	public ImageRemote(TransfertImage transfertImage) throws RemoteException {
		super();

		this.transfertImage = transfertImage;
	}

	@Override
	public TransfertImage getImage() throws RemoteException {
		return transfertImage;
	}

	@Override
	public ImageClient buildProxy() throws RemoteException {
		return new ImageClient(this);
	}

}
