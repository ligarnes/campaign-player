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
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.thread.WorkerPool;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ServerDocuments extends UnicastRemoteObject implements
		IServerDocument {

	private static final long serialVersionUID = 731240477472043798L;

	public static String CAMPAIGN_MANAGER = "Campaign-manager";

	// public static ServerDocuments SERVER_CAMPAIGN_REMOTE = null;

	public final static WorkerPool SERVER_THREAD_POOL = new WorkerPool();

	public static ServerDocuments startServer(String addressIp, String port)
			throws RemoteException, MalformedURLException, NotBoundException {
		ServerDocuments server = new ServerDocuments();
		RmiRegistryProxy
				.startRmiRegistryProxy(addressIp, Integer.valueOf(port));

		// Create chat once

		LoggerConfig.SERVER_LOGGER.log(Level.INFO, "server adress = //"
				+ addressIp + ":" + port + "/" + CAMPAIGN_MANAGER);

		// Bind the server in the rmi registry
		RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
				+ CAMPAIGN_MANAGER, server);

		SERVER_THREAD_POOL.initWorkPool(10);

		return server;
	}

	private final ArrayList<ServerListener> listeners;
	private final HashMap<Long, IDocumentRemote> documents;

	/**
	 * @throws RemoteException
	 */
	protected ServerDocuments() throws RemoteException {
		super();
		documents = new HashMap<Long, IDocumentRemote>();
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
	public synchronized Long createDocument(BeanEncapsulator bean)
			throws RemoteException {
		IDocumentRemote remote = new DocumentRemote(bean);
		Long id = remote.getPath().getId();
		documents.put(id, remote);

		// TODO notify in multithread
		for (ServerListener listener : getListeners()) {
			listener.documentAdded(id);
		}

		return id;
	}

	@Override
	public synchronized void deleteDocument(Long guid) throws RemoteException {
		IDocumentRemote remote = documents.remove(guid);
		remote.closeDocument();

		// TODO notify in multithread
		for (ServerListener listener : getListeners()) {
			listener.documentRemoved(guid);
		}
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
}
