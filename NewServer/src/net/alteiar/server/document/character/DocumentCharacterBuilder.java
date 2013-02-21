package net.alteiar.server.document.character;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;

public class DocumentCharacterBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final IPathfinderCharacter character;
	private final Long imageId;

	public DocumentCharacterBuilder(IPathfinderCharacter character, Long imageId) {
		this.character = character;
		this.imageId = imageId;
	}

	@Override
	public CharacterRemote buildDocument() throws RemoteException {
		return new CharacterRemote(character.getName(), character.getAc(),
				character.getAcFlatFooted(), character.getAcTouch(),
				character.getInitMod(), character.getHp(),
				character.getCurrentHp(), character.getWidth().floatValue(),
				character.getHeight().floatValue(), imageId);
	}
}
