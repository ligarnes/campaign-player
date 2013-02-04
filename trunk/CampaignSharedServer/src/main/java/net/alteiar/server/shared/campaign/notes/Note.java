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
package net.alteiar.server.shared.campaign.notes;

import java.rmi.RemoteException;

import net.alteiar.server.shared.observer.campaign.notes.NotesObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Note extends NotesObservableRemote implements INoteRemote {
	private static final long serialVersionUID = 1L;

	private final String title;
	private String text;

	public Note(String title, String text) throws RemoteException {
		super();
		this.title = title;
		this.text = text;
	}

	@Override
	public String getTitle() throws RemoteException {
		return this.title;
	}

	@Override
	public String getText() throws RemoteException {
		return this.text;
	}

	@Override
	public void setText(String text) throws RemoteException {
		this.text = text;
		this.notifyNotesModify(text);
	}
}
