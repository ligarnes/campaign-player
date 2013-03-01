/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.server.document.map.battle;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.server.document.map.MapClient;
import net.alteiar.shared.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleClient extends MapClient<IBattleRemote> {
	private static final long serialVersionUID = 1L;

	private transient BattleClientRemoteObserver battleListener;
	private Integer currentTurn;

	// private final SynchronizedHashMap<Long, ICharacterCombatClient>
	// characters;

	public BattleClient(IBattleRemote battle) throws RemoteException {
		super(battle);
		// characters = new SynchronizedHashMap<Long, ICharacterCombatClient>();
		currentTurn = getRemote().getCurrentTurn();

		/* 
		// Load all current Character
		for (ICharacterCombatRemote combat : remoteObject.getAllCharacter()) {
			addCharacterRemote(combat);
		}
		new BattleClientRemoteObserver(remoteObject);
		*/
	}

	/*
	@Override
	public void addCharacter(ICharacterSheetClient character, Integer init,
			Point position) {
		try {
			remoteObject.addCharacter(character.getId(), init, position);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void addMonster(ICharacterSheetClient character, Integer init,
			Boolean isVisible, Point position) {
		try {
			remoteObject.addMonster(character.getId(), init, position,
					isVisible);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void removeCharacter(ICharacterCombatClient character) {
		try {
			remoteObject.removeCharacter(character.getId());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public ICharacterCombatClient[] getAllCharacter() {
		characters.incCounter();
		ICharacterCombatClient[] allCharacters = new ICharacterCombatClient[characters
				.values().size()];
		characters.values().toArray(allCharacters);
		characters.decCounter();
		return allCharacters;
	}

	@Override
	public ICharacterCombatClient getCharacterCombat(Long guid) {
		ICharacterCombatClient client = this.characters.get(guid);

		while (client == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				ExceptionTool.showError(e);
			}
			client = this.characters.get(guid);
		}

		return client;
	}*/

	public void nextTurn() {
		try {
			getRemote().nextTurn();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public Integer getCurrentTurn() {
		return currentTurn;
	}

	/*
	protected synchronized void addCharacterRemote(ICharacterCombatRemote combat) {
		ICharacterCombatClient characterClient = new CharacterCombatClient(
				this, combat);

		characterClient
				.addCharacterCombatListener(new ICharacterCombatObserver() {
					@Override
					public void visibilityChange(
							ICharacterCombatClient character) {
						notifyMapChanged();
					}

					@Override
					public void rotationChanged(ICharacterCombatClient character) {
						notifyMapChanged();
					}

					@Override
					public void positionChanged(ICharacterCombatClient character) {
						notifyMapChanged();
					}

					@Override
					public void initiativeChange(
							ICharacterCombatClient character) {
					}

					@Override
					public void highLightChange(
							ICharacterCombatClient character,
							Boolean isHighlighted) {
						notifyMapChanged();
					}

					@Override
					public void characterChange(ICharacterCombatClient character) {
						notifyMapChanged();
					}
				});

		characters.put(characterClient.getId(), characterClient);

		notifyCharacterAdded(this, characterClient);
		notifyMapChanged();
	}

	protected void removeCharacterRemote(Long characterId) {
		ICharacterCombatClient client = characters.remove(characterId);
		notifyCharacterRemove(this, client);
		notifyMapChanged();
	}*/

	protected void setLocalCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
		// TODO this.notifyTurnChanged(this);
	}

	/*
	@Override
	public ICharacterCombatClient getCharacterAt(Point location) {
		characters.incCounter();
		ICharacterCombatClient characterAt = null;
		for (ICharacterCombatClient character : getAllCharacter()) {
			if (character.isInside(location)) {
				characterAt = character;
				break;
			}
		}
		characters.decCounter();
		return characterAt;
	}*/

	@Override
	protected void closeDocument() throws RemoteException {
		super.closeDocument();
		getRemote().removeBattleListener(battleListener);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		super.loadDocumentRemote();
		battleListener = new BattleClientRemoteObserver(getRemote());
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class BattleClientRemoteObserver extends UnicastRemoteObject
			implements IBattleListenerRemote {
		private static final long serialVersionUID = 1L;

		protected BattleClientRemoteObserver(IBattleRemote battle)
				throws RemoteException {
			super();
			battle.addBattleListener(this);
		}

		/*
		@Override
		public void characterAdded(ICharacterCombatRemote character)
				throws RemoteException {
			addCharacterRemote(character);
		}

		@Override
		public void characterRemoved(Long characterId) throws RemoteException {
			removeCharacterRemote(characterId);
		}*/

		@Override
		public void nextTurn(Integer currentTurn) throws RemoteException {
			setLocalCurrentTurn(currentTurn);
		}
	}
}
