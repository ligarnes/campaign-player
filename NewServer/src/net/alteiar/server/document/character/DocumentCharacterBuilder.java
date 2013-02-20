package net.alteiar.server.document.character;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;

public class DocumentCharacterBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final PathfinderCharacterFacade character;
	private final Long imageId;

	public DocumentCharacterBuilder(PathfinderCharacterFacade character,
			Long imageId) {
		this.character = character;
		this.imageId = imageId;
	}

	@Override
	public CharacterRemote buildDocument() throws RemoteException {
		return new CharacterRemote(character, imageId);
	}
}
