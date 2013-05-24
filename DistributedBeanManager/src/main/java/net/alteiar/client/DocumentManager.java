package net.alteiar.client;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.IServerDocument;
import net.alteiar.server.ServerListener;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.DocumentIO;
import net.alteiar.server.document.DocumentLocal;
import net.alteiar.server.document.IDocumentClient;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.UniqueID;

public class DocumentManager {

	public static DocumentManager connect(String localAddress,
			String serverAddress, String port, String globalPath)
			throws RemoteException, MalformedURLException, NotBoundException {
		LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "Connect from "
				+ localAddress + " to " + serverAddress + " at " + port);

		System.setProperty("java.rmi.server.hostname", localAddress);

		DocumentManager documentManager = null;
		IServerDocument campaign = null;
		Remote remoteObject;

		String[] allRemoteNames = RmiRegistry.list("//" + serverAddress + ":"
				+ port);

		for (String remoteName : allRemoteNames) {
			remoteObject = RmiRegistry.lookup(remoteName);
			LoggerConfig.CLIENT_LOGGER.log(Level.INFO,
					"Find an rmi registry object: " + remoteName);
			if (remoteObject instanceof IServerDocument) {
				campaign = (IServerDocument) remoteObject;
				documentManager = new DocumentManager(campaign, globalPath);
				break;
			}
		}

