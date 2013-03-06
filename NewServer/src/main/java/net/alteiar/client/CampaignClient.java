package net.alteiar.client;

import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.IServerDocument;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.CompleteCharacter;
import net.alteiar.server.document.character.DocumentCharacterBuilder;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.chat.message.MessageRemote;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.server.document.images.SerializableImage;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.battle.DocumentBattleBuilder;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;
import net.alteiar.server.document.player.DocumentPlayerBuilder;
import net.alteiar.server.document.player.PlayerClient;
import net.alteiar.shared.ExceptionTool;

public class CampaignClient extends DocumentManagerClient {
	private static final long serialVersionUID = 1L;

	private static CampaignClient INSTANCE = null;

	public static void connect(String localAddress, String serverAddress,
			String port, String campaignPath, String name, Boolean isMj) {
		IServerDocument campaign = null;

		Remote remoteObject;
		try {
			System.setProperty("java.rmi.server.hostname", localAddress);
			String[] allRemoteNames = RmiRegistry.list("//" + serverAddress
					+ ":" + port);

			for (String remoteName : allRemoteNames) {
				try {
					remoteObject = RmiRegistry.lookup(remoteName);
					LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "RMI REGISTRY: "
							+ remoteName);
					if (remoteObject instanceof IServerDocument) {
						campaign = (IServerDocument) remoteObject;
						INSTANCE = new CampaignClient(campaign, campaignPath,
								name, isMj);
						break;
					}
				} catch (RemoteException e) {
					ExceptionTool.showError(e);
				}
			}
		} catch (Exception e) {
			ExceptionTool.showError(e);
		}
	}

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	private final PlayerClient currentPlayer;
	private final ArrayList<PlayerClient> players;

	private final ArrayList<CharacterClient> characters;

	private final ArrayList<BattleClient> battles;

	private ChatRoomClient chat;

	public CampaignClient(IServerDocument server, String localPath,
			String name, Boolean isMj) throws RemoteException {
		super(server, localPath);

		// First create all local variable
		players = new ArrayList<PlayerClient>();
		characters = new ArrayList<CharacterClient>();
		battles = new ArrayList<BattleClient>();

		// Load all existing documents
		loadCampaign();

		// create current player
		Long connectTimeout30second = 30000L;
		currentPlayer = (PlayerClient) getDocument(
				server.createDocument(new DocumentPlayerBuilder(name, isMj)),
				connectTimeout30second);

		chat.setPseudo(currentPlayer.getName());
		chat.talk(currentPlayer.getName(), MessageRemote.SYSTEM_CONNECT_MESSAGE);
	}

	public void disconnect() {
		// TODO notify
	}

	public PlayerClient getCurrentPlayer() {
		return currentPlayer;
	}

	public List<PlayerClient> getPlayers() {
		return this.players;
	}

	public List<CharacterClient> getCharacters() {
		return characters;
	}

	public List<BattleClient> getBattles() {
		return this.battles;
	}

	public ChatRoomClient getChat() {
		return this.chat;
	}

	public void createCharacter(CompleteCharacter character) {
		Long imageId = createDocument(new DocumentImageBuilder(
				character.getImage()));

		createDocument(new DocumentCharacterBuilder(character.getCharacter(),
				imageId));
	}

	public void createBattle(String mapName, TransfertImage transfertImage) {
		Scale scale = new Scale(80, 1.5);
		createDocument(new DocumentBattleBuilder(mapName, transfertImage, scale));
	}

	public void createBattle(String mapName, File image) throws IOException {
		SerializableImage transfertImage = new SerializableImage(image);
		createBattle(mapName, transfertImage);
	}

	public void createMapElement(MapClient<?> map,
			DocumentMapElementBuilder mapElement) {

		Long guid = this.createDocument(mapElement);
		map.addMapElement(guid);
	}

	@Override
	protected void add(DocumentClient<?> client) {
		System.out.println("add other " + client);
		if (client instanceof PlayerClient) {
			this.players.add((PlayerClient) client);
		} else if (client instanceof ChatRoomClient) {
			this.chat = (ChatRoomClient) client;
		} else if (client instanceof CharacterClient) {
			characters.add((CharacterClient) client);
		} else if (client instanceof BattleClient) {
			addBattle((BattleClient) client);
		}
	}

	protected void addBattle(BattleClient battle) {
		battles.add(battle);
		for (ICampaignListener listener : this
				.getListener(ICampaignListener.class)) {
			listener.battleAdded(battle);
		}
	}

	protected void removeBattle(BattleClient battle) {
		battles.remove(battle);
		for (ICampaignListener listener : this
				.getListener(ICampaignListener.class)) {
			listener.battleRemoved(battle);
		}
	}

	public void addCampaignListener(ICampaignListener listener) {
		this.addListener(ICampaignListener.class, listener);
	}

	public void removeCampaignListener(ICampaignListener listener) {
		this.removeListener(ICampaignListener.class, listener);
	}
}
