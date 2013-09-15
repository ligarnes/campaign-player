package net.alteiar.campaign;

import java.awt.Color;
import java.beans.Beans;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.alteiar.SuppressBeanListener;
import net.alteiar.WaitBeanListener;
import net.alteiar.chat.Chat;
import net.alteiar.chat.MessageFactory;
import net.alteiar.combatTraker.CombatTraker;
import net.alteiar.dice.DiceRoller;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.BeanBasicDocument;
import net.alteiar.documents.BeanDirectory;
import net.alteiar.documents.BeanDocument;
import net.alteiar.kryo.MyKryoInit;
import net.alteiar.newversion.client.DocumentManager;
import net.alteiar.newversion.client.DocumentManagerListener;
import net.alteiar.newversion.server.document.DocumentIO;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.player.Player;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.file.SerializableFile;

import org.apache.log4j.Logger;

public final class CampaignClient implements DocumentManagerListener {
	// MOVE STATIC TO FACTORY class
	static CampaignClient INSTANCE = null;

	public static CampaignClient getInstance() {
		return INSTANCE;
	}

	private Player currentPlayer;
	private final ArrayList<Player> players;

	private final HashSet<BeanDocument> documentsBean;
	private BeanDirectory rootDirectory;

	private Chat chat;
	private DiceRoller diceRoller;

	// TODO FIXME currently combat traker is global but we may need to change
	// that
	private CombatTraker combatTraker;

	private final DocumentManager manager;

	private final ArrayList<CampaignListener> listeners;
	private final HashMap<UniqueID, ArrayList<WaitBeanListener>> waitBeanListeners;
	private final HashMap<UniqueID, ArrayList<SuppressBeanListener>> suppressBeanListeners;

	private final String serverIp;
	private final int port;

	// this manage event
	// TODO desactivate it for the moment
	// private final EventManager eventManager;

	protected CampaignClient(String ipServer, int port, String specificDic,
			String globalPath, MyKryoInit kryoInit) {

		try {
			this.manager = new DocumentManager(ipServer, port, specificDic,
					globalPath, kryoInit);
		} catch (IOException e) {
			Logger.getLogger(getClass()).error("Impossible de se connecter", e);
			throw new ExceptionInInitializerError(e);
		}

		this.manager.addBeanListenerClient(this);

		serverIp = ipServer;
		this.port = port;

		listeners = new ArrayList<CampaignListener>();
		waitBeanListeners = new HashMap<UniqueID, ArrayList<WaitBeanListener>>();
		suppressBeanListeners = new HashMap<UniqueID, ArrayList<SuppressBeanListener>>();

		// First create all local variable
		players = new ArrayList<Player>();
		documentsBean = new HashSet<BeanDocument>();
		rootDirectory = null;

		// eventManager = new EventManager(manager);
	}

	public boolean isInitialized() {
		return this.manager.isInitialized();
	}

	public boolean isLoaded() {
		boolean isLoaded = isInitialized();

		isLoaded = isLoaded && combatTraker != null;
		isLoaded = isLoaded && chat != null;
		isLoaded = isLoaded && diceRoller != null;

		return isLoaded;
	}

	public BeanDirectory getRootDirectory() {
		return rootDirectory;
	}

	public String getIpServer() {
		return serverIp;
	}

	public int getPort() {
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

		if (localFile.exists() && localFile.isDirectory()) {
			ArrayList<E> res = loadDirectory(localFile);
			beans.addAll(res);
		}
		if (globalFile.exists() && globalFile.isDirectory()) {
			ArrayList<E> res1 = loadDirectory(globalFile);
			beans.addAll(res1);
		}

		return beans;
	}

	public static <E extends BasicBean> ArrayList<E> loadDirectory(
			File globalPath, Class<E> classes) {
		File globalFile = new File(globalPath, classes.getCanonicalName());
		return loadDirectory(globalFile);
	}

