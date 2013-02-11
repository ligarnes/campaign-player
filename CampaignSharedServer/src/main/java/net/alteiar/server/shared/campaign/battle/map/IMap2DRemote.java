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
import net.alteiar.server.shared.campaign.battle.map.element.ICircleRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IConeRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IFilterRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.campaign.battle.map.element.IRayRemote;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;
import net.alteiar.server.shared.observer.IGUIDRemote;
import net.alteiar.server.shared.observer.campaign.map.IMapObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IMap2DRemote extends IGUIDRemote {

	void addMapListener(IMapObserverRemote map) throws RemoteException;

	void removeMapListener(IMapObserverRemote map) throws RemoteException;

	Scale getScale() throws RemoteException;

	void setScale(Scale scale) throws RemoteException;

	SerializableFile getBackground() throws RemoteException;

	IFilterRemote getFilter() throws RemoteException;

	void showPolygon(List<Point> cwPts) throws RemoteException;

	void hidePolygon(List<Point> cwPts) throws RemoteException;

	IMapElementObservableRemote[] getAllElements() throws RemoteException;

	void addMapElement(IMapElementObservableRemote element)
			throws RemoteException;

	void removedMapElement(IMapElementObservableRemote element)
			throws RemoteException;

	ICircleRemote createCircle(Point position, Color color,
			MapElementSize radius) throws RemoteException;

	IConeRemote createCone(Point position, Color color, MapElementSize heigth)
			throws RemoteException;

	IRayRemote createRay(Point position, Color color, MapElementSize width,
			MapElementSize height) throws RemoteException;
}
