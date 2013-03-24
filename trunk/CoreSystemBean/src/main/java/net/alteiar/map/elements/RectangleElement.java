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
package net.alteiar.map.elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyVetoException;
import java.rmi.RemoteException;

import net.alteiar.utils.map.element.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class RectangleElement extends ColoredShape {
	private static final long serialVersionUID = 1L;

	public static final String PROP_WIDTH_PROPERTY = "width";
	public static final String PROP_HEIGHT_PROPERTY = "height";

	protected MapElementSize width;
	protected MapElementSize height;

	/**
	 * @param element
	 * @throws RemoteException
	 */
	public RectangleElement(Point position, Color color, MapElementSize width,
			MapElementSize height) {
		super(position, color);
		this.width = width;
		this.height = height;
	}

	@Override
	public Double getWidthPixels() {
		return width.getPixels(getScale());
	}

	@Override
	public Double getHeightPixels() {
		return height.getPixels(getScale());
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Point p = getPosition();
		return new Rectangle2D.Double(p.getX() * zoomFactor, p.getY()
				* zoomFactor, getWidthPixels() * zoomFactor, getHeightPixels()
				* zoomFactor);
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		Point p = getPosition();
		Double strokeSizeMiddle = (double) strokeSize / 2;

		return new Rectangle2D.Double(p.getX() * zoomFactor + strokeSizeMiddle,
				p.getY() * zoomFactor + strokeSizeMiddle, getWidthPixels()
						* zoomFactor - strokeSize, getHeightPixels()
						* zoomFactor - strokeSize);
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public MapElementSize getWidth() {
		return width;
	}

	public void setWidth(MapElementSize width) {
		MapElementSize oldValue = this.width;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
			this.width = width;
			propertyChangeSupport.firePropertyChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public MapElementSize getHeight() {
		return height;
	}

	public void setHeight(MapElementSize height) {
		MapElementSize oldValue = this.height;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_HEIGHT_PROPERTY, oldValue, height);
			this.height = height;
			propertyChangeSupport.firePropertyChange(PROP_HEIGHT_PROPERTY,
					oldValue, height);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}
}
