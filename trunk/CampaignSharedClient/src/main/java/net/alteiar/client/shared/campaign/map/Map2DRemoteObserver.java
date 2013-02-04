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
package net.alteiar.client.shared.campaign.map;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.shared.observer.campaign.map.element.BaseMapElementClient;
import net.alteiar.server.shared.campaign.battle.map.IMap2DRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.observer.campaign.map.IMapObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 *         this class should be observer and will use the notify of the
 *         Map2DClient
 */
public class Map2DRemoteObserver extends UnicastRemoteObject implements
		IMapObserverRemote {
	private static final long serialVersionUID = 2559145398149500009L;

	private final IMap2DRemote remote;
	private final Map2DClient<?> map2D;

	/**
	 * @throws RemoteException
	 */
	public Map2DRemoteObserver(Map2DClient<?> map2D, IMap2DRemote mapRemote)
			throws RemoteException {
		super();
		this.map2D = map2D;

		remote = mapRemote;
		remote.addMapListener(this);
	}

	@Override
	public void mapElementAdded(IMapElementObservableRemote mapElement)
			throws RemoteException {
		BaseMapElementClient element = Map2DClient.loadMapElement(mapElement,
				map2D);

		if (element != null) {
			map2D.addLocalMapElement(element);
		}
	}

	@Override
	public void mapElementRemoved(IMapElementObservableRemote mapElement)
			throws RemoteException {

		BaseMapElementClient element = Map2DClient.loadMapElement(mapElement,
				map2D);

		if (element != null) {
			map2D.removeLocalMapElement(element);
		}
	}

	@Override
	public void mapRescale(Scale scale) throws RemoteException {
		map2D.setLocalScale(scale);
	}
}
