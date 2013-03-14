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
package test.pathfinder.mapElement.shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;

import net.alteiar.server.document.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class TestRectangle extends TestColoredShape {
	private static final long serialVersionUID = 1L;

	protected MapElementSize localWidth;
	protected MapElementSize localHeight;

	/**
	 * @param element
	 * @throws RemoteException
	 */
	public TestRectangle(Color color, MapElementSize width, MapElementSize height) {
		super(color);
		localWidth = width;
		localHeight = height;
	}

	@Override
	public Double getWidth() {
		return localWidth.getPixels(getScale());
	}

	@Override
	public Double getHeight() {
		return localHeight.getPixels(getScale());
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Point p = getPosition();
		return new Rectangle2D.Double(p.getX() * zoomFactor, p.getY()
				* zoomFactor, getWidth() * zoomFactor, getHeight() * zoomFactor);
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		Point p = getPosition();
		Double strokeSizeMiddle = (double) strokeSize / 2;

		return new Rectangle2D.Double(p.getX() * zoomFactor + strokeSizeMiddle,
				p.getY() * zoomFactor + strokeSizeMiddle, getWidth()
						* zoomFactor - strokeSize, getHeight() * zoomFactor
						- strokeSize);
	}
}
