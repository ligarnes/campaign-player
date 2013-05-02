package net.alteiar;

import java.awt.Color;
import java.beans.Beans;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import net.alteiar.chat.Chat;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManager;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.dice.DiceRoller;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.MapBean;
import net.alteiar.event.EventManager;
import net.alteiar.player.Player;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentLoader;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.UniqueID;

public final class CampaignClient implements DocumentManagerListener {
	private static CampaignClient INSTANCE = null;
	private static Boolean IS_SERVER = false;

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	public static synchronized void loadCampaignServer(String serverAdress,
			String port, String campaignPath) {
		startServer(serverAdress, port, campaignPath);
		connectToServer(serverAdress, serverAdress, port);
		CampaignClient.getInstance().loadGame(campaignPath);
		for (Player player : CampaignClient.getInstance().getPlayers()) {
			player.setConnected(false);
		}
	}

	public static synchronized void startNewCampaignServer(String serverAdress,
			String port, String campaignPath) {

		ServerDocuments server = startServer(serverAdress, port, campaignPath);
		Chat chat = new Chat();
		DiceRoller diceRoller = new DiceRoller();
		try {
			server.createDocument(new DocumentPath(campaignPath, chat), chat);
			server.createDocument(new DocumentPath(campaignPath, diceRoller),
					diceRoller);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		connectToServer(serverAdress, serverAdress, port);
	}

	public static synchronized void connectToServer(String localAdress,
			String serverAdress, String port) {

		if (INSTANCE != null) {
			return;
		}

		DocumentManager manager = null;
		try {
			manager = DocumentManager.connect(localAdress, serverAdress, port);
			INSTANCE = new CampaignClient(manager);
		} catch (RemoteException e) {
			ExceptionTool
					.showError(
							e,
							"Aucune partie n'a \u00E9t\u00E9 trouv\u00E9e sur le serveur s\u00E9lectionn\u00E9.");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static synchronized ServerDocuments startServer(String addressIp,
			String port, String path) {
		ServerDocuments server = null;
		try {
			server = ServerDocuments.startServer(addressIp, port, path);
			IS_SERVER = true;
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
		return server;
	}

	public static synchronized void leaveGame() {
		INSTANCE.disconnect();
		INSTANCE = null;
		if (IS_SERVER) {
			ServerDocuments.stopServer();
		}
	}

	private Player currentPlayer;
	private final ArrayList<Player> players;

	private final ArrayList<CharacterBean> characters;

	private final ArrayList<MapBean> battles;

	private final ArrayList<AuthorizationBean> documentsBean;

	private Chat chat;
	private DiceRoller diceRoller;

	private final DocumentManager manager;

	private final ArrayList<CampaignListener> listeners;
	private final HashMap<UniqueID, ArrayList<WaitBeanListener>> waitBeanListeners;

	// this manage event
	private final EventManager eventManager;

	private CampaignClient(DocumentManager manager) {
		this.manager = manager;
		this.manager.addBeanListenerClient(this);

		listeners = new ArrayList<CampaignListener>();
		waitBeanListeners = new HashMap<UniqueID, ArrayList<WaitBeanListener>>();

		// First create all local variable
		players = new ArrayList<Player>();
		characters = new ArrayList<CharacterBean>();
		battles = new ArrayList<MapBean>();

		documentsBean = new ArrayList<AuthorizationBean>();

		// Load all existing documents
		this.manager.loadDocuments();

		eventManager = new EventManager(manager);
		if (chat != null) {
			this.chat.setPseudo("unknow");
		}
	}

	public String getCampaignPath() {
		return manager.getCampaignPath();
	}

	public int getRemoteDocumentCount() {
		return manager.getRemoteDocumenCount();
	}

	public int getLocalDocumentCount() {
		return manager.getLocalDocumentCount();
	}

	public void createPlayer(String name, Boolean isMj, Color color) {
		if (currentPlayer == null) {
			// create current player
			Long connectTimeout30second = 30000L;

			Player current = new Player(name, isMj, color);
			current.setConnected(true);
			addBean(current);
			currentPlayer = getBean(current.getId(), connectTimeout30second);
			if (currentPlayer == null) {
				throw new RuntimeErrorException(new Error(
						"impossible de créer un joueur"));
			}
			connectPlayer();
		}
	}

	public Boolean selectPlayer(Player player) {
		Boolean select = false;
		if (currentPlayer == null && players.contains(player)) {
			this.currentPlayer = player;
			this.currentPlayer.setConnected(true);
			connectPlayer();
			select = true;
		}
		return select;
	}

	private void connectPlayer() {
		// The server should already contain a chat
		if (chat != null) {
			chat.setPseudo(currentPlayer.getName());
			chat.talk(currentPlayer.getName(),
					MessageRemote.SYSTEM_CONNECT_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AuthorizationBean> getDocuments() {
		ArrayList<AuthorizationBean> copy;
		synchronized (documentsBean) {
			copy = (ArrayList<AuthorizationBean>) documentsBean.clone();
		}
		return copy;
	}

	private void disconnect() {
		this.currentPlayer.setConnected(false);
	}

	public void addBean(BasicBean bean) {
		realAddBean(bean);
	}

	public void addBean(AuthorizationBean bean) {
		bean.setOwner(getCurrentPlayer().getId());
		realAddBean(bean);
	}

	public void realAddBean(BasicBean bean) {
		manager.createDocument(bean);
	}

	public void removeBean(BasicBean bean) {
		manager.removeDocument(bean);
	}

	public void removeBean(UniqueID beanId) {
		manager.removeDocument(beanId);
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBean> E getBean(UniqueID id) {
		DocumentClient document = manager.getDocument(id);
		if (document == null) {
			return null;
		}
		return (E) document.getBeanEncapsulator().getBean();
	}

	public void addWaitBeanListener(WaitBeanListener listener) {
		BasicBean bean = getBean(listener.getBeanId());
		if (bean != null) {
			listener.beanReceived(bean);
			return;
		}

		ArrayList<WaitBeanListener> listenersList = waitBeanListeners
				.get(listener.getBeanId());
		if (listenersList == null) {
			listenersList = new ArrayList<WaitBeanListener>();
			synchronized (waitBeanListeners) {
				waitBeanListeners.put(listener.getBeanId(), listenersList);
			}
		}
		listenersList.add(listener);
	}

	protected void removeWaitBeanListener(BasicBean listener) {
		synchronized (waitBeanListeners) {
			waitBeanListeners.remove(listener.getId());
		}
	}

	protected ArrayList<WaitBeanListener> getWaitBeanListener(UniqueID beanId) {
		ArrayList<WaitBeanListener> listeners;
		synchronized (waitBeanListeners) {
			listeners = waitBeanListeners.get(beanId);
		}
		if (listeners == null) {
			listeners = new ArrayList<WaitBeanListener>();
		}
		return listeners;
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBean> E getBean(UniqueID id, long timeout) {
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
	public List<MapBean> getBattles() {
		List<MapBean> copy;
		synchronized (battles) {
			copy = (List<MapBean>) battles.clone();
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
	public void beanAdded(BasicBean bean) {
		if (Beans.isInstanceOf(bean, Player.class)) {
			synchronized (players) {
				players.add((Player) Beans.getInstanceOf(bean, Player.class));
			}
		} else if (Beans.isInstanceOf(bean, Chat.class)) {
			chat = (Chat) Beans.getInstanceOf(bean, Chat.class);
		} else if (Beans.isInstanceOf(bean, MapBean.class)) {
			MapBean battle = (MapBean) Beans.getInstanceOf(bean, MapBean.class);
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
		} else if (Beans.isInstanceOf(bean, DiceRoller.class)) {
			diceRoller = (DiceRoller) Beans.getInstanceOf(bean,
					DiceRoller.class);
		}

		if (Beans.isInstanceOf(bean, AuthorizationBean.class)) {
			AuthorizationBean doc = (AuthorizationBean) Beans.getInstanceOf(
					bean, AuthorizationBean.class);
			synchronized (documentsBean) {
				documentsBean.add(doc);
			}
			notifyBeanAdded(doc);
		}

		ArrayList<WaitBeanListener> waitListeners = getWaitBeanListener(bean
				.getId());
		for (WaitBeanListener waitBeanListener : waitListeners) {
			waitBeanListener.beanReceived(bean);
		}
		removeWaitBeanListener(bean);
	}

	@Override
	public void beanRemoved(BasicBean bean) {
		if (Beans.isInstanceOf(bean, Player.class)) {
			synchronized (players) {
				players.remove(Beans.getInstanceOf(bean, Player.class));
			}
		} else if (Beans.isInstanceOf(bean, MapBean.class)) {
			MapBean battle = (MapBean) Beans.getInstanceOf(bean, MapBean.class);
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

		if (Beans.isInstanceOf(bean, AuthorizationBean.class)) {
			AuthorizationBean doc = (AuthorizationBean) Beans.getInstanceOf(
					bean, AuthorizationBean.class);
			synchronized (documentsBean) {
				documentsBean.remove(doc);
			}
			notifyBeanRemoved(doc);
		}

	}

	private static void deleteRecursive(File base) {
		if (base.listFiles() != null) {
			for (File f : base.listFiles()) {
				deleteRecursive(f);
			}
		}
		base.delete();
	}

	public void saveGame() {
		File dir = new File(manager.getCampaignPath());
		if (dir.exists()) {
			deleteRecursive(dir);
		}

		try {
			for (DocumentClient doc : manager.getDocuments()) {
				doc.saveLocal();
			}
		} catch (Exception e) {
			ExceptionTool.showError(e, "Impossible de sauver la campagne");
		}

	}

	public void loadGame(String campaignName) {
		try {
			File baseDir = new File(manager.getCampaignPath());
			if (baseDir.exists()) {
				for (File dir : baseDir.listFiles()) {
					if (dir.isDirectory()) {
						for (File fi : dir.listFiles()) {
							if (fi.isFile()) {
								addBean(DocumentLoader.loadBeanLocal(fi));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			ExceptionTool.showError(e, "Impossible de charger la campagne");
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

	protected void notifyBeanAdded(AuthorizationBean authBean) {
		for (CampaignListener listener : getListeners()) {
			listener.beanAdded(authBean);
		}
	}

	protected void notifyBeanRemoved(AuthorizationBean authBean) {
		for (CampaignListener listener : getListeners()) {
			listener.beanRemoved(authBean);
		}
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

	protected void notifyBattleAdded(MapBean battle) {
		for (CampaignListener listener : getListeners()) {
			listener.battleAdded(battle);
		}
	}

	protected void notifyBattleRemoved(MapBean battle) {
		for (CampaignListener listener : getListeners()) {
			listener.battleRemoved(battle);
		}
	}

}
