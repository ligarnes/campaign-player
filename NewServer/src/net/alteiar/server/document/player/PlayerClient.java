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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public class PlayerClient extends DocumentClient<IPlayerRemote> {
	private static final long serialVersionUID = 1L;

	private String name;
	private Boolean isMj;

	/**
	 * @param remote
	 * @throws RemoteException
	 */
	public PlayerClient(IPlayerRemote remote) {
		super(remote);
		try {
			name = remote.getName();
			isMj = remote.getIsMj();
		} catch (RemoteException e) {
			// TODO
			e.printStackTrace();
		}
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerClient other = (PlayerClient) obj;
		if (isMj == null) {
			if (other.isMj != null)
				return false;
		} else if (!isMj.equals(other.isMj))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	protected void closeDocument() throws RemoteException {
		// no observer
	}

	private void readObject(ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		initializeTransient();
	}
}
