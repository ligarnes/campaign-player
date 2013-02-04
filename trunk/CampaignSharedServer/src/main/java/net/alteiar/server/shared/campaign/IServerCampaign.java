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
package net.alteiar.server.shared.campaign;

import java.rmi.RemoteException;
import java.util.List;

import net.alteiar.SerializableFile;
import net.alteiar.pcgen.PathfinderCharacterFacade;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.chat.IChatRoomRemote;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;
import net.alteiar.server.shared.observer.IGUIDRemote;
import net.alteiar.server.shared.observer.campaign.ICampaignObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IServerCampaign extends IGUIDRemote {

	// Listeners
	void addCampaignListener(ICampaignObserverRemote listener)
			throws RemoteException;

	void removeCampaignListener(ICampaignObserverRemote listener)
			throws RemoteException;

	// Notes
	void createNote(String title, String text) throws RemoteException;

	List<INoteRemote> getAllNotes() throws RemoteException;

	// Players
	IPlayerRemote createPlayer(String name, Boolean isMj)
			throws RemoteException;

	void disconnectPlayer(String name) throws RemoteException;

	List<IPlayerRemote> getAllPlayer() throws RemoteException;

	// Chat
	IChatRoomRemote getChat() throws RemoteException;

	// Battles
	void createBattle(String name, SerializableFile background, Scale scale)
			throws RemoteException;

	void removeBattle(Long battleId) throws RemoteException;

	List<IBattleRemote> getBattles() throws RemoteException;

	// Characters
	List<ICharacterRemote> getAllCharacters() throws RemoteException;

	void createCharacter(IPlayerRemote player,
			PathfinderCharacterFacade character) throws RemoteException;

	void removeCharacter(Long characterId) throws RemoteException;

	// Monsters
	List<ICharacterRemote> getAllMonsters() throws RemoteException;

	void createMonster(PathfinderCharacterFacade character)
			throws RemoteException;

	void removeMonster(Long monsterId) throws RemoteException;

}
