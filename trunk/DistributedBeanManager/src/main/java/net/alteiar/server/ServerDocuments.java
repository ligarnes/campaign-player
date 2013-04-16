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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.logger.LoggerConfig;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.rmi.server.RmiRegistryProxy;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.ThreadPool;

/**
 * @author Cody Stoutenburg
 * 
 */
public final class ServerDocuments extends UnicastRemoteObject implements
		IServerDocument {

	private static final long serialVersionUID = 731240477472043798L;

	public static String CAMPAIGN_MANAGER = "Campaign-manager";
	public static ThreadPool SERVER_THREAD_POOL = null;

	public static ServerDocuments startServer(String addressIp, String port)
			throws RemoteException, MalformedURLException, NotBoundException {

		LoggerConfig.SERVER_LOGGER.log(Level.INFO, "start server at ip: "
				+ addressIp + ", port: " + port);

		System.setProperty("java.rmi.server.hostname", addressIp);
		ServerDocuments server = new ServerDocuments();
		RmiRegistryProxy
				.startRmiRegistryProxy(addressIp, Integer.valueOf(port));

		LoggerConfig.SERVER_LOGGER.log(Level.INFO,
				"create an object at adress = //" + addressIp + ":" + port
						+ "/" + CAMPAIGN_MANAGER);

		// Bind the server in the rmi registry
		RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
				+ CAMPAIGN_MANAGER, server);

		SERVER_THREAD_POOL = new ThreadPool(10);

		return server;
	}

	public static void stopServer() {
		SERVER_THREAD_POOL.shutdown();
		try {
			RmiRegistryProxy.INSTANCE.unbind(CAMPAIGN_MANAGER);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final ArrayList<ServerListener> listeners;
	private final HashMap<UniqueID, IDocumentRemote> documents;

	/**
	 * @throws RemoteException
	 */
	private ServerDocuments() throws RemoteException {
		super();
		documents = new HashMap<UniqueID, IDocumentRemote>();
		listeners = new ArrayList<ServerListener>();
	}

	@Override
	public void addServerListener(ServerListener listener)
			throws RemoteException {
		synchronized (listeners) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void removeServerListener(ServerListener listener)
			throws RemoteException {
		synchronized (listeners) {
			this.listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<ServerListener> getListeners() {
		List<ServerListener> copy;
		synchronized (listeners) {
			copy = (List<ServerListener>) listeners.clone();
		}
		return copy;
	}

	@Override
	public synchronized void createDocument(DocumentPath path,
			BeanEncapsulator bean, Boolean perma) throws RemoteException {
		IDocumentRemote remote = new DocumentRemote(path, bean, perma);
		UniqueID id = bean.getId();
		documents.put(id, remote);

		// TODO notify in multithread
		for (ServerListener listener : getListeners()) {
			listener.documentAdded(id);
		}
	}

	@Override
	public synchronized void deleteDocument(UniqueID guid)
			throws RemoteException {
		IDocumentRemote remote = documents.remove(guid);
		// we may try to delete a document that do not exist
		if (remote != null) {
			remote.closeDocument();

			// TODO notify in multithread
			for (ServerListener listener : getListeners()) {
				listener.documentRemoved(guid);
			}
		}
	}

	@Override
	public IDocumentRemote getDocument(UniqueID guid) throws RemoteException {
		return documents.get(guid);
	}

	@Override
	public UniqueID[] getDocuments() throws RemoteException {
		UniqueID[] docs = new UniqueID[this.documents.size()];
		this.documents.keySet().toArray(docs);
		return docs;
	}
}
