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
import java.rmi.RemoteException;

import net.alteiar.client.DocumentClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterClient extends DocumentClient<ICharacterRemote> implements
		ICharacterSheetClient {
	private static final long serialVersionUID = 1L;

	private String name;
	private Long imageId;

	private Integer ac;
	private Integer acFlatFooted;
	private Integer acTouch;

	private Integer initModifier;

	private Integer hpTotal;
	private Integer hpCurrent;

	private Double width;
	private Double height;

	public CharacterClient(ICharacterRemote characterSheet)
			throws RemoteException {
		super(characterSheet);

		try {
			// new CharacterSheetRemoteObserver(this, characterSheet);

			PathfinderCharacterFacade facade = getRemote().getCharacterFacade();
			name = facade.getName();
			ac = facade.getAc();
			acFlatFooted = facade.getAcFlatFooted();
			acTouch = facade.getAcTouch();
			initModifier = facade.getInitMod();

			hpTotal = facade.getHp();
			hpCurrent = facade.getCurrentHp();

			width = facade.getWidth().doubleValue();
			height = facade.getHeight().doubleValue();

			imageId = getRemote().getImage();
		} catch (RemoteException ex) {
			// TODO
			ex.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getTotalHp() {
		return this.hpTotal;
	}

	@Override
	public Integer getCurrentHp() {
		return this.hpCurrent;
	}

	@Override
	public Integer getAc() {
		return this.ac;
	}

	@Override
	public Integer getAcFlatFooted() {
		return this.acFlatFooted;
	}

	@Override
	public Integer getAcTouch() {
		return this.acTouch;
	}

	@Override
	public Integer getInitModifier() {
		return this.initModifier;
	}

	@Override
	public BufferedImage getBackground() {
		return null; // CampaignClient.getInstance().getDocument(imageId);
	}

	@Override
	public void setCurrentHp(Integer hp) {
		try {
			getRemote().setCurrentHp(hp);
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public void setLocalCurrentHp(Integer hp) {
		this.hpCurrent = hp;
		this.notifyCharacterModify(this);
	}

	@Override
	public Double getWidth() {
		return width;
	}

	@Override
	public Double getHeight() {
		return height;
	}

	@Override
	protected void closeDocument() throws RemoteException {

	}

	// OBSERVABLE METHODS
	@Override
	public void addCharacterListener(ICharacterClientObserver listener) {
		this.addListener(ICharacterClientObserver.class, listener);
	}

	@Override
	public void removeCharacterListener(ICharacterClientObserver listener) {
		this.removeListener(ICharacterClientObserver.class, listener);
	}

	protected void notifyCharacterModify(ICharacterSheetClient character) {
		for (ICharacterClientObserver observer : this
				.getListener(ICharacterClientObserver.class)) {
			observer.characterChanged(character);
		}
	}

	protected void notifyImageLoaded(ICharacterSheetClient character) {
		for (ICharacterClientObserver observer : this
				.getListener(ICharacterClientObserver.class)) {
			observer.imageLoaded(character);
		}
	}
}
