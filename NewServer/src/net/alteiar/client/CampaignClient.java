package net.alteiar.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.alteiar.ExceptionTool;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.IServerDocument;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.CompleteCharacter;
import net.alteiar.server.document.character.DocumentCharacterBuilder;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.DocumentMapBuilder;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.battle.DocumentBattleBuilder;
import net.alteiar.server.document.map.filter.DocumentMapFilterBuilder;
import net.alteiar.server.document.player.DocumentPlayerBuilder;
import net.alteiar.server.document.player.PlayerClient;

public class CampaignClient extends DocumentManagerClient {

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

		} catch (MalformedURLException e) {
			ExceptionTool.showError(e);
		} catch (NotBoundException e) {
			ExceptionTool.showError(e);
		} catch (RemoteException e) {
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
		players = new ArrayList<>();
		characters = new ArrayList<>();
		battles = new ArrayList<>();

		// Load all existing documents
		loadCampaign();

		// create current player
		Long connectTimeout30second = 30000L;
		currentPlayer = (PlayerClient) getDocument(
				server.createDocument(new DocumentPlayerBuilder(name, isMj)),
				connectTimeout30second);
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
		BufferedImage imgs;
		try {
			imgs = transfertImage.restoreImage();

			int width = imgs.getWidth();
			int height = imgs.getHeight();

			Long imageId = createDocument(new DocumentImageBuilder(
					transfertImage));
			Long mapFilterId = createDocument(new DocumentMapFilterBuilder(
					width, height));

			Scale scale = new Scale(80, 1.5);

			createDocument(new DocumentBattleBuilder(mapName, width, height,
					imageId, mapFilterId, scale));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createMap(String mapName, TransfertImage transfertImage) {
		BufferedImage imgs;
		try {
			imgs = transfertImage.restoreImage();

			int width = imgs.getWidth();
			int height = imgs.getHeight();

			Long imageId = createDocument(new DocumentImageBuilder(
					transfertImage));
			Long mapFilterId = createDocument(new DocumentMapFilterBuilder(
					width, height));

			Scale scale = new Scale(80, 1.5);

			createDocument(new DocumentMapBuilder(mapName, width, height,
					imageId, mapFilterId, scale));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// TODO useless ask sylvain
	/*
	@Override
	protected void add(PlayerClient client) {
		System.out.println("add player");
		this.players.add(player);
	}*/

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
			battles.add((BattleClient) client);
		}
	}
}
