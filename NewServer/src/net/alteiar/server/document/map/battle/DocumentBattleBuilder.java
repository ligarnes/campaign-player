package net.alteiar.server.document.map.battle;

import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.DocumentMapBuilder;
import net.alteiar.server.document.map.Scale;

public class DocumentBattleBuilder extends DocumentMapBuilder {
	private static final long serialVersionUID = 1L;

	public DocumentBattleBuilder(String mapName, TransfertImage image,
			Scale scale) {
		super(mapName, image, scale);
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new BattleRemote(mapName, width, height, imageId, filterId,
				scale);
	}
}
