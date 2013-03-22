package net.alteiar;

import java.awt.Color;
import java.beans.Beans;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.chat.Chat;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManager;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.map.battle.Battle;
import net.alteiar.player.Player;
import net.alteiar.server.ServerDocuments;

public class CampaignClient implements DocumentManagerListener {
	private static CampaignClient INSTANCE = null;

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	public static void connect(String localAdress, String serverAdress,
			String port, String path, String pseudo, Boolean isMj) {
		DocumentManager manager = null;
		try {
			manager = DocumentManager.connect(localAdress, serverAdress, port,
					path, pseudo, isMj);
			INSTANCE = new CampaignClient(manager, pseudo, isMj);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startServer(String addressIp, String port) {
		ServerDocuments server;
		try {
			server = ServerDocuments.startServer(addressIp, port);
			server.createDocument(new BeanEncapsulator(new Chat()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final Player currentPlayer;
	private final ArrayList<Player> players;

	// private final ArrayList<CharacterClient> characters;

	private final ArrayList<Battle> battles;

	private Chat chat;

	private final DocumentManager manager;

	public CampaignClient(DocumentManager manager, String name, Boolean isMj) {
		this.manager = manager;
		this.manager.addDocumentManagerClient(this);

		// First create all local variable
		players = new ArrayList<Player>();
		// characters = new ArrayList<CharacterClient>();
		battles = new ArrayList<Battle>();

		// Load all existing documents
		this.manager.loadDocuments();
		// need to check all documents loaded

		// create current player
		Long connectTimeout30second = 30000L;
		currentPlayer = getBean(addBean(new Player(name, isMj, Color.BLUE)),
				connectTimeout30second);

		// The server should already contain a chat
		if (chat != null) {
			chat.setPseudo(currentPlayer.getName());
			chat.talk(currentPlayer.getName(),
					MessageRemote.SYSTEM_CONNECT_MESSAGE);
		}
	}

	public Long addBean(BasicBeans bean) {
		return manager.createDocument(new BeanEncapsulator(bean));
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBeans> E getBean(long id) {
		return (E) manager.getDocument(id).getBeanEncapsulator().getBean();
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBeans> E getBean(long id, long timeout) {
		return (E) manager.getDocument(id, timeout).getBeanEncapsulator()
				.getBean();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public List<Battle> getBattles() {
		return this.battles;
	}

	public Chat getChat() {
		return this.chat;
	}

	@Override
	public void documentAdded(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();

		// System.out.println("document added: " + bean.getClass());

		if (Beans.isInstanceOf(bean, Player.class)) {
			this.players.add((Player) Beans.getInstanceOf(bean, Player.class));
		} else if (Beans.isInstanceOf(bean, Chat.class)) {
			chat = (Chat) Beans.getInstanceOf(bean, Chat.class);
		} else if (Beans.isInstanceOf(bean, Battle.class)) {
			battles.add((Battle) Beans.getInstanceOf(bean, Battle.class));
		}

	}

	@Override
	public void documentRemoved(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();

		if (Beans.isInstanceOf(bean, Player.class)) {
			this.players.remove(Beans.getInstanceOf(bean, Player.class));
		}
	}
}
