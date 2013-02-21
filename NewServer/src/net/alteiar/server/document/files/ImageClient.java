package net.alteiar.server.document.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

import net.alteiar.server.document.DocumentClient;

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

	@Override
	protected void loadDocumentLocal(File file) throws IOException {
		image = ImageIO.read(file);
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		image = this.getRemote().getImage().restoreImage();
	}
}
