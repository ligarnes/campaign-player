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

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.map.element.CircleClient;
import net.alteiar.client.shared.campaign.map.element.ConeClient;
import net.alteiar.client.shared.campaign.map.element.IMapElementClient;
import net.alteiar.client.shared.campaign.map.element.RayClient;
import net.alteiar.client.shared.observer.campaign.map.Map2DClientObservable;
import net.alteiar.client.shared.observer.campaign.map.element.BaseMapElementClient;
import net.alteiar.server.shared.campaign.battle.map.IMap2DRemote;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.ICircleRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IConeRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IRayRemote;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;
import net.alteiar.shared.tool.SynchronizedList;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Map2DClient<E extends IMap2DRemote> extends
		Map2DClientObservable<E> implements IMap2DClient<E>, Observer {
	private static final long serialVersionUID = 1L;

	private String mapName;

	private BufferedImage background;
	private Map2DFilterClient filter;
	private final SynchronizedList<IMapElementClient> allElements;
	private Scale scale;

	private boolean isMapLoaded;

	protected Map2DClient(E map2D) {
		super(map2D);

		allElements = new SynchronizedList<IMapElementClient>();
		isMapLoaded = false;
		try {
			CampaignClient.WORKER_POOL_CAMPAIGN.addTask(new Map2DLoadTask(this,
					remoteObject));

			// our observer
			new Map2DRemoteObserver(this, this.remoteObject);

		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public static BaseMapElementClient loadMapElement(
			IMapElementObservableRemote mapElement, Map2DClient<?> map2D)
			throws RemoteException {
		BaseMapElementClient element = null;

		if (mapElement instanceof ICircleRemote) {
			element = new CircleClient((ICircleRemote) mapElement, map2D);
		} else if (mapElement instanceof IConeRemote) {
			element = new ConeClient((IConeRemote) mapElement, map2D);
		} else if (mapElement instanceof IRayRemote) {
			element = new RayClient((IRayRemote) mapElement, map2D);
		} else {
			ExceptionTool.showError(null, "Unable to load the map element "
					+ mapElement.getClass());
		}

		return element;
	}

	@Override
	public String getName() {
		return mapName;
	}

	@Override
	public boolean isMapLoaded() {
		return isMapLoaded;
	}

	@Override
	public BufferedImage getBackground() {
		return background;
	}

	@Override
	public BufferedImage getFilter() {
		return this.filter.getShape();
	}

	@Override
	public void showPolygon(List<Point> cwPts) {
		try {
			remoteObject.showPolygon(cwPts);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public void hidePolygon(List<Point> cwPts) {
		try {
			remoteObject.hidePolygon(cwPts);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public void showRectangle(Integer x, Integer y, Integer width,
			Integer height) {
		try {
			List<Point> cwPts = new ArrayList<Point>();
			cwPts.add(new Point(x, y));
			cwPts.add(new Point(x, y + height));
			cwPts.add(new Point(x + width, y + height));
			cwPts.add(new Point(x + width, y));
			remoteObject.showPolygon(cwPts);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public void hideRectangle(Integer x, Integer y, Integer width,
			Integer height) {
		try {
			List<Point> cwPts = new ArrayList<Point>();
			cwPts.add(new Point(x, y));
			cwPts.add(new Point(x, y + height));
			cwPts.add(new Point(x + width, y + height));
			cwPts.add(new Point(x + width, y));
			remoteObject.hidePolygon(cwPts);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public Scale getScale() {
		return scale;
	}

	@Override
	public void setScale(Scale scale) {
		try {
			this.remoteObject.setScale(scale);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Integer getWidth() {
		return getBackground().getWidth();
	}

	@Override
	public Integer getHeight() {
		return getBackground().getHeight();
	}

	@Override
	public IMapElementClient[] getAllElements() {
		allElements.incCounter();
		IMapElementClient[] elements = new IMapElementClient[allElements.size()];
		allElements.toArray(elements);
		allElements.decCounter();
		return elements;
	}

	@Override
	public IMapElementClient getElementAt(Point location) {
		IMapElementClient element = null;
		for (IMapElementClient mapElement : getAllElements()) {
			if (mapElement.isInside(location)) {
				element = mapElement;
				break;
			}
		}
		return element;
	}

	@Override
	public void addCircle(MapElementSize radius, Color color, Point position) {
		try {
			ICircleRemote circle = remoteObject.createCircle(position, color,
					radius);
			remoteObject.addMapElement(circle);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void addCone(MapElementSize radius, Color color, Point position) {
		try {
			IConeRemote cone = remoteObject.createCone(position, color, radius);
			remoteObject.addMapElement(cone);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void addRay(MapElementSize longueur, MapElementSize largueur,
			Color color, Point position) {
		try {
			IRayRemote ray = remoteObject.createRay(position, color, longueur,
					largueur);

			remoteObject.addMapElement(ray);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void removeMapElement(IMapElementClient mapElements) {
		try {
			remoteObject.removedMapElement(mapElements.getRemoteReference());
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	public synchronized void setLocalName(String name) {
		this.mapName = name;
		notifyMapChanged();
	}

	/**
	 * set the local background
	 * 
	 * @param img
	 */
	public synchronized void setLocalBackground(BufferedImage img) {
		background = img;
	}

	public synchronized void setMapLoaded(boolean isMapLoaded) {
		this.isMapLoaded = isMapLoaded;
		this.notifyMapLoaded();
	}

	public synchronized void addLocalMapElement(BaseMapElementClient client) {
		client.addListener(this);
		allElements.add(client);
		notifyMapChanged();
	}

	public synchronized void removeLocalMapElement(BaseMapElementClient elements) {
		elements.removeListener(this);
		allElements.remove(elements);
		notifyMapChanged();
	}

	public synchronized void setLocalScale(Scale scale) {
		this.scale = scale;
		this.notifyMapChanged();
	}

	public synchronized void setLocalFilter(Map2DFilterClient filter) {
		if (this.filter != null) {
			this.filter.removeListener(this);
		}
		this.filter = filter;
		this.filter.addListener(this);
		this.notifyMapChanged();
	}

	@Override
	public void update(Observable o, Object arg) {
		notifyMapChanged();
	}

	@Override
	protected void notifyMapChanged() {
		if (isMapLoaded) {
			super.notifyMapChanged();
		}
	}

}
