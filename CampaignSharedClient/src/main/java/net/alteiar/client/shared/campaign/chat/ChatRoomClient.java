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
package net.alteiar.client.shared.campaign.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.observer.campaign.chat.ChatRoomObservable;
import net.alteiar.server.shared.campaign.chat.IChatRoomRemote;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.alteiar.server.shared.observer.campaign.chat.IChatRoomObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ChatRoomClient extends ChatRoomObservable {
	private static final long serialVersionUID = 1L;

	private String pseudo;
	protected List<MessageRemote> allMessage;

	/**
	 * @param remote
	 */
	public ChatRoomClient(IChatRoomRemote remote) {
		super(remote);
		try {
			new ChatRoomClientRemote(remoteObject);

			allMessage = new ArrayList<MessageRemote>();
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void talk(String message) {
		talk(message, MessageRemote.TEXT_MESSAGE);
	}

	public void talk(ChatObject obj, String command) {
		talk(obj.stringFormat(), command);
	}

	private String getDefaultName() {
		return CampaignClient.INSTANCE.getCurrentPlayer().getName();
	}

	public void talk(String message, String command) {
		if (pseudo == null) {
			pseudo = getDefaultName();
		}
		talk(new MessageRemote(pseudo, message, command));
	}

	protected void talk(MessageRemote msg) {
		try {
			remoteObject.talk(msg);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public List<MessageRemote> getAllMessage() {
		return allMessage;
	}

	/**
	 * this class should be observer and will use the notify of the chat room
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class ChatRoomClientRemote extends UnicastRemoteObject implements
			IChatRoomObserverRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected ChatRoomClientRemote(IChatRoomRemote chat)
				throws RemoteException {
			super();
			chat.addChatRoomListener(this);
		}

		@Override
		public void playerJoin(MessageRemote join) throws RemoteException {
			allMessage.add(join);
			notifyTalk(join);
		}

		@Override
		public void playerLeave(MessageRemote leave) throws RemoteException {
			allMessage.add(leave);
			notifyTalk(leave);
		}

		@Override
		public void playerTalk(MessageRemote message) throws RemoteException {
			allMessage.add(message);
			notifyTalk(message);
		}
	}
}
