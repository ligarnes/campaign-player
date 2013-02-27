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
package net.alteiar.server.document.player;

import java.io.File;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PlayerClient extends DocumentClient<IPlayerRemote> {
	private static final long serialVersionUID = 1L;

	private final String name;
	private final Boolean isMj;

	/**
	 * @param remote
	 * @throws RemoteException
	 */
	public PlayerClient(IPlayerRemote remote) throws RemoteException {
		super(remote);

		name = remote.getName();
		isMj = remote.getIsMj();
	}

	public String getName() {
		return name;
	}

	public Boolean isMj() {
		return isMj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isMj == null) ? 0 : isMj.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	protected void closeDocument() throws RemoteException {
		// no observer
	}

	@Override
	protected void loadDocumentLocal(File file) {
		// nothing to load
	}

	@Override
	protected void loadDocumentRemote() throws RemoteException {
		// nothing to load
	}
}
