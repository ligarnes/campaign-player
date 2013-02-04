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

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementRayRemote extends MapElementColoredRemote implements
		IRayRemote {

	private static final long serialVersionUID = 5873227946676717743L;

	private final MapElementSize width;
	private final MapElementSize height;

	public MapElementRayRemote(Point position, Color color,
			MapElementSize width, MapElementSize height) throws RemoteException {
		super(position, color);

		this.width = width;
		this.height = height;
	}

	@Override
	public MapElementSize getWidth() throws RemoteException {
		return width;
	}

	@Override
	public MapElementSize getHeight() throws RemoteException {
		return height;
	}
}
