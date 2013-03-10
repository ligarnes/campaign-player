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
import java.util.HashSet;

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.map.MapRemote;
import net.alteiar.server.document.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleRemote extends MapRemote implements IBattleRemote {
	private static final long serialVersionUID = -8540822570181198244L;

	private Integer currentTurn;

	private final HashSet<Long> characterCombat;

	public BattleRemote(String battleName, int width, int height,
			long background, long localFilter, Scale scale)
			throws RemoteException {
		super(battleName, width, height, background, localFilter, scale);

		characterCombat = new HashSet<Long>();
		currentTurn = 0;
	}

	@Override
	public void addCharacterCombat(Long id) throws RemoteException {
		boolean isAdded = false;
		synchronized (characterCombat) {
			isAdded = characterCombat.add(id);
		}
		if (isAdded) {
			notifyCharacterAdded(id);
		}
	}

	@Override
	public void removeCharacterCombat(Long id) throws RemoteException {
		boolean isRemoved = false;
		synchronized (characterCombat) {
			isRemoved = characterCombat.remove(id);
		}
		if (isRemoved) {
			notifyCharacterRemoved(id);
		}
	}

	@Override
	public HashSet<Long> getCharacterCombats() {
		HashSet<Long> charactersCopy = null;
		synchronized (characterCombat) {
			charactersCopy = (HashSet<Long>) characterCombat.clone();
		}

		return charactersCopy;
	}

	@Override
	public void nextTurn() throws RemoteException {
		synchronized (currentTurn) {
			++currentTurn;
		}
		notifyNextTurn(currentTurn);
	}

	@Override
	public Integer getCurrentTurn() throws RemoteException {
		return currentTurn;
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

	protected void notifyCharacterAdded(Long remote) {
		for (IBattleListenerRemote observer : this
				.getListener(IBattleListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new CharacterAddedRemovedTask(observer, remote,
							true));
		}
	}

	protected void notifyCharacterRemoved(Long characterId) {
		for (IBattleListenerRemote observer : this
				.getListener(IBattleListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new CharacterAddedRemovedTask(observer,
							characterId, false));
		}
	}

	protected void notifyNextTurn(Integer currentTurn) {
		for (IBattleListenerRemote observer : this
				.getListener(IBattleListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL.addTask(new nextBattleTask(this,
					observer, currentTurn));
		}
	}

	private class CharacterAddedRemovedTask extends
			BaseNotify<IBattleListenerRemote> {
		private final Boolean isAdded;
		private final Long character;

		public CharacterAddedRemovedTask(IBattleListenerRemote observer,
				Long character, Boolean isAdded) {
			super(BattleRemote.this, IBattleListenerRemote.class, observer);
			this.character = character;
			this.isAdded = isAdded;
		}

		@Override
		protected void doAction() throws RemoteException {
			if (isAdded) {
				observer.characterAdded(character);
			} else {
				observer.characterRemoved(character);
			}
		}
	}

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
	}
}
