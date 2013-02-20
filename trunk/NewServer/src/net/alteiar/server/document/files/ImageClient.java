package net.alteiar.server.document.files;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import net.alteiar.client.DocumentClient;

public class ImageClient extends DocumentClient<IImageRemote> {
	private static final long serialVersionUID = 1L;

	private transient BufferedImage image;

	public ImageClient(IImageRemote remote) throws RemoteException {
		super(remote);
	}

	public BufferedImage getImage() {
		return image;
	}

	@Override
	protected void closeDocument() throws RemoteException {
	}
}
