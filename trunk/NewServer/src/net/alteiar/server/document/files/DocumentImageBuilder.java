package net.alteiar.server.document.files;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.SerializableImage;
import net.alteiar.shared.TransfertImage;

public class DocumentImageBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final TransfertImage transfertImage;
	private final String path;

	public DocumentImageBuilder(TransfertImage transfertImage) {
		this(null, transfertImage);
	}

	public DocumentImageBuilder(String imagePath) {
		this(imagePath, null);
	}

	public DocumentImageBuilder(String imagePath, TransfertImage transfertImage) {
		this.transfertImage = transfertImage;
		this.path = imagePath;
	}

	@Override
	public IDocumentRemote buildDocument() throws RemoteException {
		if (path != null) {
			SerializableImage img = null;
			try {
				img = new SerializableImage(new File(path));
				return new ImageRemote(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (transfertImage != null)
			return new ImageRemote(transfertImage);

		return null;
	}
}
