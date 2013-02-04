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
package net.alteiar.server.shared.campaign.battle;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.observer.campaign.battle.CharacterCombatObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CharacterCombatRemote extends CharacterCombatObservableRemote
		implements ICharacterCombatRemote {
	private static final long serialVersionUID = 7644790326707927587L;

	private final ICharacterRemote characterSheet;
	private Integer initative;
	private Boolean isVisibleForPlayer;

	private Point position;
	private Double angle;

	public CharacterCombatRemote(ICharacterRemote characterSheet)
			throws RemoteException {
		this(characterSheet, new Point());
	}

	public CharacterCombatRemote(ICharacterRemote characterSheet, Point position)
			throws RemoteException {
		super();
		initative = 10;
		this.characterSheet = characterSheet;
		isVisibleForPlayer = true;

		angle = 0.0;
		this.position = position;
	}

	@Override
	public void setVisibleForPlayer(Boolean isVisible) throws RemoteException {
		isVisibleForPlayer = isVisible;
		this.notifyCharacterVisibilityChange(isVisibleForPlayer);
	}

	@Override
	public Boolean isVisibleForPlayer() throws RemoteException {
		return isVisibleForPlayer;
	}

	public ICharacterRemote getCharacterSheet() throws RemoteException {
		return this.characterSheet;
	}

	@Override
	public Long getCharacterSheetGuid() throws RemoteException {
		return characterSheet.getId();
	}

	/**
	 * 
	 * @param init
	 *            - the result get by the dice all modifier will be added
	 * @throws RemoteException
	 */
	@Override
	public void setInitiative(Integer init) throws RemoteException {
		initative = init + characterSheet.getCharacterFacade().getInitMod();
		this.notifyCharacterInitiativeChanged(initative);
	}

	/**
	 * 
	 * @return final initiative result with all modifier applied
	 * @throws RemoteException
	 */
	@Override
	public Integer getInitiative() throws RemoteException {
		return initative;
	}

	@Override
	public Point getPosition() throws RemoteException {
		return position;
	}

	@Override
	public void setPosition(Point position) throws RemoteException {
		this.position = position;
		this.notifyCharacterMovedChanged(position);
	}

	@Override
	public Double getAngle() throws RemoteException {
		return this.angle;
	}

	@Override
	public void setAngle(Double angle) throws RemoteException {
		this.angle = angle;
		this.notifyCharacterRotationChanged(angle);
	}

}
