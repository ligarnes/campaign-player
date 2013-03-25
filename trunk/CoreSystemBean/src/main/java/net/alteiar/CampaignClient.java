package net.alteiar;

import java.awt.Color;
import java.beans.Beans;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.character.CharacterBean;
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
import net.alteiar.server.document.DocumentPath;

public final class CampaignClient implements DocumentManagerListener {
	private static CampaignClient INSTANCE = null;

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	public static synchronized void connect(String localAdress,
			String serverAdress, String port, String path, String pseudo,
			Boolean isMj) {
		if (INSTANCE != null) {
			return;
		}

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
			server.createDocument(new DocumentPath("", ""),
					new BeanEncapsulator(new Chat()));
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

	private final ArrayList<CharacterBean> characters;

	private final ArrayList<Battle> battles;

	private Chat chat;

	private final DocumentManager manager;

	private final ArrayList<CampaignListener> listeners;

	private CampaignClient(DocumentManager manager, String name, Boolean isMj) {
		this.manager = manager;
		this.manager.addDocumentManagerClient(this);

		// First create all local variable
		players = new ArrayList<Player>();
		characters = new ArrayList<CharacterBean>();
		battles = new ArrayList<Battle>();
		listeners = new ArrayList<CampaignListener>();

		// Load all existing documents
		this.manager.loadDocuments();

		// create current player
		Long connectTimeout30second = 30000L;

		Player current = new Player(name, isMj, Color.BLUE);
		addBean(current);
		currentPlayer = getBean(current.getId(), connectTimeout30second);

		// The server should already contain a chat
		if (chat != null) {
			chat.setPseudo(currentPlayer.getName());
			chat.talk(currentPlayer.getName(),
					MessageRemote.SYSTEM_CONNECT_MESSAGE);
		}
	}

	public void disconnect() {

	}

	public void addBean(BasicBeans bean) {
		manager.createDocument(new BeanEncapsulator(bean));
	}

	public void removeBean(BasicBeans bean) {
		manager.removeDocument(bean);
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBeans> E getBean(long id) {
		DocumentClient document = manager.getDocument(id);
		if (document == null) {
			return null;
		}
		return (E) document.getBeanEncapsulator().getBean();
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBeans> E getBean(long id, long timeout) {
		return (E) manager.getDocument(id, timeout).getBeanEncapsulator()
				.getBean();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@SuppressWarnings("unchecked")
	public List<Player> getPlayers() {
		List<Player> copy;
		synchronized (players) {
			copy = (List<Player>) players.clone();
		}
		return copy;
	}

	@SuppressWarnings("unchecked")
	public List<Battle> getBattles() {
		List<Battle> copy;
		synchronized (battles) {
			copy = (List<Battle>) battles.clone();
		}
		return copy;
	}

	@SuppressWarnings("unchecked")
	public List<CharacterBean> getCharacters() {
		List<CharacterBean> copy;
		synchronized (characters) {
			copy = (List<CharacterBean>) characters.clone();
		}
		return copy;
	}

	public Chat getChat() {
		return this.chat;
	}

	@Override
	public void documentAdded(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();

		// System.out.println("document added: " + bean.getClass());

		if (Beans.isInstanceOf(bean, Player.class)) {
			synchronized (players) {
				players.add((Player) Beans.getInstanceOf(bean, Player.class));
			}
		} else if (Beans.isInstanceOf(bean, Chat.class)) {
			chat = (Chat) Beans.getInstanceOf(bean, Chat.class);
		} else if (Beans.isInstanceOf(bean, Battle.class)) {
			Battle battle = (Battle) Beans.getInstanceOf(bean, Battle.class);
			synchronized (battles) {
				battles.add(battle);
			}
			notifyBattleAdded(battle);
		} else if (Beans.isInstanceOf(bean, CharacterBean.class)) {
			CharacterBean character = (CharacterBean) Beans.getInstanceOf(bean,
					CharacterBean.class);
			synchronized (characters) {
				characters.add(character);
			}
			notifyCharacterAdded(character);
		}

	}

	@Override
	public void documentRemoved(DocumentClient document) {
		BasicBeans bean = document.getBeanEncapsulator().getBean();

		if (Beans.isInstanceOf(bean, Player.class)) {
			synchronized (players) {
				players.remove(Beans.getInstanceOf(bean, Player.class));
			}
		} else if (Beans.isInstanceOf(bean, Battle.class)) {
			Battle battle = (Battle) Beans.getInstanceOf(bean, Battle.class);
			synchronized (battles) {
				battles.remove(battle);
			}
			notifyBattleRemoved(battle);
		} else if (Beans.isInstanceOf(bean, CharacterBean.class)) {
			CharacterBean character = (CharacterBean) Beans.getInstanceOf(bean,
					CharacterBean.class);
			synchronized (characters) {
				characters.remove(character);
			}
			notifyCharacterRemoved(character);
		}
	}

	// /////////////// LISTENERS METHODS /////////////////
	public void addCampaignListener(CampaignListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeCampaignListener(CampaignListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<CampaignListener> getListeners() {
		ArrayList<CampaignListener> copy;
		synchronized (listeners) {
			copy = (ArrayList<CampaignListener>) listeners.clone();
		}
		return copy;
	}

	protected void notifyCharacterAdded(CharacterBean character) {
		for (CampaignListener listener : getListeners()) {
			listener.characterAdded(character);
		}
	}

	protected void notifyCharacterRemoved(CharacterBean character) {
		for (CampaignListener listener : getListeners()) {
			listener.characterRemoved(character);
		}
	}

	protected void notifyBattleAdded(Battle battle) {
		for (CampaignListener listener : getListeners()) {
			listener.battleAdded(battle);
		}
	}

	protected void notifyBattleRemoved(Battle battle) {
		for (CampaignListener listener : getListeners()) {
			listener.battleRemoved(battle);
		}
	}
}
