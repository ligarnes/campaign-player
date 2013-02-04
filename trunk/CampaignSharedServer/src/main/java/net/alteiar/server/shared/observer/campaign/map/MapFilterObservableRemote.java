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
package net.alteiar.server.shared.observer.campaign.map;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.geometry.Polygon2D;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapFilterObservableRemote extends BaseObservableRemote {
	private static final long serialVersionUID = 1L;

	private static Class<?> LISTENER = IMapFilterObserverRemote.class;

	/**
	 * @throws RemoteException
	 */
	public MapFilterObservableRemote() throws RemoteException {
		super();
	}

	public void addFilterListener(IMapFilterObserverRemote listener)
			throws RemoteException {
		this.addListener(LISTENER, listener);
	}

	public void removeListener(IMapFilterObserverRemote listener)
			throws RemoteException {
		this.removeListener(LISTENER, listener);
	}

	protected void notifyMapFilterChanged(Polygon2D newVisibleArea) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapFilterChangedTask(
					this, observer, newVisibleArea));
		}
	}

	private class MapFilterChangedTask extends BaseNotify {
		private final Polygon2D newVisibleArea;

		public MapFilterChangedTask(MapFilterObservableRemote observable,
				Remote observer, Polygon2D remote) {
			super(observable, LISTENER, observer);
			this.newVisibleArea = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMapFilterObserverRemote) observer).filterChanged(newVisibleArea);
		}

		@Override
		public String getStartText() {
			return "start map filter change)";
		}

		@Override
		public String getFinishText() {
			return "finish map filter changed";
		}
	}
}
