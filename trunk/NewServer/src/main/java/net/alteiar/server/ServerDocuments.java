/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.rmi.server.RmiRegistryProxy;
import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.chat.ChatRoomRemote;
import net.alteiar.shared.ExceptionTool;
import net.alteiar.thread.WorkerPool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ServerDocuments extends BaseObservableRemote implements
		IServerDocument {

	private static final long serialVersionUID = 731240477472043798L;

	public static String CAMPAIGN_MANAGER = "Campaign-manager";

	public static ServerDocuments SERVER_CAMPAIGN_REMOTE = null;

	public final static WorkerPool SERVER_THREAD_POOL = new WorkerPool();

	public static void startServer(String addressIp, int port) {
		SERVER_THREAD_POOL.initWorkPool(10);

		try {
			RmiRegistryProxy.startRmiRegistryProxy(addressIp, port);

			SERVER_CAMPAIGN_REMOTE = new ServerDocuments();
			// Create chat once
			SERVER_CAMPAIGN_REMOTE.createDocument(new DocumentBuilder() {
				private static final long serialVersionUID = 1L;

				@Override
				public IDocumentRemote buildMainDocument()
						throws RemoteException {
					return new ChatRoomRemote();
				}
			});

			LoggerConfig.SERVER_LOGGER.log(Level.INFO, "server adress = //"
					+ addressIp + ":" + port + "/" + CAMPAIGN_MANAGER);

			// Bind the server in the rmi registry
			RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
					+ CAMPAIGN_MANAGER, SERVER_CAMPAIGN_REMOTE);
		} catch (MalformedURLException e) {
			ExceptionTool.showError(e);
		} catch (NotBoundException e) {
			ExceptionTool.showError(e);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	private final HashMap<Long, IDocumentRemote> documents;

	/**
	 * @throws RemoteException
	 */
	protected ServerDocuments() throws RemoteException {
		super();
		documents = new HashMap<Long, IDocumentRemote>();
	}

	@Override
	public synchronized Long createDocument(DocumentBuilder documentBuilder)
			throws RemoteException {
		for (IDocumentRemote remote : documentBuilder.buildDocuments()) {
			Long id = remote.getPath().getId();
			documents.put(id, remote);
			this.notifyDocumentAdd(id);
		}

		IDocumentRemote remote = documentBuilder.buildMainDocument();
		Long id = remote.getPath().getId();
		documents.put(id, remote);
		this.notifyDocumentAdd(id);

		return id;
	}

	@Override
	public synchronized void deleteDocument(Long guid) throws RemoteException {
		IDocumentRemote remote = documents.remove(guid);
		remote.closeDocument();
		this.notifyDocumentRemoved(guid);
	}

	@Override
	public IDocumentRemote getDocument(Long guid) throws RemoteException {
		return documents.get(guid);
	}

	@Override
	public Long[] getDocuments() throws RemoteException {
		Long[] docs = new Long[this.documents.size()];
		this.documents.keySet().toArray(docs);
		return docs;
	}

	// ///////// OBSERVER METHODS
	@Override
	public synchronized void addServerListener(ServerListener observer)
			throws RemoteException {
		this.addListener(ServerListener.class, observer);
	}

	@Override
	public synchronized void removeServerListener(ServerListener observer)
			throws RemoteException {
		this.removeListener(ServerListener.class, observer);
	}

	protected void notifyDocumentAdd(Long guid) {
		for (ServerListener obs : this.getListener(ServerListener.class)) {
			SERVER_THREAD_POOL.addTask(new DocumentAdded(this, obs, guid));
		}
	}

	protected void notifyDocumentRemoved(Long guid) {
		for (ServerListener obs : this.getListener(ServerListener.class)) {
			SERVER_THREAD_POOL.addTask(new DocumentRemoved(this, obs, guid));
		}
	}

	public class DocumentAdded extends BaseNotify<ServerListener> {
		protected final Long guid;

		public DocumentAdded(ServerDocuments observable,
				ServerListener observer, Long guid) {
			super(observable, ServerListener.class, observer);
			this.guid = guid;
		}

		@Override
		protected void doAction() throws RemoteException {
			this.observer.documentAdded(guid);
		}
	}

	public class DocumentRemoved extends BaseNotify<ServerListener> {
		protected final Long guid;

		public DocumentRemoved(ServerDocuments observable,
				ServerListener observer, Long guid) {
			super(observable, ServerListener.class, observer);
			this.guid = guid;
		}

		@Override
		protected void doAction() throws RemoteException {
			this.observer.documentRemoved(guid);
		}
	}
}
