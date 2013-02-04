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

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.ServerCampaign;
import net.alteiar.server.shared.observer.BaseObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementObservableRemote extends BaseObservableRemote {

	private static final long serialVersionUID = -1754213512563839144L;

	private static final Class<?> LISTENER = IMapElementObserverRemote.class;

	public MapElementObservableRemote() throws RemoteException {
		super();
	}

	public void addMapElementListener(IMapElementObserverRemote map)
			throws RemoteException {
		this.addListener(LISTENER, map);
	}

	public void removeMapElementListener(IMapElementObserverRemote map)
			throws RemoteException {
		this.removeListener(LISTENER, map);
	}

	protected void notifyElementMoved(Point newPosition) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementMovedTask(
					this, observer, newPosition));
		}
	}

	protected void notifyElementHidden(Boolean isHidden) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementHiddenTask(
					this, observer, isHidden));
		}
	}

	protected void notifyElementRotate(Double angle) {
		for (Remote observer : this.getListener(LISTENER)) {
			ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementRotateTask(
					this, observer, angle));
		}
	}

	private class MapElementMovedTask extends BaseNotify {
		private final Point newPoint;

		public MapElementMovedTask(MapElementObservableRemote observable,
				Remote observer, Point remote) {
			super(observable, LISTENER, observer);
			this.newPoint = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMapElementObserverRemote) observer).elementMoved(newPoint);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}

	private class MapElementHiddenTask extends BaseNotify {
		private final Boolean isHidden;

		public MapElementHiddenTask(MapElementObservableRemote observable,
				Remote observer, Boolean remote) {
			super(observable, LISTENER, observer);
			this.isHidden = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMapElementObserverRemote) observer).elementHidden(isHidden);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}

	private class MapElementRotateTask extends BaseNotify {
		private final Double angle;

		public MapElementRotateTask(MapElementObservableRemote observable,
				Remote observer, Double angle) {
			super(observable, LISTENER, observer);
			this.angle = angle;
		}

		@Override
		protected void doAction() throws RemoteException {
			((IMapElementObserverRemote) observer).elementRotate(angle);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}
}
