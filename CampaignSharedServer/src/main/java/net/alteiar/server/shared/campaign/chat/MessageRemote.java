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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MessageRemote implements Externalizable {

	private static final long serialVersionUID = -2669560462857993375L;
	// private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
	// "HH:mm:ss");

	public static final String TO_EVERYONE = "_to_all_";

	// private final String currentTime;
	private String destinataire;
	private String expediteur;
	private String message;

	public MessageRemote() {
		this(TO_EVERYONE, TO_EVERYONE, TO_EVERYONE);
	}

	/**
	 * @param expediteur
	 * @param destinataire
	 * @param message
	 */
	public MessageRemote(String expediteur, String destinataire, String message) {
		super();
		// Date now = new Date();
		// currentTime = DATE_FORMAT.format(now);
		this.destinataire = destinataire;
		this.expediteur = expediteur;
		this.message = message;
	}

	public MessageRemote(String expediteur, String message) {
		this(expediteur, TO_EVERYONE, message);
	}

	public String getDestinataire() {
		return destinataire;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public String getMessage() {
		return message;
	}

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
	}
}
