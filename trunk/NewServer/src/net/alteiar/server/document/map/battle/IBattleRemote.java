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
package net.alteiar.server.document.map.battle;

import java.rmi.RemoteException;

import net.alteiar.server.document.map.IMapRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IBattleRemote extends IMapRemote {

	void addBattleListener(IBattleListenerRemote listener)
			throws RemoteException;

	void removeBattleListener(IBattleListenerRemote listener)
			throws RemoteException;

	/*
	void addCharacter(Long id, Integer init, Point position)
			throws RemoteException;

	void addMonster(Long id, Integer init, Point position, Boolean isVisible)
			throws RemoteException;

	void removeCharacter(Long guid) throws RemoteException;

	ICharacterCombatRemote[] getAllCharacter() throws RemoteException;
	*/

	void setInitiativeEachTurn(Boolean isInitEachTurn) throws RemoteException;

	void nextTurn() throws RemoteException;

	Integer getCurrentTurn() throws RemoteException;
}
