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

import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.battle.map.element.IMapElementRemote;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.observer.IGUIDRemote;
import net.alteiar.server.shared.observer.campaign.battle.ICharacterCombatObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface ICharacterCombatRemote extends IGUIDRemote, IMapElementRemote {

	void addCharacterCombatListener(ICharacterCombatObserverRemote map)
			throws RemoteException;

	void removeCharacterCombatListener(ICharacterCombatObserverRemote map)
			throws RemoteException;

	ICharacterRemote getCharacterSheet() throws RemoteException;

	Long getCharacterSheetGuid() throws RemoteException;

	/**
	 * 
	 * @param init
	 *            - the result get by the dice all modifier wil be added
	 * @throws RemoteException
	 */
	void setInitiative(Integer init) throws RemoteException;

	/**
	 * 
	 * @return final initiative result with all modifier applyed
	 * @throws RemoteException
	 */
	Integer getInitiative() throws RemoteException;

	void setVisibleForPlayer(Boolean isVisible) throws RemoteException;

	Boolean isVisibleForPlayer() throws RemoteException;
}
