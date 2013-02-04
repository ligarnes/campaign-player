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
package net.alteiar.server.shared.observer.campaign.battle;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.SerializableFile;
import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.battle.ICharacterCombatRemote;
import net.alteiar.server.shared.campaign.battle.map.Map2DRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public class BattleObservableRemote extends Map2DRemote {

	private static final long serialVersionUID = -911665897802134769L;

	private static final Class<?> LISTENER = IBattleObserverRemote.class;

	public BattleObservableRemote(SerializableFile background, Scale scale)
			throws RemoteException {
		super(background, scale);
	}

	public void addBattleListener(IBattleObserverRemote listener)
			throws RemoteException {
		this.addListener(LISTENER, listener);
	}

	public void removeBattleListener(IBattleObserverRemote listener)
			throws RemoteException {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyCharacterAdded(ICharacterCombatRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new CharacterAddedTask(
					this, observer, remote, true));
		}
	}

	protected void notifyCharacterRemoved(ICharacterCombatRemote remote) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new CharacterAddedTask(
					this, observer, remote, false));
		}
	}

	protected void notifyNextTurn(Integer currentTurn) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new nextBattleTask(this,
					observer, currentTurn));
		}
	}

	private class CharacterAddedTask extends BaseNotify {
		private final Boolean isAdded;
		private final ICharacterCombatRemote character;

		public CharacterAddedTask(BattleObservableRemote observable,
				Remote observer, ICharacterCombatRemote access, Boolean isAdded) {
			super(observable, LISTENER, observer);
			this.character = access;
			this.isAdded = isAdded;
		}

		@Override
		protected void doAction() throws RemoteException {
			if (isAdded) {
				((IBattleObserverRemote) observer).characterAdded(character);
			} else {
				((IBattleObserverRemote) observer).characterRemoved(character
						.getId());
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

	private class nextBattleTask extends BaseNotify {
		private final Integer currentTurn;

		public nextBattleTask(BattleObservableRemote observable,
				Remote observer, Integer currentTurn) {
			super(observable, LISTENER, observer);
			this.currentTurn = currentTurn;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IBattleObserverRemote) observer).nextTurn(currentTurn);

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
