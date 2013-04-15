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
package net.alteiar.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.rmi.client.IRmiRegistryProxy;

/**
 * @author Cody Stoutenburg
 * 
 */
public class RmiRegistryProxy extends UnicastRemoteObject implements
		IRmiRegistryProxy {

	private static final long serialVersionUID = -8432259884228815800L;

	public static RmiRegistryProxy INSTANCE;
	private final String bindName;

	public static synchronized void startRmiRegistryProxy(String adressIp,
			int port) throws RemoteException, MalformedURLException {
		if (INSTANCE == null) {
			LocateRegistry.createRegistry(port);

			System.setProperty("java.rmi.server.hostname", adressIp);
			INSTANCE = new RmiRegistryProxy("//" + adressIp + ":" + port + "/"
					+ IRmiRegistryProxy.RMI_NAME);
			Naming.rebind(INSTANCE.getName(), INSTANCE);
		}
	}

	/**
	 * @throws RemoteException
	 */
	private RmiRegistryProxy(String bindName) throws RemoteException {
		super();
		this.bindName = bindName;
	}

	public String getName() {
		return bindName;
	}

	@Override
	public Remote lookup(String name) throws RemoteException {
		Remote remote = null;
		try {
			remote = Naming.lookup(name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		return remote;
	}

	@Override
	public void bind(String name, Remote obj) throws RemoteException {
		try {
			Naming.bind(name, obj);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unbind(String name) throws RemoteException {
		try {
			Naming.unbind(name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rebind(String name, Remote obj) throws RemoteException {
		try {
			Naming.rebind(name, obj);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] list(String name) throws RemoteException {
		String[] lst = {};
		try {
			lst = Naming.list(name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return lst;
	}

}