		return documentManager;
	}

	// Use to notify when a document is received for blocking access
	private static CountDownLatch counter = new CountDownLatch(0);

	private static synchronized CountDownLatch getCounterInstance() {
		if (counter.getCount() == 0) {
			counter = new CountDownLatch(1);
		}
		return counter;
	}

	private final IServerDocument server;

	private final HashSet<DocumentManagerListener> listeners;
	private final HashMap<UniqueID, IDocumentClient> documents;
	private final String specificPath;
	private final String globalPath;

	private DocumentManager(IServerDocument server, String globalPath)
			throws RemoteException {
		documents = new HashMap<UniqueID, IDocumentClient>();
		listeners = new HashSet<DocumentManagerListener>();
		this.server = server;
		this.server.addServerListener(new CampaignClientObserver());

		specificPath = server.getSpecificPath();
		this.globalPath = globalPath;
	}

	public int getRemoteDocumenCount() {
		try {
			return server.getDocumentCount();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
		return -1;
	}

	public int getLocalDocumentCount() {
		return documents.size();
	}

	public void loadDocuments() {
		try {
			for (UniqueID doc : this.server.getDocuments()) {
				addDocument(doc);
			}
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public <E extends BasicBean> E getBean(UniqueID id) {
		return getBean(id, -1L);
	}

	@SuppressWarnings("unchecked")
	public <E extends BasicBean> E getBean(UniqueID id, Long timeout) {
		IDocumentClient doc = getDocument(id, -1L);
		if (doc == null) {
			// try to find it locally
			BasicBean bean = searchGlobalBean(id);
			if (bean != null) {
				// add to document in order to avoid search again
				addDocument(new DocumentLocal(bean));
			}
			doc = getDocument(id, timeout);
		}

		return doc == null ? null : (E) doc.getBeanEncapsulator().getBean();
	}

	protected BasicBean searchGlobalBean(UniqueID id) {
		BasicBean beanFound = null;
		String filename = DocumentIO.validateFilename(id.toString());

		File found = searchFile(new File(getGlobalPath()), filename);
		if (found != null) {
			try {
				beanFound = DocumentIO.loadBeanLocal(found);
			} catch (Exception e) {
				// Do not care if we cannot read the file it may be an error
			}
		}
		return beanFound;
	}

	protected File searchFile(File dir, String filename) {
		File found = null;
		Iterator<File> itt = Arrays.asList(dir.listFiles()).iterator();

		while (itt.hasNext() && found == null) {
			File file = itt.next();

			found = new File(file, filename);
			if (!found.exists()) {
				found = null;
			}
		}

		return found;
	}

	protected IDocumentClient getDocument(UniqueID id, Long timeout) {
		Long begin = System.currentTimeMillis();

		IDocumentClient value = documents.get(id);

		Long current = System.currentTimeMillis();
		while (value == null && (current - begin) < timeout) {
			try {
				if (!getCounterInstance().await((timeout - (current - begin)),
						TimeUnit.MILLISECONDS)) {
					return null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value = documents.get(id);
			current = System.currentTimeMillis();
		}
		return value;
	}

	private synchronized void addDocument(UniqueID guid) {
		try {
			addDocument(loadDocument(guid));
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		} catch (Exception e) {
			ExceptionTool.showError(e);
		}
	}

	private synchronized void addDocument(IDocumentClient document) {
		// add the document
		synchronized (documents) {
			this.documents.put(document.getId(), document);
		}

		// notify listener that a document is added
		for (DocumentManagerListener listener : getListeners()) {
			listener.beanAdded(document.getBeanEncapsulator().getBean());
		}

		// internal notify that a document is received
		getCounterInstance().countDown();
	}

	/**
	 * Load the document given a specific id we first try to load the document
	 * locally (global and specific)
	 * 
	 * @param guid
	 * @return
	 * @throws Exception
	 */
	private IDocumentClient loadDocument(UniqueID guid) throws Exception {
		IDocumentRemote doc = server.getDocument(guid);
		IDocumentClient client = new DocumentClient(doc);

		// Load the bean
		BasicBean bean = null;
		File localFile = new File(getSpecificPath(), client.getFilename());
		File globalFile = new File(getGlobalPath(), client.getFilename());
		if (localFile.exists()) {
			// load local bean if exist
			bean = DocumentIO.loadBeanLocal(localFile);
		} else if (globalFile.exists()) {
			// load global bean if exist
			bean = DocumentIO.loadBeanLocal(globalFile);
		} else {
			// load bean from the server
			bean = doc.getBean();
		}
		client.loadDocument(bean);

		return client;
	}

	public void createDocument(BasicBean bean) {
		try {
			this.server.createDocument(bean);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void saveLocal() throws Exception {
		synchronized (documents) {
			for (IDocumentClient doc : documents.values()) {
				doc.save(getSpecificPath());
			}
		}
	}

	public void saveGlobalBean(BasicBean bean) throws Exception {
		DocumentLocal doc = new DocumentLocal(bean);
		doc.save(getGlobalPath());
	}

	public String getSpecificPath() {
		return specificPath;
	}

	public String getGlobalPath() {
		return globalPath;
	}

	public void removeDocument(UniqueID beanId) {
		try {
			this.server.deleteDocument(beanId);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public void removeDocument(BasicBean bean) {
		try {
			this.server.deleteDocument(bean.getId());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	private synchronized void removeRemoteDocument(UniqueID guid) {
		IDocumentClient doc;
		synchronized (documents) {
			doc = this.documents.remove(guid);
		}
		for (DocumentManagerListener listener : getListeners()) {
			listener.beanRemoved(doc.getBeanEncapsulator().getBean());
		}
	}

	public void addBeanListenerClient(DocumentManagerListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeDocumentManagerClient(DocumentManagerListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected HashSet<DocumentManagerListener> getListeners() {
		HashSet<DocumentManagerListener> copy;
		synchronized (listeners) {
			copy = (HashSet<DocumentManagerListener>) listeners.clone();
		}
		return copy;
	}

	private class CampaignClientObserver extends UnicastRemoteObject implements
			ServerListener {
		private static final long serialVersionUID = 1L;

		protected CampaignClientObserver() throws RemoteException {
			super();
		}

		@Override
		public void documentAdded(UniqueID guid) throws RemoteException {
			addDocument(guid);
		}

		@Override
		public void documentRemoved(UniqueID guid) throws RemoteException {
			removeRemoteDocument(guid);
		}
	}
}
