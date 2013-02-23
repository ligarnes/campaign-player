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

import java.rmi.RemoteException;

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.map.MapRemote;
import net.alteiar.server.document.map.Scale;
import net.alteiar.shared.Randomizer;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleRemote extends MapRemote implements IBattleRemote {
	private static final long serialVersionUID = -8540822570181198244L;

	private Integer currentTurn;

	// //TODO move to rules
	private Boolean initiativeEachTurn;

	// private final SynchronizedHashMap<Long, ICharacterCombatRemote>
	// allCharacter;

	public BattleRemote(String battleName, int width, int height,
			long background, long localFilter, Scale scale)
			throws RemoteException {
		super(battleName, width, height, background, localFilter, scale);

		// this.allCharacter = new SynchronizedHashMap<Long,
		// ICharacterCombatRemote>();

		currentTurn = 0;
		initiativeEachTurn = false;
	}

	/*
	@Override
	public synchronized void addCharacter(Long id, Integer init, Point position)
			throws RemoteException {
		ICharacterRemote found = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getCharater(id);
		ICharacterCombatRemote combat = new CharacterCombatRemote(found);
		combat.setPosition(position);
		combat.setInitiative(init);
		allCharacter.put(combat.getId(), combat);

		this.notifyCharacterAdded(combat);
	}

	@Override
	public synchronized void addMonster(Long id, Integer init, Point position,
			Boolean isVisible) throws RemoteException {
		ICharacterRemote found = ServerCampaign.SERVER_CAMPAIGN_REMOTE
				.getMonster(id);

		String orgName = found.getCharacterFacade().getName();
		int idx = 1;
		String name = orgName + idx;

		Boolean characterChanged = true;
		while (characterChanged) {
			characterChanged = false;
			for (ICharacterCombatRemote character : this.allCharacter.values()) {
				if (character.getCharacterSheet().getCharacterFacade()
						.getName().equals(name)) {
					idx++;
					name = orgName + idx;
					characterChanged = true;
					break;
				}
			}
		}
		ICharacterCombatRemote combat = new CharacterCombatRemote(
				found.fastCopy(name));
		combat.setVisibleForPlayer(isVisible);
		combat.setPosition(position);
		combat.setInitiative(init);
		allCharacter.put(combat.getId(), combat);

		this.notifyCharacterAdded(combat);
	}

	@Override
	public synchronized void removeCharacter(Long characterId)
			throws RemoteException {
		allCharacter.remove(characterId);
		this.notifyCharacterRemoved(characterId);
	}

	@Override
	public ICharacterCombatRemote[] getAllCharacter() throws RemoteException {
		allCharacter.incCounter();
		ICharacterCombatRemote[] characters = new ICharacterCombatRemote[allCharacter
				.size()];
		allCharacter.values().toArray(characters);
		allCharacter.decCounter();

		return characters;
	}*/

	@Override
	public synchronized void setInitiativeEachTurn(Boolean isInitEachTurn)
			throws RemoteException {
		initiativeEachTurn = isInitEachTurn;
	}

	@Override
	public synchronized void nextTurn() throws RemoteException {
		if (initiativeEachTurn) {
			runInitForAllCharacter();
		}

		++currentTurn;
		notifyNextTurn(currentTurn);
	}

	@Override
	public Integer getCurrentTurn() throws RemoteException {
		return currentTurn;
	}

	// TODO should be move in rule document
	private Integer rollInitiative() {
		return Randomizer.dice(20);
	}

	// TODO should be move in rule document
	private void runInitForAllCharacter() {
		/*
		for (ICharacterCombatRemote character : allCharacter.values()) {
			try {
				character.setInitiative(rollInitiative());
			} catch (RemoteException e) {
				ExceptionTool.showError(e);
			}
		}*/
	}

	@Override
	public BattleClient buildProxy() throws RemoteException {
		return new BattleClient(this);
	}

	// //////////////LISTENER METHODS //////////////
	@Override
	public void addBattleListener(IBattleListenerRemote listener)
			throws RemoteException {
		this.addListener(IBattleListenerRemote.class, listener);
	}

	@Override
	public void removeBattleListener(IBattleListenerRemote listener)
			throws RemoteException {
		this.removeListener(IBattleListenerRemote.class, listener);
	}

	/*
	protected void notifyCharacterAdded(ICharacterCombatRemote remote) {
		for (Remote observer : this.getListener(BATTLE_LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new CharacterAddedTask(
					this, observer, remote, true));
		}
	}

	protected void notifyCharacterRemoved(Long characterId) {
		for (Remote observer : this.getListener(BATTLE_LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new CharacterRemovedTask(
					this, observer, characterId));
		}
	}*/

	protected void notifyNextTurn(Integer currentTurn) {
		for (IBattleListenerRemote observer : this
				.getListener(IBattleListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL.addTask(new nextBattleTask(this,
					observer, currentTurn));
		}
	}

	/*
	private class CharacterAddedTask extends BaseNotify<IBattleListenerRemote> {
		private final Boolean isAdded;
		private final ICharacterCombatRemote character;

		public CharacterAddedTask(BattleRemote observable, Remote observer,
				ICharacterCombatRemote access, Boolean isAdded) {
			super(observable, IBattleListenerRemote.class, observer);
			this.character = access;
			this.isAdded = isAdded;
		}

		@Override
		protected void doAction() throws RemoteException {
			if (isAdded) {
				observer.characterAdded(character);
			} else {
				observer.characterRemoved(character.getId());
			}
		}

		@Override
		public String getStartText() {
			return "start notify character added";
		}

		@Override
		public String getFinishText() {
			return "finish notify character added";
		}
	}

	private class CharacterRemovedTask extends
			BaseNotify<IBattleListenerRemote> {
		private final Long characterId;

		public CharacterRemovedTask(BattleRemote observable,
				IBattleListenerRemote observer, Long characterId) {
			super(observable, IBattleListenerRemote.class, observer);
			this.characterId = characterId;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.characterRemoved(characterId);
		}

		@Override
		public String getStartText() {
			return "start notify character removed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character removed";
		}
	}*/

	private class nextBattleTask extends BaseNotify<IBattleListenerRemote> {
		private final Integer currentTurn;

		public nextBattleTask(BattleRemote observable,
				IBattleListenerRemote observer, Integer currentTurn) {
			super(observable, IBattleListenerRemote.class, observer);
			this.currentTurn = currentTurn;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.nextTurn(currentTurn);

		}

		@Override
		public String getStartText() {
			return "start notify next turn";
		}

		@Override
		public String getFinishText() {
			return "finish notify next turn";
		}
	}
}
