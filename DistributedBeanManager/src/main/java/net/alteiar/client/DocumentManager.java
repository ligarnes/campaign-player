package net.alteiar.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.server.IServerDocument;
import net.alteiar.server.ServerListener;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.server.document.IDocumentRemote;

public class DocumentManager {

	public static DocumentManager connect(String localAddress,
			String serverAddress, String port, String campaignPath,
			String name, Boolean isMj) throws RemoteException,
			MalformedURLException, NotBoundException {
		DocumentManager documentManager = null;

		IServerDocument campaign = null;

		Remote remoteObject;
		System.setProperty("java.rmi.server.hostname", localAddress);
		String[] allRemoteNames = RmiRegistry.list("//" + serverAddress + ":"
				+ port);

		for (String remoteName : allRemoteNames) {
			remoteObject = RmiRegistry.lookup(remoteName);
			LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "RMI REGISTRY: "
					+ remoteName);
			if (remoteObject instanceof IServerDocument) {
				campaign = (IServerDocument) remoteObject;
				documentManager = new DocumentManager(campaign, campaignPath);
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
	private final HashMap<Long, DocumentClient> documents;

	public DocumentManager(IServerDocument server, String localPath)
			throws RemoteException {
		documents = new HashMap<Long, DocumentClient>();
		listeners = new HashSet<DocumentManagerListener>();

		this.server = server;
		this.server.addServerListener(new CampaignClientObserver());
	}

	public void loadDocuments() {
		try {
			for (Long doc : this.server.getDocuments()) {
				addDocument(doc);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DocumentClient getDocument(Long id) {
		return documents.get(id);
	}

	public DocumentClient getDocument(Long id, Long timeout) {
		Long begin = System.currentTimeMillis();

		DocumentClient value = documents.get(id);

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

	private synchronized void addDocument(Long guid) {
		IDocumentRemote doc;
		try {
			doc = server.getDocument(guid);
			DocumentClient client = doc.buildProxy();
			client.loadDocument();

			synchronized (documents) {
				this.documents.put(guid, client);
			}

			for (DocumentManagerListener listener : getListeners()) {
				listener.documentAdded(client);
			}
			getCounterInstance().countDown();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public void createDocument(BeanEncapsulator bean) {
		// long guid = -1L;
		try {
			this.server.createDocument(new DocumentPath("", ""), bean);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return guid;
	}

	public void removeDocument(BasicBeans bean) {
		try {
			this.server.deleteDocument(bean.getId());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void removeDocument(Long guid) {
		DocumentClient doc;
		synchronized (documents) {
			doc = this.documents.remove(guid);
		}
		for (DocumentManagerListener listener : getListeners()) {
			listener.documentRemoved(doc);
		}
	}

	public void addDocumentManagerClient(DocumentManagerListener listener) {
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
		public void documentAdded(Long guid) throws RemoteException {
			addDocument(guid);
		}

		@Override
		public void documentRemoved(Long guid) throws RemoteException {
			removeDocument(guid);
		}
	}
}
