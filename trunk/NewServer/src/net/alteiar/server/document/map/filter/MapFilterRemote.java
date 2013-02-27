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
package net.alteiar.server.document.map.filter;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.rmi.RemoteException;

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentRemote;
import net.alteiar.shared.Polygon2D;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapFilterRemote extends DocumentRemote implements IMapFilterRemote {
	private static final long serialVersionUID = 1L;

	private final Area visible;
	private final int width;
	private final int height;

	/**
	 * @throws RemoteException
	 */
	public MapFilterRemote(int width, int height) throws RemoteException {
		super();
		this.visible = new Area();

		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() throws RemoteException {
		return this.width;
	}

	@Override
	public int getHeight() throws RemoteException {
		return this.height;
	}

	@Override
	public void showPolygon(Point[] cwPts) {
		Polygon newRectangle = new Polygon();

		for (Point point : cwPts) {
			newRectangle.addPoint(point.x, point.y);
		}

		show(new Area(newRectangle));
	}

	@Override
	public void hidePolygon(Point[] cwPts) {
		Polygon newRectangle = new Polygon();

		for (Point point : cwPts) {
			newRectangle.addPoint(point.x, point.y);
		}

		hide(new Area(newRectangle));
	}

	protected void show(Area newArea) {
		visible.add(newArea);
		this.notifyMapFilterChanged(new Polygon2D(visible));
	}

	protected void hide(Area newArea) {
		visible.subtract(newArea);
		this.notifyMapFilterChanged(new Polygon2D(visible));
	}

	@Override
	public Polygon2D getVisibleRectangle() throws RemoteException {
		return new Polygon2D(visible);
	}

	@Override
	public MapFilterClient buildProxy() throws RemoteException {
		return new MapFilterClient(this);
	}

	// //////////// Listeners methods //////////////////
	@Override
	public void addFilterListener(IMapFilterListenerRemote listener)
			throws RemoteException {
		this.addListener(IMapFilterListenerRemote.class, listener);
	}

	@Override
	public void removeFilterListener(IMapFilterListenerRemote listener)
			throws RemoteException {
		this.removeListener(IMapFilterListenerRemote.class, listener);
	}

	protected void notifyMapFilterChanged(Polygon2D newVisibleArea) {
		for (IMapFilterListenerRemote observer : this
				.getListener(IMapFilterListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new MapFilterChangedTask(this, observer,
							newVisibleArea));
		}
	}

	private class MapFilterChangedTask extends
			BaseNotify<IMapFilterListenerRemote> {
		private final Polygon2D newVisibleArea;

		public MapFilterChangedTask(MapFilterRemote observable,
				IMapFilterListenerRemote observer, Polygon2D remote) {
			super(observable, IMapFilterListenerRemote.class, observer);
			this.newVisibleArea = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.filterChanged(newVisibleArea);
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
