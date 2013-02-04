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
import java.util.List;

import net.alteiar.client.shared.campaign.map.element.IMapElement;
import net.alteiar.client.shared.campaign.map.element.IMapElementClient;
import net.alteiar.client.shared.observer.IProxyClient;
import net.alteiar.client.shared.observer.campaign.map.IMapObserver;
import net.alteiar.server.shared.campaign.battle.map.Scale;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;
import net.alteiar.server.shared.observer.IGUIDRemote;

/**
 * @author Cody Stoutenburg
 * 
 *         is the usable interface for Map2DClient please do not use directly
 *         Map2DClient because it have some methode that are needed for caching
 *         etc...
 */
public interface IMap2DClient<E extends IGUIDRemote> extends IProxyClient<E> {

	void addMapListener(IMapObserver listener);

	void removeMapListener(IMapObserver listener);

	boolean isMapLoaded();

	BufferedImage getBackground();

	BufferedImage getFilter();

	void showPolygon(List<Point> cwPts);

	void hidePolygon(List<Point> cwPts);

	void showRectangle(Integer x, Integer y, Integer width, Integer height);

	void hideRectangle(Integer x, Integer y, Integer width, Integer height);

	Scale getScale();

	Integer getWidth();

	Integer getHeight();

	List<IMapElementClient> getAllElements();

	IMapElement getElementAt(Point location);

	void removeMapElement(IMapElementClient mapElement);

	void addCircle(MapElementSize radius, Color color, Point position);

	void addCone(MapElementSize radius, Color color, Point position);

	void addRay(MapElementSize width, MapElementSize height, Color color,
			Point position);

	void setScale(Scale scale);
}
