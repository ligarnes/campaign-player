package net.alteiar;

import java.awt.Color;
import java.beans.Beans;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.alteiar.chat.Chat;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.client.DocumentClient;
import net.alteiar.client.DocumentManager;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.dice.DiceRoller;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.player.Player;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentLoader;
import net.alteiar.server.document.DocumentPath;
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
	}

	public static synchronized void startNewCampaignServer(String serverAdress,
			String port, String campaignPath) {

		ServerDocuments server = startServer(serverAdress, port, campaignPath);
		Chat chat = new Chat();
		DiceRoller diceRoller = new DiceRoller();
		try {
			server.createDocument(new DocumentPath(campaignPath, chat),
					new BeanEncapsulator(chat), false);
			server.createDocument(new DocumentPath(campaignPath, diceRoller),
					new BeanEncapsulator(diceRoller), false);
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
			// TODO do not care about perma path for the moment
			manager = DocumentManager.connect(localAdress, serverAdress, port,
					"");
			INSTANCE = new CampaignClient(manager);
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

	private final ArrayList<Battle> battles;

	private final ArrayList<AuthorizationBean> documentsBean;

	private Chat chat;
	private DiceRoller diceRoller;

	private final DocumentManager manager;

	private final ArrayList<CampaignListener> listeners;
	private final HashMap<UniqueID, ArrayList<WaitBeanListener>> waitBeanListeners;

	private CampaignClient(DocumentManager manager) {
		this.manager = manager;
		this.manager.addDocumentManagerClient(this);

		listeners = new ArrayList<CampaignListener>();
		waitBeanListeners = new HashMap<UniqueID, ArrayList<WaitBeanListener>>();

		// First create all local variable
		players = new ArrayList<Player>();
		characters = new ArrayList<CharacterBean>();
		battles = new ArrayList<Battle>();

		documentsBean = new ArrayList<AuthorizationBean>();

		// Load all existing documents
		this.manager.loadDocuments();

	}

	public void createPlayer(String name, Boolean isMj) {
		if (currentPlayer == null) {
			// create current player
			Long connectTimeout30second = 30000L;

			Player current = new Player(name, isMj, Color.BLUE);
			addNotPermaBean(current);
			currentPlayer = getBean(current.getId(), connectTimeout30second);
			connectPlayer();
		}
	}
	
	public void createPlayer(String name, Boolean isMj, Color color) {
		if (currentPlayer == null) {
			// create current player
			Long connectTimeout30second = 30000L;

			Player current = new Player(name, isMj, color);
			addNotPermaBean(current);
			currentPlayer = getBean(current.getId(), connectTimeout30second);
			connectPlayer();
		}
	}

	public Boolean selectPlayer(Player player) {
		Boolean select = false;
		if (currentPlayer == null && players.contains(player)) {
			this.currentPlayer = player;
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
	}

	public void addBean(AuthorizationBean bean, Boolean perma) {
		bean.setOwner(CampaignClient.getInstance().getCurrentPlayer().getId());

		// replace name by guid, we are sure of the unicity
		manager.createDocument(
				new DocumentPath(manager.getCampaignPath(), bean),
				new BeanEncapsulator(bean), perma);
	}

	public void addBean(BasicBeans bean) {
		addBean(bean, false);
	}

	public void addBean(AuthorizationBean bean) {
		addBean(bean, false);
	}

	public void addBean(BasicBeans bean, Boolean perma) {
		manager.createDocument(new DocumentPath(manager.getCampaignPath(), bean
				.getId().toString()), new BeanEncapsulator(bean), perma);
	}

	public void addNotPermaBean(AuthorizationBean bean) {
		this.addBean(bean, false);
	}

	public void addNotPermaBean(BasicBeans bean) {
		this.addBean(bean, false);
	}

	public void addPermaBean(AuthorizationBean bean) {
		this.addBean(bean, true);
	}

	public void addPermaBean(BasicBeans bean) {
		this.addBean(bean, true);
	}

	public void removeBean(BasicBeans bean) {
		manager.removeDocument(bean);
	}

	public void removeBean(UniqueID beanId) {
		manager.removeDocument(beanId);
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBeans> E getBean(UniqueID id) {
		DocumentClient document = manager.getDocument(id);
		if (document == null) {
			return null;
		}
		return (E) document.getBeanEncapsulator().getBean();
	}

	public void addWaitBeanListener(WaitBeanListener listener) {
		BasicBeans bean = getBean(listener.getBeanId());
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

	protected void removeWaitBeanListener(BasicBeans listener) {
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
	public <E extends BasicBeans> E getBean(UniqueID id, long timeout) {
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

		ObjectOutputStream oos = null;
		try {
			ArrayList<DocumentClient> docs = manager.getDocuments();
			HashSet<String> list = new HashSet<String>();

			for (DocumentClient doc : docs) {
				if (!doc.isPerma()) {
					doc.saveLocal();
				} else {
					list.add(doc.getDocumentPath().getCompletePath());
				}
			}
			oos = new ObjectOutputStream(new FileOutputStream(
					manager.getCampaignPath() + "/permaList.txt"));
			oos.writeObject(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void loadGame(String campaignName) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(
					manager.getCampaignPath() + "/permaList.txt"));
			HashSet<String> list = (HashSet<String>) ois.readObject();
			for (String path : list) {
				File f = new File(path);
				addPermaBean(DocumentLoader.loadBeanLocal(f));
			}
			File baseDir = new File(manager.getCampaignPath());
			if (baseDir.exists()) {
				for (File dir : baseDir.listFiles()) {
					if (dir.isDirectory()) {
						for (File fi : dir.listFiles()) {
							if (fi.isFile()) {
								addNotPermaBean(DocumentLoader
										.loadBeanLocal(fi));
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
