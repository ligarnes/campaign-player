package net.alteiar.server.document.map.battle.character;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.character.CharacterClient;

public class DocumentCharacterCombatBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final Long characterId;
	private final int initiative;

	public DocumentCharacterCombatBuilder(CharacterClient character,
			Integer initiative) {
		this.characterId = character.getId();
		this.initiative = initiative;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new CharacterCombatRemote(characterId, initiative);
	}

}
