package net.alteiar;

import java.awt.Color;
import java.beans.Beans;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;

import net.alteiar.chat.Chat;
import net.alteiar.chat.MessageFactory;
import net.alteiar.client.DocumentManager;
import net.alteiar.client.DocumentManagerListener;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.dice.DiceRoller;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.BeanDocument;
import net.alteiar.player.Player;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentIO;
import net.alteiar.shared.UniqueID;

import org.apache.log4j.Logger;

public final class CampaignClient implements DocumentManagerListener {

	private static CampaignClient INSTANCE = null;
	private static Boolean IS_SERVER = false;

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	public static synchronized void loadCampaignServer(String serverAdress,
			String port, String globalDocumentPath, String campaignPath) {
		startServer(serverAdress, port, campaignPath);
		connectToServer(serverAdress, serverAdress, port, globalDocumentPath);

		try {
			CampaignClient.getInstance().loadGame(campaignPath);
		} catch (Exception e) {
			Logger.getLogger(CampaignClient.class).error(
					"Impossible de charger la campagne", e);
		}

		for (Player player : CampaignClient.getInstance().getPlayers()) {
			player.setConnected(false);
		}
	}

	public static synchronized void startNewCampaignServer(String serverAdress,
			String port, String globalDocumentPath, String campaignPath) {

		ServerDocuments server = startServer(serverAdress, port, campaignPath);
		Chat chat = new Chat();
		DiceRoller diceRoller = new DiceRoller();
		try {
			server.createDocument(chat);
			server.createDocument(diceRoller);
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class).error(
					"start new campaign server, enable to join server", e);
		}
		connectToServer(serverAdress, serverAdress, port, globalDocumentPath);
	}

	public static synchronized void connectToServer(String localAdress,
			String serverAdress, String port, String globalDocumentPath) {

		if (INSTANCE != null) {
			return;
		}

		DocumentManager manager = null;
		try {
			manager = DocumentManager.connect(localAdress, serverAdress, port,
					globalDocumentPath);
			INSTANCE = new CampaignClient(manager, localAdress, serverAdress,
					port);
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class)
					.error("Aucune partie n'a \u00E9t\u00E9 trouv\u00E9e sur le serveur s\u00E9lectionn\u00E9.",
							e);
		} catch (MalformedURLException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Malformed Url when connect to server", e);
		} catch (NotBoundException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Not Bound exception when connect to server", e);
		}
	}

	private static synchronized ServerDocuments startServer(String addressIp,
			String port, String path) {
		ServerDocuments server = null;
		try {
			server = ServerDocuments.startServer(addressIp, port, path);
			IS_SERVER = true;
		} catch (RemoteException e) {
			Logger.getLogger(CampaignClient.class).error(
					"Remote exception when start server", e);
		} catch (MalformedURLException e) {
			Logger.getLogger(CampaignClient.class).error(
					"MalformedURLException exception when start server", e);
		} catch (NotBoundException e) {
			Logger.getLogger(CampaignClient.class).error(
					"NotBoundException exception when start server", e);
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

	private final ArrayList<BeanDocument> documentsBean;

	private Chat chat;
	private DiceRoller diceRoller;

	private final DocumentManager manager;

	private final ArrayList<CampaignListener> listeners;
	private final HashMap<UniqueID, ArrayList<WaitBeanListener>> waitBeanListeners;
	private final HashMap<UniqueID, ArrayList<SuppressBeanListener>> suppressBeanListeners;

	private final String localIp;
	private final String serverIp;
	private final String port;

	// this manage event
	// TODO desactivate it for the moment
	// private final EventManager eventManager;

	private CampaignClient(DocumentManager manager, String ipLocal,
			String ipServer, String port) {
		this.manager = manager;
		this.manager.addBeanListenerClient(this);

		localIp = ipLocal;
		serverIp = ipServer;
		this.port = port;

		listeners = new ArrayList<CampaignListener>();
		waitBeanListeners = new HashMap<UniqueID, ArrayList<WaitBeanListener>>();
		suppressBeanListeners = new HashMap<UniqueID, ArrayList<SuppressBeanListener>>();

		// First create all local variable
		players = new ArrayList<Player>();
		documentsBean = new ArrayList<BeanDocument>();

		// Load all existing documents
		this.manager.loadDocuments();

		// eventManager = new EventManager(manager);
		if (chat != null) {
			this.chat.setPseudo("unknow");
		}
	}

	public String getIpLocal() {
		return localIp;
	}

	public String getIpServer() {
		return serverIp;
	}

	public String getPort() {
		return port;
	}

	/**
	 * Load all bean with from the given class that are in specific or global
	 * directory
	 * 
	 * @param classes
	 * @return
	 */
	public <E extends BasicBean> ArrayList<E> loadLocalBean(Class<E> classes) {
		ArrayList<E> beans = new ArrayList<E>();

		File localFile = new File(manager.getSpecificPath(),
				classes.getCanonicalName());
		File globalFile = new File(manager.getGlobalPath(),
				classes.getCanonicalName());

		ArrayList<E> res = loadDirectory(localFile);
		ArrayList<E> res1 = loadDirectory(globalFile);

		beans.addAll(res);
		beans.addAll(res1);

		return beans;
	}

	public static <E extends BasicBean> ArrayList<E> loadDirectory(
			File globalPath, Class<E> classes) {
		File globalFile = new File(globalPath, classes.getCanonicalName());
		return loadDirectory(globalFile);
	}

	private static <E extends BasicBean> ArrayList<E> loadDirectory(
			File localFile) {
		ArrayList<E> result = new ArrayList<E>();
		if (localFile.exists() && localFile.isDirectory()) {
			for (File f : localFile.listFiles()) {
				try {
					BasicBean bean = DocumentIO.loadBeanLocal(f);
					result.add((E) bean);
				} catch (Exception e) {
					Logger.getLogger(CampaignClient.class).warn(
							"fail to load global bean " + f.getName(), e);
				}
			}
		}
		return result;
	}

	/**
	 * Save the basic bean as a permanent bean (this bean would be accessible
	 * with any campaign)
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void savePerma(BasicBean bean) {
		try {
			manager.saveGlobalBean(bean);
		} catch (Exception e) {
			Logger.getLogger(CampaignClient.class).error(
					"Error on global save", e);
		}
	}

	public DiceRoller getDiceRoller() {
		return diceRoller;
	}

	public String getCampaignName() {
		return manager.getSpecificPath();
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
						"impossible de créer un joueur"),
						"impossible de créer un joueur");
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

			chat.talk(MessageFactory.connectMessage(currentPlayer));
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<BeanDocument> getDocuments() {
		ArrayList<BeanDocument> copy;
		synchronized (documentsBean) {
			copy = (ArrayList<BeanDocument>) documentsBean.clone();
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
		if (bean != null) {
			manager.removeDocument(bean);
		}
	}

	public void removeBean(UniqueID beanId) {
		manager.removeDocument(beanId);
	}

	public <E extends BasicBean> E getBean(UniqueID id) {
		return manager.getBean(id);
	}

	public <E extends BasicBean> ArrayList<E> getBeans(Collection<UniqueID> ids) {
		ArrayList<E> lst = new ArrayList<E>();

		for (UniqueID id : ids) {
			E bean = getBean(id);
			lst.add(bean);
		}

		return lst;
	}

	public void addSuppressBeanListener(SuppressBeanListener listener) {
		synchronized (suppressBeanListeners) {
			ArrayList<SuppressBeanListener> listeners = suppressBeanListeners
					.get(listener.getBeanId());

			if (listeners == null) {
				listeners = new ArrayList<SuppressBeanListener>();
				suppressBeanListeners.put(listener.getBeanId(), listeners);
			}
			listeners.add(listener);
		}
	}

	protected ArrayList<SuppressBeanListener> getSuppressBeanListener(
			UniqueID beanId) {
		ArrayList<SuppressBeanListener> listeners;
		synchronized (suppressBeanListeners) {
			listeners = suppressBeanListeners.get(beanId);
		}
		if (listeners == null) {
			listeners = new ArrayList<SuppressBeanListener>();
		}
		return listeners;
	}

	protected void removeSuppressBeanListener(BasicBean listener) {
		synchronized (suppressBeanListeners) {
			suppressBeanListeners.remove(listener.getId());
		}
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

	public <E extends BasicBean> E getBean(UniqueID id, long timeout) {
		return manager.getBean(id, timeout);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getDm() {
		Player dm = null;

		Iterator<Player> players = getPlayers().iterator();
		while (players.hasNext() && dm == null) {
			Player p = players.next();
			if (p.isDm()) {
				dm = p;
			}
		}

		return dm;
	}

	@SuppressWarnings("unchecked")
	public List<Player> getPlayers() {
		List<Player> copy;
		synchronized (players) {
			copy = (List<Player>) players.clone();
		}
		return copy;
	}

	public Chat getChat() {
		return this.chat;
	}

	@Override
	public void beanAdded(BasicBean bean) {
		if (Beans.isInstanceOf(bean, Player.class)) {
			Player player = (Player) Beans.getInstanceOf(bean, Player.class);
			synchronized (players) {
				players.add(player);
			}
			notifyPlayerAdded(player);
		} else if (Beans.isInstanceOf(bean, Chat.class)) {
			chat = (Chat) Beans.getInstanceOf(bean, Chat.class);
		} else if (Beans.isInstanceOf(bean, DiceRoller.class)) {
			diceRoller = (DiceRoller) Beans.getInstanceOf(bean,
					DiceRoller.class);
		}

		if (Beans.isInstanceOf(bean, BeanDocument.class)) {
			final BeanDocument doc = (BeanDocument) Beans.getInstanceOf(bean,
					BeanDocument.class);

			// TODO may change later but now we do not want to receive the
			// document while we do not contain the internal bean
			this.addWaitBeanListener(new WaitBeanListener() {
				@Override
				public UniqueID getBeanId() {
					return doc.getBeanId();
				}

				@Override
				public void beanReceived(BasicBean bean) {
					synchronized (documentsBean) {
						documentsBean.add(doc);
					}
					notifyBeanDocumentAdded(doc);
					notifyBeanAdded(doc);
				}
			});
		} else {
			notifyBeanAdded(bean);
		}
	}

	@Override
	public void beanRemoved(BasicBean bean) {
		if (Beans.isInstanceOf(bean, Player.class)) {
			synchronized (players) {
				players.remove(Beans.getInstanceOf(bean, Player.class));
			}
		}

		if (Beans.isInstanceOf(bean, BeanDocument.class)) {
			BeanDocument doc = (BeanDocument) Beans.getInstanceOf(bean,
					BeanDocument.class);
			synchronized (documentsBean) {
				documentsBean.remove(doc);
			}
			notifyBeanRemoved(doc);
		}

		ArrayList<SuppressBeanListener> removeListeners = getSuppressBeanListener(bean
				.getId());
		for (SuppressBeanListener removeBeanListener : removeListeners) {
			removeBeanListener.beanRemoved(bean);
		}
		removeSuppressBeanListener(bean);
	}

	private static void deleteRecursive(File base) {
		if (base.listFiles() != null) {
			for (File f : base.listFiles()) {
				deleteRecursive(f);
			}
		}
		base.delete();
	}

	public void saveGame() throws Exception {
		File dir = new File(manager.getSpecificPath());
		if (dir.exists()) {
			deleteRecursive(dir);
		}

		manager.saveLocal();
	}

	public void loadGame(String campaignName) throws Exception {
		File baseDir = new File(manager.getSpecificPath());
		if (baseDir.exists()) {
			for (File dir : baseDir.listFiles()) {
				if (dir.isDirectory()) {
					for (File fi : dir.listFiles()) {
						if (fi.isFile()) {
							BasicBean bean = DocumentIO.loadBeanLocal(fi);
							if (bean != null) {
								addBean(bean);
							}
						}
					}
				}
			}
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

	protected void notifyBeanAdded(BasicBean bean) {
		ArrayList<WaitBeanListener> waitListeners = getWaitBeanListener(bean
				.getId());
		for (WaitBeanListener waitBeanListener : waitListeners) {
			waitBeanListener.beanReceived(bean);
		}
		removeWaitBeanListener(bean);
	}

	protected void notifyBeanDocumentAdded(BeanDocument authBean) {
		for (CampaignListener listener : getListeners()) {
			listener.beanAdded(authBean);
		}
	}

	protected void notifyBeanRemoved(BeanDocument authBean) {
		for (CampaignListener listener : getListeners()) {
			listener.beanRemoved(authBean);
		}
	}

	protected void notifyPlayerAdded(Player player) {
		for (CampaignListener listener : getListeners()) {
			listener.playerAdded(player);
		}
	}
}
