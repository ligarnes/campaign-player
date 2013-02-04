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
package net.alteiar.server.shared.observer.campaign.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ChatRoomObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = 1L;

	private static final Class<?> LISTENER = IChatRoomObserverRemote.class;

	public ChatRoomObservableRemote() throws RemoteException {
		super();
	}

	public void addChatRoomListener(IChatRoomObserverRemote map)
			throws RemoteException {
		this.addListener(LISTENER, map);
	}

	public void removeChatRoomListener(IChatRoomObserverRemote map)
			throws RemoteException {
		this.removeListener(LISTENER, map);
	}

	protected void notifyPlayerJoin(MessageRemote join) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new ChatRoomPlayerJoinQuitTask(this, observer,
							join, true));
		}
	}

	protected void notifyPlayerLeave(MessageRemote leave) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new ChatRoomPlayerJoinQuitTask(this, observer,
							leave, false));
		}
	}

	protected void notifyPlayerTalk(MessageRemote message) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL
					.addTask(new ChatRoomPlayerTalkTask(this, observer, message));
		}
	}

	private class ChatRoomPlayerJoinQuitTask extends BaseNotify {
		private final boolean join;
		private final MessageRemote message;

		public ChatRoomPlayerJoinQuitTask(ChatRoomObservableRemote observable,
				Remote observer, MessageRemote name, boolean join) {
			super(observable, LISTENER, observer);
			this.message = name;
			this.join = join;
		}

		@Override
		protected void doAction() throws RemoteException {
			if (join) {
				((IChatRoomObserverRemote) observer).playerJoin(message);
			} else {
				((IChatRoomObserverRemote) observer).playerLeave(message);
			}
		}

		@Override
		public String getStartText() {
			return "start notify player join";
		}

		@Override
		public String getFinishText() {
			return "finish notify player join";
		}
	}

	private class ChatRoomPlayerTalkTask extends BaseNotify {
		private final MessageRemote message;

		public ChatRoomPlayerTalkTask(ChatRoomObservableRemote observable,
				Remote observer, MessageRemote message) {
			super(observable, LISTENER, observer);
			this.message = message;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IChatRoomObserverRemote) observer).playerTalk(message);
		}

		@Override
		public String getStartText() {
			return "start notify player talk";
		}

		@Override
		public String getFinishText() {
			return "finish notify player talk";
		}
	}
}
