package net.alteiar.server.document.map;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;

public class DocumentMapBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	protected final String mapName;

	protected final int width;
	protected final int height;

	protected final Long imageId;
	protected final Long filterId;

	protected final Scale scale;

	public DocumentMapBuilder(String mapName, int width, int height,
			Long imageId, Long filterId, Scale scale) {
		this.mapName = mapName;

		this.width = width;
		this.height = height;

		this.imageId = imageId;
		this.filterId = filterId;

		this.scale = scale;
	}

	@Override
	public IDocumentRemote buildDocument() throws RemoteException {
		return new MapRemote(mapName, width, height, imageId, filterId, scale);
	}
}
