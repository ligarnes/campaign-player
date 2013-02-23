package net.alteiar.server.document.map.battle;

import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.map.DocumentMapBuilder;
import net.alteiar.server.document.map.Scale;

public class DocumentBattleBuilder extends DocumentMapBuilder {
	private static final long serialVersionUID = 1L;

	public DocumentBattleBuilder(String mapName, int width, int height,
			Long imageId, Long filterId, Scale scale) {
		super(mapName, width, height, imageId, filterId, scale);
	}

	@Override
	public IDocumentRemote buildDocument() throws RemoteException {
		return new BattleRemote(mapName, width, height, imageId, filterId,
				scale);
	}
}
