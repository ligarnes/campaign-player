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
package net.alteiar.client.shared.campaign.notes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.observer.campaign.notes.NoteObservable;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.observer.campaign.notes.INotesObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class NoteClient extends NoteObservable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String text;

	/**
	 * @param remote
	 */
	public NoteClient(INoteRemote remote) {
		super(remote);
		try {
			new NotesClientRemote(remoteObject);

			title = remoteObject.getTitle();
			text = remoteObject.getText();
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void applyText() {
		try {
			remoteObject.setText(text);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	private void textChanged(String text) {
		this.text = text;
		this.notifyNotesModify(text);
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class NotesClientRemote extends UnicastRemoteObject implements
			INotesObserverRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		public NotesClientRemote(INoteRemote note) throws RemoteException {
			super();
			note.addNoteListener(this);
		}

		@Override
		public void notesModify(String newNote) throws RemoteException {
			textChanged(newNote);
		}
	}
}
