package net.alteiar.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.alteiar.ExceptionTool;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.IServerDocument;
import net.alteiar.server.ServerListener;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.character.DocumentCharacterBuilder;
import net.alteiar.server.document.character.PathfinderCharacterFacade;
import net.alteiar.server.document.chat.ChatRoomClient;
import net.alteiar.server.document.files.DocumentImageBuilder;
import net.alteiar.server.document.player.DocumentPlayerBuilder;
import net.alteiar.server.document.player.PlayerClient;
import net.alteiar.shared.SerializableImage;

public class CampaignClient implements ICampaignClient {

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
		try {
			INSTANCE = new CampaignClient(campaign, name, isMj);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	private final IServerDocument server;

	private final PlayerClient currentPlayer;
	private final ArrayList<PlayerClient> players;

	private final HashMap<Long, DocumentClient<?>> documents;

	private ArrayList<CharacterClient> characters;

	private ChatRoomClient chat;

	private static CountDownLatch counter = new CountDownLatch(0);

	protected static synchronized CountDownLatch getCounterInstance() {
		if (counter.getCount() == 0) {
			counter = new CountDownLatch(1);
		}
		return counter;
	}

	public CampaignClient(IServerDocument server, String name, Boolean isMj)
			throws RemoteException {
		this.server = server;
		this.server.addServerListener(new CampaignClientObserver());

		documents = new HashMap<>();
		players = new ArrayList<>();

		loadCampaign();

		currentPlayer = (PlayerClient) getDocument(
				server.createDocument(new DocumentPlayerBuilder(name, isMj)),
				300L);
	}

	protected void loadCampaign() {
		try {
			for (Long doc : this.server.getDocuments()) {
				addDocument(doc);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PlayerClient getCurrentPlayer() {
		return currentPlayer;
	}

	public List<PlayerClient> getPlayers() {
		return this.players;
	}

	public ChatRoomClient getChat() {
		return this.chat;
	}

	public DocumentClient<?> getDocument(Long id) {
		return documents.get(id);
	}

	public DocumentClient<?> getDocument(Long id, Long timeout) {
		Long begin = System.currentTimeMillis();

		DocumentClient<?> value = documents.get(id);

		Long current = System.currentTimeMillis();
		while (value == null && (current - begin) < timeout) {
			try {
				getCounterInstance().await((timeout - (current - begin)),
						TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value = documents.get(id);
			current = System.currentTimeMillis();
		}
		return value;
	}

	public void createCharacter(PathfinderCharacterFacade character) {
		try {
			Long imageId = server.createDocument(new DocumentImageBuilder(
					new SerializableImage(character.getImage())));

			server.createDocument(new DocumentCharacterBuilder(character,
					imageId));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addDocument(Long guid) {
		IDocumentRemote doc;
		try {
			// search localy

			// else get from server
			doc = server.getDocument(guid);
			DocumentClient<?> client = doc.buildProxy();
			client.initializeTransient();

			this.documents.put(guid, client);

			this.add(client);
			getCounterInstance().countDown();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	// TODO useless adsk sylvain
	private void add(PlayerClient player) {
		System.out.println("add player");
		this.players.add(player);
	}

	private void add(DocumentClient<?> client) {
		System.out.println("add other " + client);
		if (client instanceof PlayerClient) {
			this.players.add((PlayerClient) client);
		} else if (client instanceof ChatRoomClient) {
			this.chat = (ChatRoomClient) client;
		}
	}

	private void removeDocument(Long guid) {
		this.documents.remove(guid);
	}

	private class CampaignClientObserver extends UnicastRemoteObject implements
			ServerListener {
		private static final long serialVersionUID = 1L;

		protected CampaignClientObserver() throws RemoteException {
			super();
		}

		@Override
		public void documentAdded(Long guid) throws RemoteException {
			addDocument(guid);
		}

		@Override
		public void documentRemoved(Long guid) throws RemoteException {
			removeDocument(guid);
		}
	}
}
