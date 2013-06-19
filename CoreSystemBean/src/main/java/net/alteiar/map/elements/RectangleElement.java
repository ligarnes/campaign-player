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
import java.rmi.RemoteException;

import net.alteiar.map.size.MapElementSize;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 */
public class RectangleElement extends ColoredShape {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_WIDTH_PROPERTY = "width";
	public static final String PROP_HEIGHT_PROPERTY = "height";

	@Element
	protected MapElementSize width;
	@Element
	protected MapElementSize height;

	public RectangleElement() {
	}

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
		if (notifyRemote(PROP_WIDTH_PROPERTY, oldValue, width)) {
			this.width = width;
			notifyLocal(PROP_WIDTH_PROPERTY, oldValue, width);
		}
	}

	public MapElementSize getHeight() {
		return height;
	}

	public void setHeight(MapElementSize height) {
		MapElementSize oldValue = this.height;
		if (notifyRemote(PROP_HEIGHT_PROPERTY, oldValue, height)) {
			this.height = height;
			notifyLocal(PROP_HEIGHT_PROPERTY, oldValue, height);
		}
	}

	@Override
	public String getNameFormat() {
		if (getWidth().getShortUnitFormat().equals(
				getHeight().getShortUnitFormat())) {
			return "Rectangle " + getWidth().getValue() + "x"
					+ getHeight().getValue() + " "
					+ getWidth().getShortUnitFormat();
		}
		return "Rectangle " + getWidth().getValue()
				+ getWidth().getShortUnitFormat() + "x"
				+ getHeight().getValue() + " "
				+ getHeight().getShortUnitFormat();
	}
}
