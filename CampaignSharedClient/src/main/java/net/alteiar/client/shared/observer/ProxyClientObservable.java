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
package net.alteiar.client.shared.observer;

import java.io.Serializable;
import java.rmi.RemoteException;

import net.alteiar.ExceptionTool;
import net.alteiar.server.shared.campaign.IGUID;
import net.alteiar.server.shared.observer.IGUIDRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ProxyClientObservable<E extends IGUIDRemote> extends
		BaseObservable implements IGUID, Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	protected final E remoteObject;

	public ProxyClientObservable(E remote) {
		remoteObject = remote;
		try {
			id = remoteObject.getId();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	public E getRemoteReference() {
		return remoteObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IGUID)) {
			return false;
		}
		IGUID other = (IGUID) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}
}
