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

import net.alteiar.client.bean.BasicBean;
import net.alteiar.rmi.client.RmiRegistry;
import net.alteiar.rmi.server.RmiRegistryProxy;
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;
import net.alteiar.thread.ThreadPoolUtils;

import org.apache.log4j.Logger;

/**
 * @author Cody Stoutenburg
 * 
 */
public final class ServerDocuments extends UnicastRemoteObject implements
		IServerDocument {

	private static final long serialVersionUID = 731240477472043798L;

	public static String CAMPAIGN_MANAGER = "Campaign-manager";

	public static ServerDocuments startServer(String addressIp, String port,
			String campaignPath) throws RemoteException, MalformedURLException,
			NotBoundException {

		Logger.getLogger(ServerDocuments.class).info(
				"start server at ip: " + addressIp + ", port: " + port);

		// start server thread pool
		ThreadPoolUtils.startServerThreadPool();

		System.setProperty("java.rmi.server.hostname", addressIp);
		ServerDocuments server = new ServerDocuments(campaignPath);
		RmiRegistryProxy
				.startRmiRegistryProxy(addressIp, Integer.valueOf(port));

		Logger.getLogger(ServerDocuments.class).info(
				"create an object at adress = //" + addressIp + ":" + port
						+ "/" + CAMPAIGN_MANAGER);

		// Bind the server in the rmi registry
		RmiRegistry.rebind("//" + addressIp + ":" + port + "/"
				+ CAMPAIGN_MANAGER, server);

		return server;
	}

	public static void stopServer() {
		ThreadPoolUtils.getServerPool().shutdown();

		// TODO fail to unbind need to check
		/*
		 * try { RmiRegistryProxy.INSTANCE.unbind(CAMPAIGN_MANAGER); } catch
		 * (RemoteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	private final String campaignPath;
	private final ArrayList<ServerListener> listeners;
	private final HashMap<UniqueID, IDocumentRemote> documents;

	/**
	 * @throws RemoteException
	 */
	private ServerDocuments(String campaignPath) throws RemoteException {
		super();
		this.campaignPath = campaignPath;
		documents = new HashMap<UniqueID, IDocumentRemote>();
		listeners = new ArrayList<ServerListener>();
	}

	@Override
	public int getDocumentCount() throws RemoteException {
		return documents.size();
	}

	@Override
	public String getSpecificPath() throws RemoteException {
		return campaignPath;
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
	public synchronized void createDocument(BasicBean bean)
			throws RemoteException {
		IDocumentRemote remote = new DocumentRemote(bean);
		final UniqueID id = bean.getId();
		documents.put(id, remote);

		for (final ServerListener listener : getListeners()) {
			ThreadPoolUtils.getServerPool().execute(new MyRunnable() {
				@Override
				public String getTaskName() {
					return "Create documents";
				}

				@Override
				public void run() {
					try {
						listener.documentAdded(id);
					} catch (RemoteException e) {
						listeners.remove(listener);
					}
				}
			});
		}
	}

	@Override
	public synchronized void deleteDocument(final UniqueID guid)
			throws RemoteException {
		IDocumentRemote remote = documents.remove(guid);
		// we may try to delete a document that do not exist
		if (remote != null) {
			remote.closeDocument();

			for (final ServerListener listener : getListeners()) {
				ThreadPoolUtils.getServerPool().execute(new MyRunnable() {
					@Override
					public String getTaskName() {
						return "Delete documents";
					}

					@Override
					public void run() {
						try {
							listener.documentRemoved(guid);
						} catch (RemoteException e) {
							listeners.remove(listener);
						}
					}
				});
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
