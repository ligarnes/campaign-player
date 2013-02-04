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

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterCombatObservableRemote extends BaseObservableRemote {

	private static final long serialVersionUID = 1480788117925735459L;

	private static final Class<?> LISTENER = ICharacterCombatObserverRemote.class;

	/**
	 * @throws RemoteException
	 */
	public CharacterCombatObservableRemote() throws RemoteException {
		super();
	}

	public void addCharacterCombatListener(ICharacterCombatObserverRemote map)
			throws RemoteException {
		this.addListener(LISTENER, map);
	}

	public void removeCharacterCombatListener(ICharacterCombatObserverRemote map)
			throws RemoteException {
		this.removeListener(LISTENER, map);
	}

	protected void notifyCharacterVisibilityChange(Boolean visibility) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterVisibilityChangedTask(this, observer,
							visibility));
		}
	}

	protected void notifyCharacterInitiativeChanged(Integer init) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterInitiativeChangedTask(this, observer,
							init));
		}
	}

	protected void notifyCharacterMovedChanged(Point init) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterPositionChangedTask(this, observer,
							init));
		}
	}

	protected void notifyCharacterRotationChanged(Double init) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new CharacterRotationChangedTask(this, observer,
							init));
		}
	}

	private class CharacterVisibilityChangedTask extends BaseNotify {

		private final Boolean visibility;

		public CharacterVisibilityChangedTask(
				CharacterCombatObservableRemote observable, Remote observer,
				Boolean init) {
			super(observable, LISTENER, observer);
			this.visibility = init;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterCombatObserverRemote) observer)
					.visibilityChanged(visibility);
		}

		@Override
		public String getStartText() {
			return "start notify character init changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character init changed";
		}
	}

	private class CharacterInitiativeChangedTask extends BaseNotify {

		private final Integer init;

		public CharacterInitiativeChangedTask(
				CharacterCombatObservableRemote observable, Remote observer,
				Integer init) {
			super(observable, LISTENER, observer);
			this.init = init;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterCombatObserverRemote) observer).initiativeChanged(init);
		}

		@Override
		public String getStartText() {
			return "start notify character init changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character init changed";
		}
	}

	private class CharacterPositionChangedTask extends BaseNotify {
		private final Point newPos;

		public CharacterPositionChangedTask(
				CharacterCombatObservableRemote observable, Remote observer,
				Point init) {
			super(observable, LISTENER, observer);
			this.newPos = init;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterCombatObserverRemote) observer).elementMoved(newPos);
		}

		@Override
		public String getStartText() {
			return "start notify character init changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character init changed";
		}
	}

	private class CharacterRotationChangedTask extends BaseNotify {
		private final Double newAngle;

		public CharacterRotationChangedTask(
				CharacterCombatObservableRemote observable, Remote observer,
				Double init) {
			super(observable, LISTENER, observer);
			this.newAngle = init;
		}

		@Override
		protected void doAction() throws RemoteException {
			((ICharacterCombatObserverRemote) observer).elementRotate(newAngle);
		}

		@Override
		public String getStartText() {
			return "start notify character init changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character init changed";
		}
	}
}
