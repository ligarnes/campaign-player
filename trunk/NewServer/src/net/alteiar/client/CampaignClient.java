package net.alteiar.client;

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
import net.alteiar.server.document.player.DocumentPlayerBuilder;
import net.alteiar.server.document.player.PlayerClient;

public class CampaignClient extends DocumentManagerClient {

	private static CampaignClient INSTANCE = null;

	public static void connect(String localAdress, String address, String port,
			String name, Boolean isMj) {
		IServerDocument campaign = null;

		Remote remoteObject;
		try {
			System.setProperty("java.rmi.server.hostname", localAdress);
			String[] allRemoteNames = RmiRegistry.list("//" + address + ":"
					+ port);

			for (String remoteName : allRemoteNames) {
				try {
					remoteObject = RmiRegistry.lookup(remoteName);
					LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "RMI REGISTRY: "
							+ remoteName);
					if (remoteObject instanceof IServerDocument) {
						campaign = (IServerDocument) remoteObject;
						INSTANCE = new CampaignClient(campaign, name, isMj);
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

	private ChatRoomClient chat;

	public CampaignClient(IServerDocument server, String name, Boolean isMj)
			throws RemoteException {
		super(server);

		players = new ArrayList<>();
		characters = new ArrayList<>();

		loadCampaign();

		currentPlayer = (PlayerClient) getDocument(
				server.createDocument(new DocumentPlayerBuilder(name, isMj)),
				5000L);
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

	public ChatRoomClient getChat() {
		return this.chat;
	}

	public void createCharacter(CompleteCharacter character) {
		Long imageId = createDocument(new DocumentImageBuilder(
				character.getImage()));

		createDocument(new DocumentCharacterBuilder(
				character.getCharacter(), imageId));
	}

	// TODO useless adsk sylvain
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
		}
	}
}
