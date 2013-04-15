package net.alteiar.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import net.alteiar.shared.UniqueID;

public class DocumentManager {

	public static DocumentManager connect(String localAddress,
			String serverAddress, String port, String campaignPath,
			String permaPath) throws RemoteException, MalformedURLException,
			NotBoundException {
		LoggerConfig.CLIENT_LOGGER.log(Level.INFO, "Connect from "
				+ localAddress + " to " + serverAddress + " at " + port);

		DocumentManager documentManager = null;
		IServerDocument campaign = null;

		Remote remoteObject;
		System.setProperty("java.rmi.server.hostname", localAddress);
		String[] allRemoteNames = RmiRegistry.list("//" + serverAddress + ":"
				+ port);

		for (String remoteName : allRemoteNames) {
			remoteObject = RmiRegistry.lookup(remoteName);
			LoggerConfig.CLIENT_LOGGER.log(Level.INFO,
					"Find an rmi registry object: " + remoteName);
			if (remoteObject instanceof IServerDocument) {
				campaign = (IServerDocument) remoteObject;
				documentManager = new DocumentManager(campaign, campaignPath,
						permaPath);
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
	private final HashMap<UniqueID, DocumentClient> documents;
	private final String campaignPath;
	private final String permaPath;

	private DocumentManager(IServerDocument server, String localPath,
			String permaPath) throws RemoteException {
		campaignPath = localPath;
		this.permaPath = permaPath;
		documents = new HashMap<UniqueID, DocumentClient>();
		listeners = new HashSet<DocumentManagerListener>();
		this.server = server;
		this.server.addServerListener(new CampaignClientObserver());
	}

	public void loadDocuments() {
		try {
			for (UniqueID doc : this.server.getDocuments()) {
				addDocument(doc);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DocumentClient getDocument(UniqueID id) {
		return documents.get(id);
	}

	public DocumentClient getDocument(UniqueID id, Long timeout) {
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

	private synchronized void addDocument(UniqueID guid) {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createDocument(DocumentPath path, BeanEncapsulator bean,
			Boolean perma) {
		// long guid = -1L;
		try {
			this.server.createDocument(path, bean, perma);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return guid;
	}

	public ArrayList<DocumentClient> getDocuments() {
		return new ArrayList<DocumentClient>(documents.values());
	}

	public String getCampaignPath() {
		return campaignPath;
	}

	public String getPermaPath() {
		return permaPath;
	}

	public void removeDocument(UniqueID beanId) {
		try {
			this.server.deleteDocument(beanId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeDocument(BasicBeans bean) {
		try {
			this.server.deleteDocument(bean.getId());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void removeRemoteDocument(UniqueID guid) {
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
		public void documentAdded(UniqueID guid) throws RemoteException {
			addDocument(guid);
		}

		@Override
		public void documentRemoved(UniqueID guid) throws RemoteException {
			removeRemoteDocument(guid);
		}
	}
}
