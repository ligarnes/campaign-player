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

/**
 * @author Cody Stoutenburg
 * 
 */
public class MessageRemote implements Serializable {

	private static final long serialVersionUID = -2669560462857993375L;

	public static final String TEXT_MESSAGE = "text";
	public static final String SYSTEM_CONNECT_MESSAGE = "system.connect";
	public static final String SYSTEM_DISCONNECT_MESSAGE = "system.disconnect";

	private final String expediteur;
	private final String message;
	private final String command;

	public MessageRemote(String expediteur, String message, String command) {
		super();

		this.expediteur = expediteur;
		this.message = message;
		this.command = command;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public String getMessage() {
		return message;
	}

	public String getCommand() {
		return command;
	}

	/*
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(destinataire);
		out.writeUTF(expediteur);
		out.writeUTF(message);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		destinataire = in.readUTF();
		expediteur = in.readUTF();
		message = in.readUTF();
	}*/
}
