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
package net.alteiar.server.shared.campaign.battle.map.element;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.rmi.RemoteException;
import java.util.List;

import net.alteiar.server.shared.geometry.Polygon2D;
import net.alteiar.server.shared.observer.campaign.map.MapFilterObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapFilterRemote extends MapFilterObservableRemote implements
		IFilterRemote {
	private static final long serialVersionUID = 1L;

	private transient Area visible;

	/**
	 * @throws RemoteException
	 */
	public MapFilterRemote() throws RemoteException {
		super();
		visible = new Area();
	}

	public void showPolygon(List<Point> cwPts) {
		Polygon newRectangle = new Polygon();

		for (Point point : cwPts) {
			newRectangle.addPoint(point.x, point.y);
		}

		show(new Area(newRectangle));
	}

	public void hidePolygon(List<Point> cwPts) {
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
}
