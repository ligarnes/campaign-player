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
package net.alteiar.client.shared.campaign;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.battle.BattleClient;
import net.alteiar.client.shared.campaign.battle.CreateBattleTask;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.CharacterSheetClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.chat.ChatRoomClient;
import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.campaign.player.IObjectPlayerAccess;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.client.shared.campaign.player.PlayerClient;
import net.alteiar.client.shared.observer.campaign.CampaignObservable;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.pcgen.PathfinderCharacterFacade;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.shared.campaign.IMediaManagerRemote;
import net.alteiar.server.shared.campaign.IServerCampaign;
import net.alteiar.server.shared.campaign.battle.IBattleRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.character.ICharacterRemote;
import net.alteiar.server.shared.campaign.chat.MessageRemote;
import net.alteiar.server.shared.campaign.notes.INoteRemote;
import net.alteiar.server.shared.campaign.player.IPlayerRemote;
import net.alteiar.server.shared.observer.campaign.ICampaignObserverRemote;
import net.alteiar.shared.tool.SynchronizedHashMap;
import net.alteiar.thread.Task;
import net.alteiar.thread.TaskInfo;
import net.alteiar.thread.WorkerPool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CampaignClient extends CampaignObservable implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static WorkerPool WORKER_POOL_CAMPAIGN = new WorkerPool();
	public static CampaignClient INSTANCE = null;

	private final SynchronizedHashMap<Long, IBattleClient> allBattles;
	private final SynchronizedHashMap<Long, IPlayerClient> allPlayer;
	private final List<NoteClient> allNotes;
	private final SynchronizedHashMap<Long, ICharacterSheetClient> allCharacters;
	private final SynchronizedHashMap<Long, ICharacterSheetClient> allMonster;

	private final MediaManagerClient mediaManager;

	private ChatRoomClient chatRoom;

	private PlayerClient currentPlayer;

	public static final void createClientCampaign(int numberOfThread,
			TaskInfo info) {
		WORKER_POOL_CAMPAIGN.initWorkPool(numberOfThread, info);
	}

	public static void connect(String localAdress, String address, String port,
			String name, Boolean isMj) {
		IServerCampaign campaign = null;
		MediaManagerClient mediaManager = null;

		Remote remoteObject;
		try {
			System.setProperty("java.rmi.server.hostname", localAdress);
			String[] allRemoteNames = RmiRegistry.list("//" + address + ":"
					+ port);

			for (String remoteName : allRemoteNames) {
				try {
					remoteObject = RmiRegistry.lookup(remoteName);
					if (remoteObject instanceof IServerCampaign) {
						LoggerConfig.CLIENT_LOGGER.log(Level.INFO,
								"RMI REGISTRY: " + remoteName);

						campaign = (IServerCampaign) remoteObject;
					}
					if (remoteObject instanceof IMediaManagerRemote) {
						LoggerConfig.CLIENT_LOGGER.log(Level.INFO,
								"RMI REGISTRY: " + remoteName);

						mediaManager = new MediaManagerClient(
								(IMediaManagerRemote) remoteObject);
					}
				} catch (RemoteException e) {
					ExceptionTool.showError(e);
				}
			}

		} catch (MalformedURLException e) {
			ExceptionTool.showError(e);
		} catch (NotBoundException e) {
			ExceptionTool.showError(e);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
		INSTANCE = new CampaignClient(campaign, mediaManager, name, isMj);
		try {
			INSTANCE.loadCampaign(name, isMj);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
		INSTANCE.getChatRoom().talk(INSTANCE.getCurrentPlayer().getName(),
				MessageRemote.SYSTEM_CONNECT_MESSAGE);

	}

	private CampaignClient(IServerCampaign campaign,
			MediaManagerClient mediaManager, String name, Boolean isMj) {
		super(campaign);
		allBattles = new SynchronizedHashMap<Long, IBattleClient>();
		allPlayer = new SynchronizedHashMap<Long, IPlayerClient>();
		allNotes = new ArrayList<NoteClient>();
		allCharacters = new SynchronizedHashMap<Long, ICharacterSheetClient>();
		allMonster = new SynchronizedHashMap<Long, ICharacterSheetClient>();

		this.mediaManager = mediaManager;

		// create current player
		try {
			currentPlayer = new PlayerClient(remoteObject.createPlayer(name,
					isMj));
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void disconnect() {
		try {
			chatRoom.talk(currentPlayer.getName(),
					MessageRemote.SYSTEM_DISCONNECT_MESSAGE);
			this.remoteObject.disconnectPlayer(currentPlayer.getName());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	/**
	 * 
	 * @param name
	 * @param isMj
	 * @throws RemoteException
	 * @throws NullPointerException
	 *             - if the campaign is null
	 */
	private void loadCampaign(String name, boolean isMj) throws RemoteException {
		// first create campaign observer
		new ClientCampaignObserver(remoteObject);

		// we load the chat
		chatRoom = new ChatRoomClient(remoteObject.getChat());

		// we load all player
		for (IPlayerRemote player : remoteObject.getAllPlayer()) {
			PlayerClient client = new PlayerClient(player);
			this.allPlayer.put(client.getId(), client);
		}

		// we load all character sheet
		Collection<ICharacterRemote> allCharacterSheet = remoteObject
				.getAllCharacters();
		for (ICharacterRemote character : allCharacterSheet) {
			ICharacterSheetClient client = new CharacterSheetClient(character);
			this.allCharacters.put(client.getId(), client);
		}

		// we load all battles
		for (IBattleRemote battle : remoteObject.getBattles()) {
			campaignBattleAdded(battle);
		}

		// we load all notes
		/*
		Collection<INoteRemote> allNotesRemote = remoteObject.getAllNotes();
		for (INoteRemote notes : allNotesRemote) {
			NoteClient client = new NoteClient(notes);
			this.allNotes.add(client);
		}
		*/
	}

	public IMediaManagerClient getMediaManager() {
		return mediaManager;
	}

	public IPlayerClient getCurrentPlayer() {
		return this.currentPlayer;
	}

	public Boolean canAccess(IObjectPlayerAccess access) {
		return access.getAccess().canAcces(currentPlayer.getId(),
				currentPlayer.isMj());
	}

	public ChatRoomClient getChatRoom() {
		return this.chatRoom;
	}

	/*
	public void createNotes(String title, String text) {
		try {
			remoteObject.createNote(title, text);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}*/

	public List<NoteClient> getAllNotes() {
		return this.allNotes;
	}

	public void createBattle(String battleName, File background, Scale scale) {
		WORKER_POOL_CAMPAIGN.addTask(new CreateBattleTask(remoteObject,
				battleName, background, scale));
	}

	public void createCharacter(final PathfinderCharacterFacade newCharacter) {
		Task create = new Task() {
			@Override
			public void run() {
				try {
					remoteObject.createCharacter(
							currentPlayer.getRemoteReference(), newCharacter);
				} catch (RemoteException e) {
					ExceptionTool.showError(e);
				}
			}

			@Override
			public String getStartText() {
				return "Create character";
			}

			@Override
			public String getFinishText() {
				return "Character created";
			}
		};

		CampaignClient.WORKER_POOL_CAMPAIGN.addTask(create);
	}

	public void removeCharacter(ICharacterSheetClient character) {
		try {
			remoteObject.removeCharacter(character.getId());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void removeMonster(ICharacterSheetClient character) {
		try {
			remoteObject.removeMonster(character.getId());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void createMonster(final PathfinderCharacterFacade newCharacter) {
		Task create = new Task() {

			@Override
			public void run() {
				try {
					remoteObject.createMonster(newCharacter);
				} catch (RemoteException e) {
					ExceptionTool.showError(e);
				}
			}

			@Override
			public String getStartText() {
				return "Create monster";
			}

			@Override
			public String getFinishText() {
				return "Monster created";
			}
		};

		CampaignClient.WORKER_POOL_CAMPAIGN.addTask(create);
	}

	public ICharacterSheetClient[] getAllCharacter() {
		this.allCharacters.incCounter();
		ICharacterSheetClient[] characters = new ICharacterSheetClient[allCharacters
				.size()];
		allCharacters.values().toArray(characters);
		this.allCharacters.decCounter();
		return characters;
	}

	public ICharacterSheetClient[] getAllMonster() {
		this.allMonster.incCounter();
		ICharacterSheetClient[] monsters = new ICharacterSheetClient[allMonster
				.size()];
		allMonster.values().toArray(monsters);
		this.allMonster.decCounter();
		return monsters;
	}

	public void removeBattle(IBattleClient battle) {
		try {
			remoteObject.removeBattle(battle.getId());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public IBattleClient[] getBattles() {
		allBattles.incCounter();
		IBattleClient[] battles = new IBattleClient[allBattles.size()];
		allBattles.values().toArray(battles);
		allBattles.decCounter();
		return battles;
	}

	// PLAYER
	public IPlayerClient[] getAllPlayer() {
		allPlayer.incCounter();
		IPlayerClient[] players = new IPlayerClient[allPlayer.size()];
		allPlayer.values().toArray(players);
		allPlayer.decCounter();
		return players;
	}

	public Collection<IPlayerClient> getAllPlayer(List<Long> ids) {
		List<IPlayerClient> players = new ArrayList<IPlayerClient>();
		for (IPlayerClient iPlayerClient : allPlayer.values()) {
			if (ids.contains(iPlayerClient.getId())) {
				players.add(iPlayerClient);
			}
		}
		return players;
	}

	public ICharacterSheetClient getCharacter(Long guid) {
		return allCharacters.get(guid);
	}

	// ////////////PROTECTED METHODS///////////////
	protected void campaignBattleAdded(IBattleRemote battle) {
		BattleClient client = new BattleClient(battle);
		allBattles.put(client.getId(), client);
		this.notifyBattleAdded(client);
	}

	protected void campaignBattleRemoved(Long battleId) {
		IBattleClient client = allBattles.remove(battleId);
		this.notifyBattleRemoved(client);
	}

	protected void campaignPlayerAdded(IPlayerRemote player) {
		PlayerClient client = new PlayerClient(player);
		allPlayer.put(client.getId(), client);
		this.notifyPlayerAdded(client);
	}

	protected void campaignPlayerRemoved(Long playerId) {
		IPlayerClient client = allPlayer.remove(playerId);
		this.notifyPlayerRemoved(client);
	}

	protected void campaignNoteAdded(INoteRemote note) {
		NoteClient client = new NoteClient(note);
		allNotes.add(client);
		this.notifyNoteAdded(client);
	}

	protected void campaignNoteRemoved(INoteRemote note) {
		NoteClient client = new NoteClient(note);
		allNotes.remove(client);
		this.notifyNoteRemoved(client);
	}

	protected void campaignCharacterAdded(ICharacterRemote character) {
		ICharacterSheetClient client = new CharacterSheetClient(character);
		allCharacters.put(client.getId(), client);
		this.notifyCharacterAdded(client);
	}

	protected void campaignCharacterRemoved(Long characterId) {
		ICharacterSheetClient client = allCharacters.remove(characterId);
		this.notifyCharacterRemoved(client);
	}

	protected void campaignMonsterAdded(ICharacterRemote note) {
		ICharacterSheetClient client = new CharacterSheetClient(note);
		allMonster.put(client.getId(), client);
		this.notifyMonsterAdded(client);
	}

	protected void campaignMonsterRemoved(Long monsterId) {
		ICharacterSheetClient client = allMonster.remove(monsterId);
		this.notifyMonsterRemoved(client);
	}

	// ////////////PRIVATE INNER CLASS THAT OBSERVE///////////////
	private class ClientCampaignObserver extends UnicastRemoteObject implements
			ICampaignObserverRemote {

		private static final long serialVersionUID = 3048139095893131937L;

		/**
		 * @throws RemoteException
		 */
		public ClientCampaignObserver(IServerCampaign campaign)
				throws RemoteException {
			super();
			campaign.addCampaignListener(this);
		}

		@Override
		public void battleAdded(IBattleRemote battle) throws RemoteException {

			campaignBattleAdded(battle);
		}

		@Override
		public void battleRemoved(Long battleId) throws RemoteException {
			campaignBattleRemoved(battleId);
		}

		@Override
		public void playerAdded(IPlayerRemote player) throws RemoteException {
			campaignPlayerAdded(player);
		}

		@Override
		public void playerRemoved(Long playerId) throws RemoteException {
			campaignPlayerRemoved(playerId);
		}

		@Override
		public void noteAdded(INoteRemote note) throws RemoteException {
			campaignNoteAdded(note);
		}

		@Override
		public void noteRemoved(INoteRemote note) throws RemoteException {
			campaignNoteRemoved(note);
		}

		@Override
		public void characterAdded(ICharacterRemote character)
				throws RemoteException {
			campaignCharacterAdded(character);
		}

		@Override
		public void characterRemoved(Long characterId) throws RemoteException {
			campaignCharacterRemoved(characterId);
		}

		@Override
		public void monsterAdded(ICharacterRemote character)
				throws RemoteException {
			campaignMonsterAdded(character);
		}

		@Override
		public void monsterRemoved(Long monsterId) throws RemoteException {
			campaignMonsterRemoved(monsterId);
		}
	}
}
