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

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.server.document.DocumentPath;
import net.alteiar.server.document.IDocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IServerDocument extends Remote {

	void addServerListener(ServerListener listener) throws RemoteException;

	void removeServerListener(ServerListener listener) throws RemoteException;

	// Documents
	Long createDocument(DocumentPath path, BeanEncapsulator documentBuilder)
			throws RemoteException;

	void deleteDocument(Long guid) throws RemoteException;

	IDocumentRemote getDocument(Long guid) throws RemoteException;

	Long[] getDocuments() throws RemoteException;
}
