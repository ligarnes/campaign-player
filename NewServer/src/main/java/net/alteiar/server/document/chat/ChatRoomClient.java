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

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.chat.message.ChatObject;
import net.alteiar.server.document.chat.message.MessageRemote;
import net.alteiar.shared.ExceptionTool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ChatRoomClient extends DocumentClient<IChatRoomRemote> {
	private static final long serialVersionUID = 1L;

	private transient ChatRoomListenerRemote listener;

	private transient String pseudo;
	protected transient List<MessageRemote> allMessage;

	/**
	 * @param remote
	 * @throws RemoteException
	 */
	public ChatRoomClient(IChatRoomRemote remote) throws RemoteException {
		super(remote);
		this.pseudo = "pseudo";
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

	public void talk(String message, String command) {
		talk(new MessageRemote(pseudo, message, command));
	}

	protected void talk(MessageRemote msg) {
		try {
			getRemote().talk(msg);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public List<MessageRemote> getAllMessage() {
		return allMessage;
	}

	public void addChatRoomListener(IChatRoomObserver listener) {
		this.addListener(IChatRoomObserver.class, listener);
	}

	public void removeChatRoomListener(IChatRoomObserver listener) {
		this.removeListener(IChatRoomObserver.class, listener);
	}

	protected void notifyTalk(MessageRemote remote) {
		for (Object observer : this.getListener(IChatRoomObserver.class)) {
			((IChatRoomObserver) observer).talk(remote);
		}
	}

	@Override
	protected void closeDocument() throws RemoteException {
		getRemote().removeChatRoomListener(listener);
	}

	@Override
	protected void loadDocumentLocal(File file) throws IOException {
		// cannot load from document
	}

	@Override
	protected void loadDocumentRemote() throws RemoteException {
		listener = new ChatRoomListenerRemote(getRemote());
		allMessage = new ArrayList<MessageRemote>();
	}

	/**
	 * this class should be observer and will use the notify of the chat room
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class ChatRoomListenerRemote extends UnicastRemoteObject implements
			IChatRoomListener {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected ChatRoomListenerRemote(IChatRoomRemote chat)
				throws RemoteException {
			super();
			chat.addChatRoomListener(this);
		}

		@Override
		public void playerTalk(MessageRemote message) throws RemoteException {
			allMessage.add(message);
			notifyTalk(message);
		}
	}
}
