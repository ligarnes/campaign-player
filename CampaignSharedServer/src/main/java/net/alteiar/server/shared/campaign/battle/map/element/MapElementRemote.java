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
import java.rmi.RemoteException;

import net.alteiar.server.shared.observer.campaign.map.MapElementObservableRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class MapElementRemote extends MapElementObservableRemote
		implements IMapElementObservableRemote {
	private static final long serialVersionUID = -3573905342323034939L;

	// the position is the position of the upper left corner
	private Point position;
	private Double angle;

	/**
	 * @param position
	 * @param color
	 * @throws RemoteException
	 */
	public MapElementRemote(Point position) throws RemoteException {
		this.position = position;
		this.angle = 0.0;
	}

	@Override
	public Point getPosition() throws RemoteException {
		return position;
	}

	@Override
	public void setPosition(Point position) throws RemoteException {
		if (!this.position.equals(position)) {
			this.position = position;
			this.notifyElementMoved(position);
		}
	}

	@Override
	public Double getAngle() throws RemoteException {
		return this.angle;
	}

	@Override
	public void setAngle(Double angle) throws RemoteException {
		if (!this.angle.equals(angle)) {
			this.angle = angle;
			this.notifyElementRotate(angle);
		}
	}
}
