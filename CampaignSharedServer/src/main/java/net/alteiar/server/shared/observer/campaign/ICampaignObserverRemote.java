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
package net.alteiar.server.shared.observer.campaign;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface ICampaignObserverRemote extends Remote {
	void playerAdded(IPlayerRemote player) throws RemoteException;

	void playerRemoved(Long playerId) throws RemoteException;

	void noteAdded(INoteRemote note) throws RemoteException;

	void noteRemoved(INoteRemote note) throws RemoteException;

	void battleAdded(IBattleRemote battle) throws RemoteException;

	void battleRemoved(Long battleId) throws RemoteException;

	void characterAdded(ICharacterRemote character) throws RemoteException;

	void characterRemoved(Long characterId) throws RemoteException;

	void monsterAdded(ICharacterRemote character) throws RemoteException;

	void monsterRemoved(Long monsterId) throws RemoteException;
}
