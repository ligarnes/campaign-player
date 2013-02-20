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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Cody Stoutenburg
 * 
 *         GUIDRemote : Globally Unique Identifier
 */
public abstract class GUIDRemote extends UnicastRemoteObject implements IGUIDRemote {

	private static final long serialVersionUID = -4294940504488754306L;

	private static Long currentGUID = 0L;

	private static final Long generateNextGUID() {
		long guid = currentGUID;
		currentGUID++;
		return guid;
	}

	private final Long id;

	/**
	 * @throws RemoteException
	 */
	protected GUIDRemote() throws RemoteException {
		super();

		id = generateNextGUID();
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() throws RemoteException {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GUIDRemote other = (GUIDRemote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
