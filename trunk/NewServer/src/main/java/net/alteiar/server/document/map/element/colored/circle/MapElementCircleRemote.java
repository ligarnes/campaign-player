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
package net.alteiar.server.document.map.element.colored.circle;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.map.element.colored.MapElementColoredRemote;
import net.alteiar.server.document.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementCircleRemote extends MapElementColoredRemote implements
		ICircleRemote {
	private static final long serialVersionUID = 940613673212818094L;

	private final MapElementSize radius;

	/**
	 * @param shape
	 * @param position
	 * @throws RemoteException
	 */
	public MapElementCircleRemote(Point position, Color color,
			MapElementSize size) throws RemoteException {
		super(position, color);
		radius = size;
	}

	@Override
	public MapElementSize getRadius() throws RemoteException {
		return radius;
	}

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new CircleClient(this);
	}
}
