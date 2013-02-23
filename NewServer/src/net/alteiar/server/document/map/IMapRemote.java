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

import net.alteiar.server.document.IDocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IMapRemote extends IDocumentRemote {
	String getName() throws RemoteException;

	void addMapListener(IMapListenerRemote map) throws RemoteException;

	void removeMapListener(IMapListenerRemote map) throws RemoteException;

	Scale getScale() throws RemoteException;

	void setScale(Scale scale) throws RemoteException;

	Long getBackground() throws RemoteException;

	Integer getWidth() throws RemoteException;

	Integer getHeight() throws RemoteException;

	Long getFilter() throws RemoteException;

	/*
	void showPolygon(List<Point> cwPts) throws RemoteException;

	void hidePolygon(List<Point> cwPts) throws RemoteException;
	*/

	/*
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
			
	*/
}
