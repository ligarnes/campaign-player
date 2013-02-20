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
package net.alteiar.server.document.character;

import java.rmi.RemoteException;

import net.alteiar.client.DocumentClient;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterRemote extends DocumentRemote implements ICharacterRemote {
	private static final long serialVersionUID = 1L;

	private final PathfinderCharacterFacade character;
	private final Long imageId;

	public CharacterRemote(PathfinderCharacterFacade character, Long imgId)
			throws RemoteException {
		super();
		this.character = character;
		imageId = imgId;
	}

	@Override
	public PathfinderCharacterFacade getCharacterFacade()
			throws RemoteException {
		return character;
	}

	@Override
	public Long getImage() throws RemoteException {
		return imageId;
	}

	@Override
	public void setCurrentHp(Integer hp) throws RemoteException {
		this.character.setCurrentHp(hp);
		this.notifyCurrentHealthPointChanged(hp);
	}

	@Override
	public DocumentClient<ICharacterRemote> buildProxy() throws RemoteException {
		return new CharacterClient(this);
	}

	// Observable functions
	@Override
	public void addCharacterListener(ICharacterListener map)
			throws RemoteException {
		this.addListener(ICharacterListener.class, map);
	}

	@Override
	public void removeCharacterListener(ICharacterListener map)
			throws RemoteException {
		this.removeListener(ICharacterListener.class, map);
	}

	protected void notifyCurrentHealthPointChanged(Integer currentHp) {
		for (ICharacterListener observer : this
				.getListener(ICharacterListener.class)) {

			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new CharacterChangedTask(this, observer, currentHp));
		}
	}

	private class CharacterChangedTask extends BaseNotify<ICharacterListener> {
		private final Integer currentHp;

		public CharacterChangedTask(CharacterRemote observable,
				ICharacterListener observer, Integer hp) {
			super(observable, observer);
			this.currentHp = hp;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.currentHealthPointChanged(currentHp);
		}

		@Override
		public String getStartText() {
			return "start notify character changed";
		}

		@Override
		public String getFinishText() {
			return "finish notify character changed";
		}
	}

	@Override
	public void closeDocument() throws RemoteException {
		// TODO remove listener
	}
}
