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

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import net.alteiar.ExceptionTool;
import net.alteiar.SerializableFile;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.pcgen.PathfinderCharacterFacade;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.rmi.server.RmiRegistryProxy;
import net.alteiar.server.shared.campaign.battle.BattleRemote;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.character.CharacterRemote;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.chat.ChatRoomRemote;
import net.alteiar.server.shared.campaign.chat.IChatRoomRemote;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.campaign.notes.Note;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;
import net.alteiar.server.shared.campaign.player.PlayerAccess;
import net.alteiar.server.shared.campaign.player.PlayerRemote;
import net.alteiar.server.shared.observer.campaign.CampaignObservableRemote;
import net.alteiar.shared.tool.SynchronizedList;
import net.alteiar.thread.TaskInfoAdapter;
import net.alteiar.thread.WorkerPool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ServerCampaign extends CampaignObservableRemote implements
		IServerCampaign {

	private static final long serialVersionUID = 731240477472043798L;

	public static String CAMPAIGN_MANAGER = "Campaign-manager";
	public static String MEDIA_MANAGER = "media-manager";

	public static IMediaManagerRemote MEDIA_MANAGER_REMOTE = null;
	public static ServerCampaign SERVER_CAMPAIGN_REMOTE = null;

	public final static WorkerPool SERVER_THREAD_POOL = new WorkerPool();

	private final ConcurrentHashMap<Long, IBattleRemote> allBattles;
	private final SynchronizedList<IPlayerRemote> allPlayers;
	private final ConcurrentHashMap<Long, ICharacterRemote> allCharacters;
	private final ConcurrentHashMap<Long, ICharacterRemote> allMonster;
	private final SynchronizedList<INoteRemote> allNotes;
	private final IChatRoomRemote chatRoom;

	public static void startServer(String addressIp, int port) {
		SERVER_THREAD_POOL.initWorkPool(20, new TaskInfoAdapter());

		try {
			RmiRegistryProxy.startRmiRegistryProxy(addressIp, port);

			SERVER_CAMPAIGN_REMOTE = new ServerCampaign();
			MEDIA_MANAGER_REMOTE = new MediaManagerRemote();

			LoggerConfig.SERVER_LOGGER.log(Level.INFO, "server adress = //"
					+ addressIp + ":" + port + "/" + CAMPAIGN_MANAGER);

			// Bind the server in the rmi registry
			RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
					+ CAMPAIGN_MANAGER, SERVER_CAMPAIGN_REMOTE);

			RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
					+ MEDIA_MANAGER, MEDIA_MANAGER_REMOTE);
		} catch (MalformedURLException e) {
			ExceptionTool.showError(e);
		} catch (NotBoundException e) {
			ExceptionTool.showError(e);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public List<IPlayerRemote> getAllPlayer() throws RemoteException {
		return allPlayers.getUnmodifiableList();
	}

	/**
	 * @throws RemoteException
	 */
	protected ServerCampaign() throws RemoteException {
		super();
		allBattles = new ConcurrentHashMap<Long, IBattleRemote>();
		allPlayers = new SynchronizedList<IPlayerRemote>();
		allNotes = new SynchronizedList<INoteRemote>();
		allCharacters = new ConcurrentHashMap<Long, ICharacterRemote>();
		allMonster = new ConcurrentHashMap<Long, ICharacterRemote>();
		chatRoom = new ChatRoomRemote();
	}

	// ////////// PLAYER METHODS ///////////////////////
	@Override
	public IPlayerRemote createPlayer(String name, Boolean isMj)
			throws RemoteException {
		for (IPlayerRemote player : allPlayers.getUnmodifiableList()) {
			if (player.getName().equals(name)) {
				name += "1";
			}
		}
		IPlayerRemote player = new PlayerRemote(name, isMj);
		allPlayers.add(player);
		this.notifyPlayerAdded(player);
		return player;
	}

	@Override
	public void disconnectPlayer(String name) throws RemoteException {
		IPlayerRemote removed = null;
		for (IPlayerRemote player : allPlayers.getUnmodifiableList()) {
			if (player.getName().equals(name)) {
				removed = player;
				break;
			}
		}

		allPlayers.remove(removed);
		this.notifyPlayerRemoved(removed.getId());
	}

	// ////////// BATTLES METHODS ///////////////////////
	@Override
	public List<IBattleRemote> getBattles() throws RemoteException {
		return new ArrayList<IBattleRemote>(allBattles.values());
	}

	@Override
	public void createBattle(String name, SerializableFile background,
			Scale scale) throws RemoteException {
		LoggerConfig.SERVER_LOGGER.log(Level.INFO,
				"Create a new Battle on server");

		// force unique name
		int idx = 0;
		boolean haveChanged = true;
		String originalName = name;
		while (haveChanged) {
			haveChanged = false;
			for (IBattleRemote charac : allBattles.values()) {
				if (charac.getName().equals(name)) {
					idx++;
					haveChanged = true;
					break;
				}
			}
			if (idx > 0) {
				name = originalName + idx;
			}
		}

		IBattleRemote battle = new BattleRemote(name, background, scale);
		allBattles.put(battle.getId(), battle);
		this.notifyBattleAdded(battle);
	}

	@Override
	public void removeBattle(Long battleId) throws RemoteException {
		allBattles.remove(battleId);
		this.notifyBattleRemoved(battleId);
	}

	// ////////// CHARACTERS METHODS ///////////////////////
	private PlayerAccess createPlayerAccess(IPlayerRemote player)
			throws RemoteException {
		PlayerAccess playerAccess = new PlayerAccess(true, false);
		playerAccess.addPlayer(player.getId());
		return playerAccess;
	}

	@Override
	public void createCharacter(IPlayerRemote player,
			PathfinderCharacterFacade character) throws RemoteException {

		ICharacterRemote characterSheet = new CharacterRemote(character);
		characterSheet.setPlayerAccess(createPlayerAccess(player));

		// force unique name
		int idx = 0;
		boolean haveChanged = true;
		String originalName = character.getName();
		while (haveChanged) {
			haveChanged = false;
			for (ICharacterRemote charac : allCharacters.values()) {
				if (charac.getCharacterFacade().getName()
						.equals(character.getName())) {
					idx++;
					haveChanged = true;
					break;
				}
			}
			if (idx > 0) {
				character.setName(originalName + idx);
			}
		}

		this.allCharacters.put(characterSheet.getId(), characterSheet);

		this.notifyCharacterAdded(characterSheet);
	}

	@Override
	public List<ICharacterRemote> getAllCharacters() throws RemoteException {
		return new ArrayList<ICharacterRemote>(this.allCharacters.values());
	}

	public ICharacterRemote getCharater(Long id) {
		return this.allCharacters.get(id);
	}

	public ICharacterRemote getMonster(Long id) {
		return this.allMonster.get(id);
	}

	// ////////// MONSTERS METHODS ///////////////////////
	@Override
	public void createMonster(PathfinderCharacterFacade character)
			throws RemoteException {
		ICharacterRemote characterSheet = new CharacterRemote(character);

		// force unique name
		int idx = 0;
		boolean haveChanged = true;
		String originalName = character.getName();
		while (haveChanged) {
			haveChanged = false;
			for (ICharacterRemote charac : allMonster.values()) {
				if (charac.getCharacterFacade().getName()
						.equals(character.getName())) {
					idx++;
					haveChanged = true;
					break;
				}
			}
			if (idx > 0) {
				character.setName(originalName + idx);
			}
		}

		this.allMonster.put(characterSheet.getId(), characterSheet);
		this.notifyMonsterAdded(characterSheet);
	}

	@Override
	public void removeMonster(Long monsterId) throws RemoteException {
		this.allMonster.remove(monsterId);
		this.notifyMonsterRemoved(monsterId);
	}

	@Override
	public List<ICharacterRemote> getAllMonsters() throws RemoteException {
		return new ArrayList<ICharacterRemote>(this.allMonster.values());
	}

	// ////////// CHAT METHODS ///////////////////////
	@Override
	public IChatRoomRemote getChat() throws RemoteException {
		return this.chatRoom;
	}

	// ////////// NOTES METHODS ///////////////////////
	@Override
	public void createNote(String title, String text) throws RemoteException {
		INoteRemote note = new Note(title, text);
		this.allNotes.add(note);
		this.notifyNoteAdded(note);
	}

	@Override
	public List<INoteRemote> getAllNotes() throws RemoteException {
		return new ArrayList<INoteRemote>(allNotes.getUnmodifiableList());
	}

	@Override
	public void removeCharacter(Long characterId) throws RemoteException {
		this.allCharacters.remove(characterId);
		this.notifyCharacterRemoved(characterId);
	}

}
