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
package net.alteiar.server.shared.observer.campaign.notes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class NotesObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = 1L;

	private static final Class<?> LISTENER = INotesObserverRemote.class;

	public NotesObservableRemote() throws RemoteException {
		super();
	}

	public void addNoteListener(INotesObserverRemote listener)
			throws RemoteException {
		this.addListener(LISTENER, listener);
	}

	public void removeNoteListener(INotesObserverRemote listener)
			throws RemoteException {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyNotesModify(String notes) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new NotesModifyTask(this,
					observer, notes));
		}
	}

	private class NotesModifyTask extends BaseNotify {
		private final String text;

		public NotesModifyTask(NotesObservableRemote observable,
				Remote observer, String text) {
			super(observable, LISTENER, observer);
			this.text = text;
		}

		@Override
		protected void doAction() throws RemoteException {
			((INotesObserverRemote) observer).notesModify(text);
		}

		@Override
		public String getStartText() {
			return "start notify notes modify";
		}

		@Override
		public String getFinishText() {
			return "finish notify notes modify";
		}
	}
}
