package net.alteiar.server.document.files;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.TransfertImage;

public class DocumentImageBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final TransfertImage transfertImage;

	public DocumentImageBuilder(TransfertImage transfertImage) {
		this.transfertImage = transfertImage;
	}

	@Override
	public IDocumentRemote buildDocument() throws RemoteException {
		return new ImageRemote(transfertImage);
	}
}
