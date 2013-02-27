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
package net.alteiar.server.document.map.element;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.ServerDocuments;
import net.alteiar.server.document.DocumentRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class MapElementRemote extends DocumentRemote implements
		IMapElementRemote {
	private static final long serialVersionUID = -3573905342323034939L;

	// the position is the position of the upper left corner
	private Point position;
	private Double angle;

	private Boolean isHidden;

	/**
	 * @param position
	 * @param color
	 * @throws RemoteException
	 */
	public MapElementRemote(Point position) throws RemoteException {
		this.position = position;
		this.angle = 0.0;
		isHidden = false;
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

	@Override
	public Boolean getIsHidden() throws RemoteException {
		return isHidden;
	}

	@Override
	public void setIsHidden(Boolean isHidden) throws RemoteException {
		this.isHidden = isHidden;
	}

	// //////// LISTENERS METHODS //////////
	@Override
	public void addMapElementListener(IMapElementListenerRemote listener)
			throws RemoteException {
		this.addListener(IMapElementListenerRemote.class, listener);
	}

	@Override
	public void removeMapElementListener(IMapElementListenerRemote listener)
			throws RemoteException {
		this.removeListener(IMapElementListenerRemote.class, listener);
	}

	protected void notifyElementMoved(Point newPosition) {
		for (IMapElementListenerRemote observer : this
				.getListener(IMapElementListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL.addTask(new MapElementMovedTask(
					this, observer, newPosition));
		}
	}

	protected void notifyElementHidden(Boolean isHidden) {
		for (IMapElementListenerRemote observer : this
				.getListener(IMapElementListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new MapElementHiddenTask(this, observer, isHidden));
		}
	}

	protected void notifyElementRotate(Double angle) {
		for (IMapElementListenerRemote observer : this
				.getListener(IMapElementListenerRemote.class)) {
			ServerDocuments.SERVER_THREAD_POOL
					.addTask(new MapElementRotateTask(this, observer, angle));
		}
	}

	private class MapElementMovedTask extends
			BaseNotify<IMapElementListenerRemote> {
		private final Point newPoint;

		public MapElementMovedTask(MapElementRemote observable,
				IMapElementListenerRemote observer, Point remote) {
			super(observable, IMapElementListenerRemote.class, observer);
			this.newPoint = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.elementMoved(newPoint);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}

	private class MapElementHiddenTask extends
			BaseNotify<IMapElementListenerRemote> {
		private final Boolean isHidden;

		public MapElementHiddenTask(MapElementRemote observable,
				IMapElementListenerRemote observer, Boolean remote) {
			super(observable, IMapElementListenerRemote.class, observer);
			this.isHidden = remote;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.elementHidden(isHidden);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}

	private class MapElementRotateTask extends
			BaseNotify<IMapElementListenerRemote> {
		private final Double angle;

		public MapElementRotateTask(MapElementRemote observable,
				IMapElementListenerRemote observer, Double angle) {
			super(observable, IMapElementListenerRemote.class, observer);
			this.angle = angle;
		}

		@Override
		protected void doAction() throws RemoteException {
			observer.elementRotate(angle);
		}

		@Override
		public String getStartText() {
			return "start notify map element moved)";
		}

		@Override
		public String getFinishText() {
			return "finish notify map element moved";
		}
	}
}
