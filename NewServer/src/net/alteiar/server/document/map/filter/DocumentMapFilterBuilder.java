package net.alteiar.server.document.map.filter;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;

public class DocumentMapFilterBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final int width;
	private final int height;

	public DocumentMapFilterBuilder(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new MapFilterRemote(width, height);
	}
}
