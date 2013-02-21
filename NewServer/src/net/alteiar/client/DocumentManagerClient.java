package net.alteiar.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import net.alteiar.server.IServerDocument;
import net.alteiar.server.ServerListener;
import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.IDocumentRemote;

public abstract class DocumentManagerClient {

	private static CountDownLatch counter = new CountDownLatch(0);

	private static synchronized CountDownLatch getCounterInstance() {
		if (counter.getCount() == 0) {
			counter = new CountDownLatch(1);
		}
		return counter;
	}

	private final IServerDocument server;

	private final HashMap<Long, DocumentClient<?>> documents;

	public DocumentManagerClient(IServerDocument server) throws RemoteException {
		this.server = server;
		this.server.addServerListener(new CampaignClientObserver());

		documents = new HashMap<>();
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

	private void addDocument(Long guid) {
		IDocumentRemote doc;
		try {
			doc = server.getDocument(guid);
			DocumentClient<?> client = doc.buildProxy();
			client.loadDocument();

			this.documents.put(guid, client);

			this.add(client);
			getCounterInstance().countDown();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
	}

	protected abstract void add(DocumentClient<?> client);

	// TODO to change to protected
	public long createDocument(DocumentBuilder documentBuilder) {
		long guid = -1L;
		try {
			guid = this.server.createDocument(documentBuilder);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return guid;
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
