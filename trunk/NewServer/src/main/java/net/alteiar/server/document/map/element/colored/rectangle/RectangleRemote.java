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
package net.alteiar.server.document.map.element.colored.rectangle;

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
public class RectangleRemote extends MapElementColoredRemote implements
		IRectangleRemote {
	private static final long serialVersionUID = 940613673212818094L;

	private final MapElementSize width;
	private final MapElementSize height;

	/**
	 * @param shape
	 * @param position
	 * @throws RemoteException
	 */
	public RectangleRemote(Long map, Point position, Color color,
			MapElementSize width, MapElementSize height) throws RemoteException {
		super(map, position, color);
		this.width = width;
		this.height = height;
	}

	@Override
	public MapElementSize getWidth() throws RemoteException {
		return this.width;
	}

	@Override
	public MapElementSize getHeight() throws RemoteException {
		return this.height;
	}

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new RectangleClient(this);
	}
}
