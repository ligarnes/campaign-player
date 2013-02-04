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
package net.alteiar.server.shared.observer.campaign.character;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.player.PlayerAccess;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class CharacterSheetObservableRemote extends
		BaseObservableRemote {
	private static final long serialVersionUID = 1L;

	private static final Class<?> LISTENER = ICharacterSheetObserverRemote.class;

	public CharacterSheetObservableRemote() throws RemoteException {
		super();
	}

	public void addCharacterListener(ICharacterSheetObserverRemote map)
			throws RemoteException {
		this.addListener(LISTENER, map);
	}

	public void removeCharacterListener(ICharacterSheetObserverRemote map)
			throws RemoteException {
		this.removeListener(LISTENER, map);
	}

	protected void notifyCurrentHealthPointChanged(Integer currentHp) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new CharacterChangedTask(
					this, observer, currentHp));
		}
	}

	protected void notifyPlayerAccessChanged(PlayerAccess playerAccess) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterAccessChangedTask(this, observer,
							playerAccess));
		}
	}

	private class CharacterChangedTask extends BaseNotify {
		private final Integer currentHp;

		public CharacterChangedTask(CharacterSheetObservableRemote observable,
				Remote observer, Integer hp) {
			super(observable, LISTENER, observer);
			this.currentHp = hp;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterSheetObserverRemote) observer)
					.currentHealthPointChanged(currentHp);
		}

		@Override
		public String getStartText() {
			return "start notify character changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character changed";
		}
	}

	private class CharacterAccessChangedTask extends BaseNotify {
		private final PlayerAccess playerAccess;

		public CharacterAccessChangedTask(
				CharacterSheetObservableRemote observable, Remote observer,
				PlayerAccess access) {
			super(observable, LISTENER, observer);
			this.playerAccess = access;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterSheetObserverRemote) observer)
					.accessChanged(playerAccess);
		}

		@Override
		public String getStartText() {
			return "start notify character access changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character access changed";
		}
	}
}