	@SuppressWarnings("unchecked")
	private static <E extends BasicBean> ArrayList<E> loadDirectory(
			File localFile) {

		ArrayList<E> result = new ArrayList<E>();
		for (File f : localFile.listFiles()) {
			try {
				BasicBean bean = DocumentIO.loadBeanLocal(f);
				result.add((E) bean);
			} catch (Exception e) {
				Logger.getLogger(CampaignClient.class).warn(
						"fail to load global bean " + f.getName(), e);
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

	public File searchFile(String filename) {
		File global = new File(manager.getGlobalPath(),
				SerializableFile.FILE_DIR + "/" + filename);
		File specific = new File(manager.getSpecificPath(),
				SerializableFile.FILE_DIR + "/" + filename);

		if (specific.exists()) {
			return specific;
		} else if (global.exists()) {
			return global;
		}

		return null;
	}

	public DiceRoller getDiceRoller() {
		return diceRoller;
	}

	public String getCampaignDirectory() {
		return manager.getSpecificPath();
	}

	public String getCampaignName() {
		return manager.getSpecificPath();
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
				throw new RuntimeException("impossible de créer un joueur");
			}
			connectPlayer();

			if (rootDirectory == null) {
				UniqueID id = null;
				BeanDirectory root = new BeanDirectory(id, "ROOT");
				root.setOwner(currentPlayer.getId());

				manager.createDocument(root);

				// we wait it here because the root directory must exist!
				rootDirectory = getBean(root.getId(), 3000L);

				BeanDirectory pj = new BeanDirectory(rootDirectory,
						"personnages");
				addBean(pj);
			}
		}
	}

	public Boolean selectPlayer(Player player) {
		Boolean select = false;
		if (currentPlayer == null && players.contains(player)) {
			this.currentPlayer = CampaignClient.getInstance().getBean(
					player.getId());
			this.currentPlayer.setConnected(true);
			connectPlayer();
			select = true;
		}
		return select;
	}

	private void connectPlayer() {
		// The server should already contain a chat
		if (chat != null) {
			chat.talk(MessageFactory.connectMessage(currentPlayer));
		}
	}

	@SuppressWarnings("unchecked")
	public HashSet<BeanDocument> getDocuments() {
		HashSet<BeanDocument> copy;
		synchronized (documentsBean) {
			copy = (HashSet<BeanDocument>) documentsBean.clone();
		}
		return copy;
	}

	void disconnect() {
		this.currentPlayer.setConnected(false);
		manager.stopClient();
	}

	public void addBean(BasicBean bean) {
		realAddBean(bean);
	}

	public void addBean(BeanBasicDocument doc) {
		BeanDirectory dir = getBean(doc.getParent());
		dir.addDocument(doc);
		doc.setOwner(getCurrentPlayer().getId());
		realAddBean(doc);
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
			removeBean(bean.getId());
		}
	}

	public void removeBean(UniqueID beanId) {
		manager.deleteDocument(beanId);
	}

	public <E extends BasicBean> E getBean(UniqueID id, long timeout) {
		return manager.getBean(id, timeout);
	}

	public <E extends BasicBean> E getBean(UniqueID id) {
		return manager.getBean(id, -1);
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

	public CombatTraker getCombatTraker() {
		return this.combatTraker;
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
		} else if (Beans.isInstanceOf(bean, CombatTraker.class)) {
			combatTraker = (CombatTraker) Beans.getInstanceOf(bean,
					CombatTraker.class);
		}

		if (Beans.isInstanceOf(bean, BeanDirectory.class)) {
			final BeanDirectory doc = (BeanDirectory) Beans.getInstanceOf(bean,
					BeanDirectory.class);
			if (doc.getParent() == null) {
				this.rootDirectory = doc;
			}
			notifyBeanDocumentAdded(doc);
			notifyBeanAdded(doc);
		} else if (Beans.isInstanceOf(bean, BeanDocument.class)) {
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

		if (Beans.isInstanceOf(bean, BeanBasicDocument.class)) {
			BeanBasicDocument doc = (BeanBasicDocument) Beans.getInstanceOf(
					bean, BeanBasicDocument.class);
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
		File dir = new File(manager.getSpecificBeanPath());
		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				deleteRecursive(file);
			}
		}

		manager.saveLocal();
	}

	public void loadGame(String campaignName) throws Exception {
		File baseDir = new File(manager.getSpecificBeanPath());
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

	protected void notifyBeanDocumentAdded(BeanBasicDocument authBean) {
		for (CampaignListener listener : getListeners()) {
			listener.beanAdded(authBean);
		}
	}

	protected void notifyBeanRemoved(BeanBasicDocument authBean) {
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
