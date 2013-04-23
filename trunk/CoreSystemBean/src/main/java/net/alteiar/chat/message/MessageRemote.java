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
package net.alteiar.chat.message;

import java.io.Serializable;

import net.alteiar.CampaignClient;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MessageRemote implements Serializable {
	@Attribute
	private static final long serialVersionUID = -2669560462857993375L;

	public static final String TEXT_MESSAGE = "text";
	public static final String SYSTEM_CONNECT_MESSAGE = "system.connect";
	public static final String SYSTEM_DISCONNECT_MESSAGE = "system.disconnect";

	@Element
	private UniqueID playerId;

	@Element
	private String sender;
	@Element
	private String message;
	@Element
	private String command;

	// Needed for save/load api
	protected MessageRemote() {
	}

	public MessageRemote(String expediteur, String message, String command) {
		super();

		playerId = CampaignClient.getInstance().getCurrentPlayer().getId();

		this.sender = expediteur;
		this.message = message;
		this.command = command;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public UniqueID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UniqueID playerId) {
		this.playerId = playerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageRemote other = (MessageRemote) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	/*
	 * @Override public void writeExternal(ObjectOutput out) throws IOException
	 * { out.writeUTF(destinataire); out.writeUTF(expediteur);
	 * out.writeUTF(message); }
	 * 
	 * @Override public void readExternal(ObjectInput in) throws IOException,
	 * ClassNotFoundException { destinataire = in.readUTF(); expediteur =
	 * in.readUTF(); message = in.readUTF(); }
	 */

}
