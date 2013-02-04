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
package net.alteiar.server.shared.campaign.chat;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.alteiar.server.shared.observer.campaign.chat.ChatRoomObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ChatRoomRemote extends ChatRoomObservableRemote implements
		IChatRoomRemote {
	private static final long serialVersionUID = 1L;

	private final ConcurrentLinkedQueue<MessageRemote> allMessage;

	/**
	 * @throws RemoteException
	 */
	public ChatRoomRemote() throws RemoteException {
		super();

		allMessage = new ConcurrentLinkedQueue<MessageRemote>();
	}

	@Override
	public void join(MessageRemote join) {
		allMessage.add(join);
		this.notifyPlayerJoin(join);
	}

	@Override
	public void talk(MessageRemote message) {
		allMessage.add(message);
		this.notifyPlayerTalk(message);
	}

	@Override
	public void leave(MessageRemote leave) {
		allMessage.add(leave);
		this.notifyPlayerLeave(leave);
	}

	@Override
	public List<MessageRemote> getAllMessage() {
		List<MessageRemote> allMsg = new ArrayList<MessageRemote>();

		Iterator<MessageRemote> itt = allMessage.iterator();
		while (itt.hasNext()) {
			allMsg.add(itt.next());
		}

		return allMsg;
	}

}
