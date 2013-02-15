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
package net.alteiar.client.shared.campaign.character;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.IMediaManagerClient;
import net.alteiar.client.shared.observer.campaign.character.CharacterClientObservable;
import net.alteiar.pcgen.PathfinderCharacter;
import net.alteiar.pcgen.PathfinderCharacterFacade;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.player.PlayerAccess;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterSheetClient extends CharacterClientObservable implements
		ICharacterSheetClient {
	private static final long serialVersionUID = 1L;

	private PlayerAccess access;

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

	private String htmlCharacterSheet;

	public CharacterSheetClient(ICharacterRemote characterSheet) {
		super(characterSheet);

		try {
			new CharacterSheetRemoteObserver(this, characterSheet);

			PathfinderCharacterFacade facade = remoteObject
					.getCharacterFacade();
			name = facade.getName();
			ac = facade.getAc();
			acFlatFooted = facade.getAcFlatFooted();
			acTouch = facade.getAcTouch();
			initModifier = facade.getInitMod();

			hpTotal = facade.getHp();
			hpCurrent = facade.getCurrentHp();

			width = facade.getWidth().doubleValue();
			height = facade.getHeight().doubleValue();

			access = remoteObject.getPlayerAccess();
			imageId = remoteObject.getImage();
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public PlayerAccess getAccess() {
		return this.access;
	}

	@Override
	public void setAccess(PlayerAccess access) {
		try {
			this.remoteObject.setPlayerAccess(access);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
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

	private IMediaManagerClient getMediaManager() {
		return CampaignClient.INSTANCE.getMediaManager();
	}

	private BufferedImage getBackgroundImage() {
		if (this.imageId < 0) {
			getMediaManager().getDefaultImage();
		}
		return getMediaManager().getImage(this.imageId);
	}

	@Override
	public BufferedImage getBackground() {
		BufferedImage img = getBackgroundImage();
		if (img == null) {
			Observer o = new ImageObserver(this);
			getMediaManager().addListener(o);
			img = getBackgroundImage();
			if (img != null) {
				getMediaManager().removeListener(o);
			} else {
				img = getMediaManager().getDefaultImage();
			}
		}
		return img;
	}

	@Override
	public void setCurrentHp(Integer hp) {
		try {
			this.remoteObject.setCurrentHp(hp);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void setLocalCurrentHp(Integer hp) {
		this.hpCurrent = hp;
		this.notifyCharacterModify(this);
	}

	public void setInternalAccess(PlayerAccess access) {
		this.access = access;
		this.notifyPlayerAccessModify(this.access);
	}

	@Override
	public String getHtmlCharacterSheet() {
		return htmlCharacterSheet;
	}

	private class ImageObserver implements Observer {
		private final CharacterSheetClient character;

		public ImageObserver(CharacterSheetClient character) {
			this.character = character;
		}

		@Override
		public void update(Observable o, Object arg) {
			if (getMediaManager().getImage(imageId) != null) {
				getMediaManager().removeListener(this);
				notifyImageLoaded(character);
			}
		}
	}

	@Override
	public PathfinderCharacter convert() {
		PathfinderCharacter character = new PathfinderCharacter();
		character.setName(getName());

		character.setHtmlCharacter(getHtmlCharacterSheet());

		character.setInitMod(getInitModifier());

		character.setAc(getAc());
		character.setAcFlatFooted(getAcFlatFooted());
		character.setAcTouch(getAcTouch());

		character.setHp(getTotalHp());

		character.setWidth(getWidth().floatValue());
		character.setHeight(getHeight().floatValue());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(getBackground(), "png", baos);
			baos.flush();
			byte[] imageBytes = baos.toByteArray();
			baos.close();

			character.setImage(imageBytes);
		} catch (IOException e) {
			ExceptionTool.showError(e, "impossible de sauvegarder l'image");
		}

		return character;
	}

	@Override
	public Double getWidth() {
		return width;
	}

	@Override
	public Double getHeight() {
		return height;
	}
}
