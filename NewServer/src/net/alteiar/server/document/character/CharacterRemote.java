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

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.DocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterRemote extends DocumentRemote implements ICharacterRemote {
	private static final long serialVersionUID = 1L;

	private final String name;

	private final Integer ac;
	private final Integer acFlatFooted;
	private final Integer acTouch;

	private final Integer initModifier;

	private final Integer hpTotal;
	private Integer hpCurrent;

	private final Float width;
	private final Float height;

	private final Long imageId;

	public CharacterRemote(String name, Integer ac, Integer acFlatFooted,
			Integer acTouch, Integer initModifier, Integer hpTotal,
			Integer hpCurrent, Float width, Float height, Long imageId)
			throws RemoteException {
		super();
		this.name = name;
		this.ac = ac;
		this.acFlatFooted = acFlatFooted;
		this.acTouch = acTouch;
		this.initModifier = initModifier;
		this.hpTotal = hpTotal;
		this.hpCurrent = hpCurrent;
		this.width = width;
		this.height = height;
		this.imageId = imageId;
	}

	@Override
	public Long getImage() throws RemoteException {
		return imageId;
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	@Override
	public Integer getHp() throws RemoteException {
		return this.hpTotal;
	}

	@Override
	public Integer getCurrentHp() throws RemoteException {
		return this.hpCurrent;
	}

	@Override
	public Integer getAc() throws RemoteException {
		return this.ac;
	}

	@Override
	public Integer getAcFlatFooted() throws RemoteException {
		return acFlatFooted;
	}

	@Override
	public Integer getAcTouch() throws RemoteException {
		return acTouch;
	}

	@Override
	public Integer getInitMod() throws RemoteException {
		return initModifier;
	}

	@Override
	public Float getWidth() throws RemoteException {
		return width;
	}

	@Override
	public Float getHeight() throws RemoteException {
		return height;
	}

	@Override
	public void setCurrentHp(Integer hp) throws RemoteException {
		this.hpCurrent = hp;
		this.notifyCurrentHealthPointChanged(hpCurrent);
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
			super(observable, ICharacterListener.class, observer);
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
