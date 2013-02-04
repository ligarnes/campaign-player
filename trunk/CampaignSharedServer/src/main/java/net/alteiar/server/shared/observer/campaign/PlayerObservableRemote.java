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
package net.alteiar.server.shared.observer.campaign;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PlayerObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = 290022110671796273L;

	private static Class<?> LISTENER = IPlayerObserverRemote.class;

	public PlayerObservableRemote() throws RemoteException {
		super();
	}

	public void addPlayerListener(IPlayerObserverRemote map)
			throws RemoteException {
		this.addListener(LISTENER, map);
	}

	public void removePlayerListener(IPlayerObserverRemote map)
			throws RemoteException {
		this.removeListener(LISTENER, map);
	}

	protected void notifyObserverMessageReceived(MessageRemote message) {
		for (Remote remote : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new PlayerMessageReceivedTask(this, remote,
							message));
		}
	}

	private class PlayerMessageReceivedTask extends BaseNotify {

		private final MessageRemote message;

		public PlayerMessageReceivedTask(PlayerObservableRemote observable,
				Remote observer, MessageRemote message) {
			super(observable, LISTENER, observer);
			this.message = message;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IPlayerObserverRemote) observer).messageReceived(message);
		}

		@Override
		public String getStartText() {
			return "start notify message received";
		}

		@Override
		public String getFinishText() {
			return "finish notify message received";
		}
	}
}
