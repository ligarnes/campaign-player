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
package net.alteiar.server.shared.campaign.battle.map;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.List;

import net.alteiar.SerializableFile;
import net.alteiar.server.shared.campaign.battle.map.element.IFilterRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IRayRemote;
import net.alteiar.server.shared.campaign.battle.map.element.MapElementCircleRemote;
import net.alteiar.server.shared.campaign.battle.map.element.MapElementConeRemote;
import net.alteiar.server.shared.campaign.battle.map.element.MapElementRayRemote;
import net.alteiar.server.shared.campaign.battle.map.element.MapFilterRemote;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;
import net.alteiar.server.shared.observer.campaign.map.MapObservableRemote;
import net.alteiar.shared.tool.SynchronizedList;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class Map2DRemote extends MapObservableRemote implements
		IMap2DRemote {

	private static final long serialVersionUID = 7146516259514104557L;

	private final SynchronizedList<IMapElementObservableRemote> allElements;

	private final SerializableFile background;
	private final MapFilterRemote filterRemote;

	private Scale scale;

	/**
	 * @throws RemoteException
	 */
	public Map2DRemote(SerializableFile background, Scale scale)
			throws RemoteException {
		super();

		this.background = background;

		this.allElements = new SynchronizedList<IMapElementObservableRemote>();

		filterRemote = new MapFilterRemote();
		this.scale = scale;
	}

	@Override
	public void addMapElement(IMapElementObservableRemote element)
			throws RemoteException {
		this.allElements.add(element);
		this.notifyMapElementAdded(element);
	}

	@Override
	public void removedMapElement(IMapElementObservableRemote element)
			throws RemoteException {
		this.allElements.remove(element);
		this.notifyMapElementRemoved(element);
	}

	@Override
	public List<IMapElementObservableRemote> getAllElements()
			throws RemoteException {
		return this.allElements.getUnmodifiableList();
	}

	@Override
	public SerializableFile getBackground() throws RemoteException {
		return background;
	}

	@Override
	public IFilterRemote getFilter() throws RemoteException {
		return this.filterRemote;
	}

	@Override
	public void showPolygon(List<Point> cwPts) throws RemoteException {
		this.filterRemote.showPolygon(cwPts);
	}

	@Override
	public void hidePolygon(List<Point> cwPts) throws RemoteException {
		this.filterRemote.hidePolygon(cwPts);
	}

	@Override
	public MapElementCircleRemote createCircle(Point position, Color color,
			MapElementSize radius) throws RemoteException {
		return new MapElementCircleRemote(position, color, radius);
	}

	@Override
	public MapElementConeRemote createCone(Point position, Color color,
			MapElementSize heigth) throws RemoteException {
		return new MapElementConeRemote(position, color, heigth);
	}

	@Override
	public IRayRemote createRay(Point position, Color color,
			MapElementSize width, MapElementSize height) throws RemoteException {
		return new MapElementRayRemote(position, color, width, height);
	}

	@Override
	public Scale getScale() throws RemoteException {
		return scale;
	}

	@Override
	public void setScale(Scale scale) throws RemoteException {
		this.scale = scale;
		this.notifyMapElementScaleChanged(scale);
	}

}
