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
package net.alteiar.client.shared.observer.campaign.map.element;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.map.element.IMapElementClient;
import net.alteiar.client.shared.observer.ProxyClientObservableSimple;
import net.alteiar.server.shared.campaign.battle.map.element.IMapElementObservableRemote;
import net.alteiar.server.shared.observer.campaign.map.IMapElementObserverRemote;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class BaseMapElementClient extends
		ProxyClientObservableSimple<IMapElementObservableRemote> implements
		IMapElementClient {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	// this are remote information
	protected Point localPosition;
	protected Boolean isHidden;
	protected Double localAngle;

	public BaseMapElementClient(IMapElementObservableRemote element) {
		super(element);
		try {
			// load all data
			localPosition = remoteObject.getPosition();
			localAngle = remoteObject.getAngle();
			new MapElementRemoteObserver(remoteObject);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public void revertPosition() {
		refreshPosition();
	}

	@Override
	public Point getPosition() {
		return (Point) localPosition.clone();
	}

	@Override
	public Point getCenterPosition() {
		Point position = getPosition();
		position.x += (getWidth() / 2);
		position.y += (getWidth() / 2);
		return position;
	}

	/**
	 * the default rotation offset is the center of the shape
	 * 
	 * @return
	 */
	@Override
	public Point getCenterOffset() {
		return new Point(getWidth() / 2, getHeight() / 2);
	}

	@Override
	public void setPosition(Point position) {
		localPosition = position;
		notifyListeners();
	}

	@Override
	public void applyPosition() {
		try {
			remoteObject.setPosition(localPosition);
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	@Override
	public Double getAngle() {
		return this.localAngle;
	}

	@Override
	public void applyRotate() {
		try {
			this.remoteObject.setAngle(localAngle);
		} catch (RemoteException ex) {
			ExceptionTool.showError(ex);
		}
	}

	@Override
	public void setAngle(Double angle) {
		this.localAngle = angle;
		notifyListeners();
	}

	@Override
	public abstract BufferedImage getShape(double zoomFactor);

	@Override
	public Boolean isInside(Point location) {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(localPosition.x, localPosition.y);
		Point p = getCenterOffset();
		affineTransform.rotate(Math.toRadians(getAngle()), p.x, p.y);

		Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		Shape s = affineTransform.createTransformedShape(rect);

		return s.contains(location);
	}

	private void refreshPosition() {
		try {
			localPosition = remoteObject.getPosition();
			notifyListeners();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}
	}

	private void setHidden(Boolean isHidden) {
		this.isHidden = isHidden;
		notifyListeners();
	}

	private void setRotate(Double rotate) {
		this.localAngle = rotate;
		notifyListeners();
	}

	/**
	 * this class should be observer and will use the notify of the Map2DClient
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	private class MapElementRemoteObserver extends UnicastRemoteObject
			implements IMapElementObserverRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected MapElementRemoteObserver(IMapElementObservableRemote element)
				throws RemoteException {
			super();
			element.addMapElementListener(this);
		}

		@Override
		public void elementMoved(Point newPosition) throws RemoteException {
			refreshPosition();
		}

		@Override
		public void elementHidden(Boolean isHidden) throws RemoteException {
			setHidden(isHidden);
		}

		@Override
		public void elementRotate(Double angle) throws RemoteException {
			setRotate(angle);
		}
	}
}
