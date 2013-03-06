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
import java.util.HashSet;

import net.alteiar.server.BaseObservableRemote;
import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class MapRemote extends DocumentRemote implements IMapRemote {

	private static final long serialVersionUID = 7146516259514104557L;

	private final String mapName;

	private final int width;
	private final int height;

	private final HashSet<Long> elements;

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

		elements = new HashSet<Long>();

		this.filterRemote = filterRemote;
		this.scale = scale;
	}

	@Override
	public String getName() throws RemoteException {
		return mapName;
	}

	@Override
	public Integer getWidth() throws RemoteException {
		return width;
	}

	@Override
	public Integer getHeight() throws RemoteException {
		return height;
	}

	@Override
	public void addMapElement(Long mapElement) throws RemoteException {
		synchronized (elements) {
			elements.add(mapElement);
		}
		notifyMapElementAdded(mapElement);
	}

	@Override
	public void removeMapElement(Long mapElement) throws RemoteException {
		synchronized (elements) {
			elements.remove(mapElement);
		}
		notifyMapElementRemoved(mapElement);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashSet<Long> getMapElements() throws RemoteException {
		HashSet<Long> elementsCopy = new HashSet<Long>();
		synchronized (elements) {
			elementsCopy = (HashSet<Long>) elements.clone();
		}
		return elementsCopy;
	}

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
		this.notifyMapScaleChanged(scale);
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
	 * protected void notifyMapElementAdded(IMapElementObservableRemote
	 * newPosition) { for (Remote observer :
	 * this.getListener(IMapObserverRemote.class)) {
	 * ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementChangeTask( this,
	 * observer, newPosition, true)); } }
	 * 
	 * protected void notifyMapElementRemoved(IMapElementObservableRemote
	 * removed) { for (Remote observer :
	 * this.getListener(IMapObserverRemote.class)) {
	 * ServerCampaign.SERVER_THREAD_POOL.addTask(new MapElementChangeTask( this,
	 * observer, removed, false)); } }
	 */

	protected void notifyMapScaleChanged(Scale scale) {
		for (IMapListenerRemote observer : this
				.getListener(IMapListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL.addTask(new MapScaleChangeTask(
					this, observer, scale));
		}
	}

	protected void notifyMapElementAdded(Long mapElement) {
		for (IMapListenerRemote observer : this
				.getListener(IMapListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new MapElementAddedRemoved(this, observer,
							mapElement, true));
		}
	}

	protected void notifyMapElementRemoved(Long mapElement) {
		for (IMapListenerRemote observer : this
				.getListener(IMapListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new MapElementAddedRemoved(this, observer,
							mapElement, false));
		}
	}

	private class MapElementAddedRemoved extends BaseNotify<IMapListenerRemote> {
		private final Long elementId;
		private final Boolean isAdded;

		public MapElementAddedRemoved(BaseObservableRemote observable,
				IMapListenerRemote observer, Long elementId, Boolean isAdded) {
			super(observable, IMapListenerRemote.class, observer);
			this.elementId = elementId;
			this.isAdded = isAdded;
		}

		@Override
		public String getStartText() {
			return "element added or removed";
		}

		@Override
		public String getFinishText() {
			return "element added or removed";
		}

		@Override
		protected void doAction() throws RemoteException {
			if (isAdded) {
				observer.mapElementAdded(elementId);
			} else {
				observer.mapElementRemoved(elementId);
			}
		}

	}

	/*
	 * private class MapElementChangeTask extends BaseNotify { private final
	 * IMapElementObservableRemote remote; private final boolean isAdded;
	 * 
	 * public MapElementChangeTask(BaseObservableRemote observable, Remote
	 * observer, IMapElementObservableRemote remote, boolean isAdded) {
	 * super(observable, MAP_LISTENER, observer); this.remote = remote;
	 * this.isAdded = isAdded; }
	 * 
	 * @Override protected void doAction() throws RemoteException { if (isAdded)
	 * { ((IMapObserverRemote) observer).mapElementAdded(remote); } else {
	 * ((IMapObserverRemote) observer).mapElementRemoved(remote); } }
	 * 
	 * @Override public String getStartText() { return
	 * "start map element changed (remove or add)"; }
	 * 
	 * @Override public String getFinishText() { return
	 * "finish map element changed (remove or add)"; } }
	 */

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
}
