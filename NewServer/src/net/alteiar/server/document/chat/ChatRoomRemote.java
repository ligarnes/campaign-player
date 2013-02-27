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
package net.alteiar.server.document.chat;

import java.rmi.RemoteException;

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.server.document.chat.message.MessageRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ChatRoomRemote extends DocumentRemote implements IChatRoomRemote {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws RemoteException
	 */
	public ChatRoomRemote() throws RemoteException {
		super();
	}

	@Override
	public void talk(MessageRemote message) throws RemoteException {
		this.notifyPlayerTalk(message);
	}

	// Observable
	@Override
	public void addChatRoomListener(IChatRoomListener map)
			throws RemoteException {
		this.addListener(IChatRoomListener.class, map);
	}

	@Override
	public void removeChatRoomListener(IChatRoomListener map)
			throws RemoteException {
		this.removeListener(IChatRoomListener.class, map);
	}

	protected void notifyPlayerTalk(MessageRemote message) {
		for (IChatRoomListener observer : this
				.getListener(IChatRoomListener.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new ChatRoomPlayerTalkTask(this, observer, message));
		}
	}

	private class ChatRoomPlayerTalkTask extends BaseNotify<IChatRoomListener> {
		private final MessageRemote message;

		public ChatRoomPlayerTalkTask(ChatRoomRemote observable,
				IChatRoomListener observer, MessageRemote message) {
			super(observable, IChatRoomListener.class, observer);
			this.message = message;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.playerTalk(message);
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

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new ChatRoomClient(this);
	}
}
