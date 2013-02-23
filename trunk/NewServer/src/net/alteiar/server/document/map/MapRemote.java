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
package net.alteiar.server.document.map;

import java.rmi.RemoteException;

import net.alteiar.server.BaseObservableRemote;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.DocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapRemote extends DocumentRemote implements IMapRemote {

	private static final long serialVersionUID = 7146516259514104557L;

	private final String mapName;

	private final int width;
	private final int height;

	// private final SynchronizedList<IMapElementObservableRemote> allElements;

	private final Long background;
	private final Long filterRemote;

	private Scale scale;

	/**
	 * @throws RemoteException
	 */
	public MapRemote(String mapName, int width, int height, Long background,
			Long filterRemote, Scale scale) throws RemoteException {
		super();

		this.mapName = mapName;

		this.background = background;

		this.width = width;
		this.height = height;

		// this.allElements = new
		// SynchronizedList<IMapElementObservableRemote>();

		this.filterRemote = filterRemote;
		this.scale = scale;
	}

	@Override
	public String getName() throws RemoteException {
		return mapName;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public Integer getHeight() {
		return height;
	}

	/*
	@Override
	public synchronized void addMapElement(IMapElementObservableRemote element)
			throws RemoteException {
		this.allElements.add(element);
		this.notifyMapElementAdded(element);
	}

	@Override
	public synchronized void removedMapElement(
			IMapElementObservableRemote element) throws RemoteException {
		this.allElements.remove(element);
		this.notifyMapElementRemoved(element);
	}

	@Override
	public synchronized IMapElementObservableRemote[] getAllElements()
			throws RemoteException {
		this.allElements.incCounter();
		IMapElementObservableRemote[] mapElements = new IMapElementObservableRemote[this.allElements
				.size()];
		this.allElements.toArray(mapElements);
		this.allElements.decCounter();
		return mapElements;
	}*/

	@Override
	public Long getBackground() throws RemoteException {
		return this.background;
	}

	@Override
	public Long getFilter() throws RemoteException {
		return this.filterRemote;
	}

	@Override
	public Scale getScale() throws RemoteException {
		return scale;
	}

	@Override
	public synchronized void setScale(Scale scale) throws RemoteException {
		this.scale = scale;
		this.notifyMapElementScaleChanged(scale);
	}

	// /////////////// LISTENERS METHODS //////////////////////
	@Override
	public void addMapListener(IMapListenerRemote map) throws RemoteException {
		this.addListener(IMapListenerRemote.class, map);
	}

	@Override
	public void removeMapListener(IMapListenerRemote map)
			throws RemoteException {
		this.removeListener(IMapListenerRemote.class, map);
	}

	/*
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
	}*/

	protected void notifyMapElementScaleChanged(Scale scale) {
		for (IMapListenerRemote observer : this
				.getListener(IMapListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL.addTask(new MapScaleChangeTask(
					this, observer, scale));
		}
	}

	/*
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
	}*/

	private class MapScaleChangeTask extends BaseNotify<IMapListenerRemote> {
		private final Scale scale;

		public MapScaleChangeTask(BaseObservableRemote observable,
				IMapListenerRemote observer, Scale scale) {
			super(observable, IMapListenerRemote.class, observer);
			this.scale = scale;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.mapRescale(scale);
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

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new MapClient(this);
	}
}
