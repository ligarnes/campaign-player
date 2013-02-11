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
import java.util.Observer;

import net.alteiar.server.shared.campaign.IGUID;
import net.alteiar.server.shared.observer.IGUIDRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ProxyClientObservableSimple<E extends IGUIDRemote> extends
		ProxyClientObservable<E> implements IGUID, Serializable {
	private static final long serialVersionUID = 1L;

	public ProxyClientObservableSimple(E remote) {
		super(remote);
	}

	public void addListener(Observer o) {
		this.addListener(Observer.class, o);
	}

	public void removeListener(Observer o) {
		this.removeListener(Observer.class, o);
	}

	protected synchronized void notifyListeners(Object arg) {
		for (Object obs : this.getListener(Observer.class)) {
			((Observer) obs).update(null, arg);
		}
	}

	protected void notifyListeners() {
		notifyListeners(null);
	}
}
