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
package net.alteiar.server.shared.campaign.character;

import java.rmi.RemoteException;

import net.alteiar.SerializableFile;
import net.alteiar.pcgen.PathfinderCharacterFacade;
import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.campaign.player.PlayerAccess;
import net.alteiar.server.shared.observer.campaign.character.CharacterSheetObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterRemote extends CharacterSheetObservableRemote implements
		ICharacterRemote {
	private static final long serialVersionUID = 1L;

	private PlayerAccess playerAccess;

	private final PathfinderCharacterFacade character;
	private Long imageId;

	private CharacterRemote(PathfinderCharacterFacade character, Long imgId)
			throws RemoteException {
		super();
		this.character = character;
		imageId = imgId;
		playerAccess = new PlayerAccess(true, false);
	}

	public CharacterRemote(PathfinderCharacterFacade character)
			throws RemoteException {
		super();
		// remove the bytes of the image from the object
		this.character = character;// character.FastCopy(character.getName());

		// no id are below 0 so use the -1 id if no image found
		if (character.getImage() == null) {
			imageId = -1L;
		} else {
			imageId = ServerCampaign.MEDIA_MANAGER_REMOTE
					.addImage(new SerializableFile(character.getImage()));
		}
		playerAccess = new PlayerAccess();
	}

	@Override
	public PathfinderCharacterFacade getCharacterFacade()
			throws RemoteException {
		return character;
	}

	@Override
	public ICharacterRemote fastCopy(String newName) throws RemoteException {
		return new CharacterRemote(this.character.FastCopy(newName),
				this.imageId);
	}

	@Override
	public PlayerAccess getPlayerAccess() {
		return playerAccess;
	}

	@Override
	public void setPlayerAccess(PlayerAccess access) {
		playerAccess = access;
		this.notifyPlayerAccessChanged(access);
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
}
