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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.files.ImageClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterClient extends DocumentClient<ICharacterRemote> {
	private static final long serialVersionUID = 1L;

	private transient CharacterClientListener listener;

	private final String name;
	private final Long imageId;

	private final Integer ac;
	private final Integer acFlatFooted;
	private final Integer acTouch;

	private final Integer initModifier;

	private final Integer hpTotal;
	private Integer hpCurrent;

	private final Double width;
	private final Double height;

	public CharacterClient(ICharacterRemote characterSheet)
			throws RemoteException {
		super(characterSheet);

		name = getRemote().getName();
		ac = getRemote().getAc();
		acFlatFooted = getRemote().getAcFlatFooted();
		acTouch = getRemote().getAcTouch();
		initModifier = getRemote().getInitMod();

		hpTotal = getRemote().getHp();
		hpCurrent = getRemote().getCurrentHp();

		width = getRemote().getWidth().doubleValue();
		height = getRemote().getHeight().doubleValue();

		imageId = getRemote().getImage();
	}

	public String getName() {
		return this.name;
	}

	public Integer getTotalHp() {
		return this.hpTotal;
	}

	public Integer getCurrentHp() {
		return this.hpCurrent;
	}

	public Integer getAc() {
		return this.ac;
	}

	public Integer getAcFlatFooted() {
		return this.acFlatFooted;
	}

	public Integer getAcTouch() {
		return this.acTouch;
	}

	public Integer getInitModifier() {
		return this.initModifier;
	}

	public BufferedImage getImage() {
		return ((ImageClient) CampaignClient.getInstance().getDocument(imageId))
				.getImage();
	}

	public void setCurrentHp(Integer hp) {
		try {
			getRemote().setCurrentHp(hp);
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public Double getWidth() {
		return width;
	}

	public Double getHeight() {
		return height;
	}

	@Override
	protected void closeDocument() throws RemoteException {
		this.getRemote().removeCharacterListener(listener);
	}

	@Override
	protected void loadDocumentLocal(File file) throws IOException {
		// nothing
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		listener = new CharacterClientListener();
		getRemote().addCharacterListener(listener);
	}

	protected void setLocalCurrentHp(Integer hp) {
		this.hpCurrent = hp;
		this.notifyCharacterModify(this);
	}

	// LISTENER METHODS
	public void addCharacterListener(ICharacterClientListener listener) {
		this.addListener(ICharacterClientListener.class, listener);
	}

	public void removeCharacterListener(ICharacterClientListener listener) {
		this.removeListener(ICharacterClientListener.class, listener);
	}

	protected void notifyCharacterModify(CharacterClient character) {
		for (ICharacterClientListener observer : this
				.getListener(ICharacterClientListener.class)) {
			observer.characterChanged(character);
		}
	}

	protected void notifyImageLoaded(CharacterClient character) {
		for (ICharacterClientListener observer : this
				.getListener(ICharacterClientListener.class)) {
			observer.imageLoaded(character);
		}
	}

	private class CharacterClientListener extends UnicastRemoteObject implements
			ICharacterListener {
		private static final long serialVersionUID = 1L;

		public CharacterClientListener() throws RemoteException {
			super();
		}

		@Override
		public void currentHealthPointChanged(Integer currentHp)
				throws RemoteException {
			setLocalCurrentHp(currentHp);
		}
	}
}
