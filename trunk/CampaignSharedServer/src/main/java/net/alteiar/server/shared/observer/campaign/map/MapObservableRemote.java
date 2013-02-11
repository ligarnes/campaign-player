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
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapObservableRemote extends BaseObservableRemote {

	private static final long serialVersionUID = -1808992998811038888L;

	private static final Class<?> MAP_LISTENER = IMapObserverRemote.class;

	/**
	 * @throws RemoteException
	 */
	public MapObservableRemote() throws RemoteException {
		super();
	}

	public void addMapListener(IMapObserverRemote map) throws RemoteException {
		this.addListener(MAP_LISTENER, map);
	}

	public void removeMapListener(IMapObserverRemote map)
			throws RemoteException {
		this.removeListener(MAP_LISTENER, map);
	}

	protected void notifyMapElementAdded(IMapElementObservableRemote newPosition) {
		for (Remote observer : this.getListener(IMapObserverRemote.class)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementChangeTask(
					this, observer, newPosition, true));
		}
	}

	protected void notifyMapElementRemoved(IMapElementObservableRemote removed) {
		for (Remote observer : this.getListener(IMapObserverRemote.class)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementChangeTask(
					this, observer, removed, false));
		}
	}

	protected void notifyMapElementScaleChanged(Scale scale) {
		for (Remote observer : this.getListener(IMapObserverRemote.class)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapScaleChangeTask(
					this, observer, scale));
		}
	}

	private class MapElementChangeTask extends BaseNotify {
		private final IMapElementObservableRemote remote;
		private final boolean isAdded;

		public MapElementChangeTask(BaseObservableRemote observable,
				Remote observer, IMapElementObservableRemote remote,
				boolean isAdded) {
			super(observable, MAP_LISTENER, observer);
			this.remote = remote;
			this.isAdded = isAdded;
		}

		@Override
		protected void doAction() throws RemoteException {
			if (isAdded) {
				((IMapObserverRemote) observer).mapElementAdded(remote);
			} else {
				((IMapObserverRemote) observer).mapElementRemoved(remote);
			}
		}

		@Override
		public String getStartText() {
			return "start map element changed (remove or add)";
		}

		@Override
		public String getFinishText() {
			return "finish map element changed (remove or add)";
		}
	}

	private class MapScaleChangeTask extends BaseNotify {
		private final Scale scale;

		public MapScaleChangeTask(BaseObservableRemote observable,
				Remote observer, Scale scale) {
			super(observable, MAP_LISTENER, observer);
			this.scale = scale;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMapObserverRemote) observer).mapRescale(scale);
		}

		@Override
		public String getStartText() {
			return "start map rescale";
		}

		@Override
		public String getFinishText() {
			return "finish map rescale";
		}
	}
}
