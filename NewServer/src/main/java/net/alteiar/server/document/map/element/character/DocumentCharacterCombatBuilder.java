package net.alteiar.server.document.map.element.character;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public class DocumentCharacterCombatBuilder extends DocumentMapElementBuilder {
	private static final long serialVersionUID = 1L;

	protected final Long characterId;

	public DocumentCharacterCombatBuilder(MapClient<?> map, Point position,
			CharacterClient character) {
		this(map.getId(), position, character.getId());
	}

	public DocumentCharacterCombatBuilder(Long map, Point position,
			CharacterClient character) {
		this(map, position, character.getId());
	}

	public DocumentCharacterCombatBuilder(Long map, Point position,
			Long characterId) {
		super(map, position);
		this.characterId = characterId;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new CharacterCombatRemote(getMap(), getPosition(), characterId);
	}
}
