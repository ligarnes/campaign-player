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
package net.alteiar.rmi.client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IRmiRegistryProxy extends Remote {
	static final String RMI_NAME = "rmiregistry";

	/**
	 * Returns a reference, a stub, for the remote object associated with the
	 * specified <code>name</code>.
	 * 
	 * @param name
	 *            a name in URL format (without the scheme component)
	 * @return a reference for a remote object
	 * @exception NotBoundException
	 *                if name is not currently bound
	 * @exception RemoteException
	 *                if registry could not be contacted
	 * @exception MalformedURLException
	 *                if the name is not an appropriately formatted URL
	 */
	public Remote lookup(String name) throws RemoteException;

	/**
	 * Binds the specified <code>name</code> to a remote object.
	 * 
	 * @param name
	 *            a name in URL format (without the scheme component)
	 * @param obj
	 *            a reference for the remote object (usually a stub)
	 * @exception AlreadyBoundException
	 *                if name is already bound
	 * @exception MalformedURLException
	 *                if the name is not an appropriately formatted URL
	 * @exception RemoteException
	 *                if registry could not be contacted
	 */
	public void bind(String name, Remote obj) throws RemoteException;

	/**
	 * Destroys the binding for the specified name that is associated with a
	 * remote object.
	 * 
	 * @param name
	 *            a name in URL format (without the scheme component)
	 * @exception NotBoundException
	 *                if name is not currently bound
	 * @exception MalformedURLException
	 *                if the name is not an appropriately formatted URL
	 * @exception RemoteException
	 *                if registry could not be contacted
	 */
	public void unbind(String name) throws RemoteException;

	/**
	 * Rebinds the specified name to a new remote object. Any existing binding
	 * for the name is replaced.
	 * 
	 * @param name
	 *            a name in URL format (without the scheme component)
	 * @param obj
	 *            new remote object to associate with the name
	 * @exception MalformedURLException
	 *                if the name is not an appropriately formatted URL
	 * @exception RemoteException
	 *                if registry could not be contacted
	 */
	public void rebind(String name, Remote obj) throws RemoteException;

	/**
	 * Returns an array of the names bound in the registry. The names are
	 * URL-formatted (without the scheme component) strings. The array contains
	 * a snapshot of the names present in the registry at the time of the call.
	 * 
	 * @param name
	 *            a registry name in URL format (without the scheme component)
	 * @return an array of names (in the appropriate format) bound in the
	 *         registry
	 * @exception MalformedURLException
	 *                if the name is not an appropriately formatted URL
	 * @exception RemoteException
	 *                if registry could not be contacted.
	 */
	public String[] list(String name) throws RemoteException;
}
