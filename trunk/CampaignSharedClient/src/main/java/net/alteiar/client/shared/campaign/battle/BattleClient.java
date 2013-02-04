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
package net.alteiar.client.shared.campaign.battle;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.battle.character.CharacterCombatClient;
import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.map.Map2DRemoteObserver;
import net.alteiar.client.shared.observer.campaign.battle.BattleClientObservable;
import net.alteiar.client.shared.observer.campaign.battle.character.ICharacterCombatObserver;
import net.alteiar.server.shared.campaign.MyTimer;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.battle.ICharacterCombatRemote;
import net.alteiar.server.shared.observer.campaign.battle.IBattleObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleClient extends BattleClientObservable implements
		IBattleClient {
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer currentTurn;
	private final ConcurrentHashMap<Long, ICharacterCombatClient> characters;

	public BattleClient(IBattleRemote battle) {
		super(battle);

		MyTimer timer = new MyTimer();
		timer.startTimer();
		characters = new ConcurrentHashMap<Long, ICharacterCombatClient>();
		try {
			name = remoteObject.getName();
			currentTurn = remoteObject.getCurrentTurn();

			// Load all current Character
			for (ICharacterCombatRemote combat : remoteObject.getAllCharacter()) {
				addCharacterRemote(combat);
			}
			new BattleClientRemoteObserver(this, remoteObject);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
		timer.endTimer();
		timer.printTime("battle client created " + getId());
	}

	@Override
	public String getName() {
		return name;
	}

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
			remoteObject.removeCharacter(((CharacterCombatClient) character)
					.getRemoteReference());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Collection<ICharacterCombatClient> getAllCharacter() {
		return characters.values();
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
	}

	public void setInitiativeEachTurn(Boolean isInitEachTurn) {
		try {
			remoteObject.setInitiativeEachTurn(isInitEachTurn);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void nextTurn() {
		try {
			remoteObject.nextTurn();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Integer getCurrentTurn() {
		return currentTurn;
	}

	protected synchronized ICharacterCombatClient addCharacterRemote(
			ICharacterCombatRemote combat) {
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
						notifyMapChanged();
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

		return characterClient;
	}

	protected void removeCharacterRemote(Long characterId) {
		// ICharacterCombatClient client = new CharacterCombatClient(this,
		// combat);
		ICharacterCombatClient client = characters.remove(characterId);// client.getId());
		notifyCharacterRemove(this, client);
		notifyMapChanged();
	}

	protected void setLocalCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
		this.notifyTurnChanged(this);
	}

	@Override
	public ICharacterCombatClient getCharacterAt(Point location) {
		ICharacterCombatClient characterAt = null;
		for (ICharacterCombatClient character : getAllCharacter()) {
			if (character.isInside(location)) {
				characterAt = character;
				break;
			}
		}
		return characterAt;
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class BattleClientRemoteObserver extends Map2DRemoteObserver
			implements IBattleObserverRemote {
		private static final long serialVersionUID = 2559145398149500009L;

		protected BattleClientRemoteObserver(BattleClient client,
				IBattleRemote battle) throws RemoteException {
			super(client, battle);
			battle.addBattleListener(this);
		}

		@Override
		public void characterAdded(ICharacterCombatRemote character)
				throws RemoteException {
			addCharacterRemote(character);
		}

		@Override
		public void characterRemoved(Long characterId) throws RemoteException {
			removeCharacterRemote(characterId);
		}

		@Override
		public void nextTurn(Integer currentTurn) throws RemoteException {
			setLocalCurrentTurn(currentTurn);
		}
	}
}
