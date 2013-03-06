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

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.server.document.map.element.colored.MapElementColoredClient;
import net.alteiar.server.document.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CircleClient extends
		MapElementColoredClient<MapElementCircleRemote> {
	private static final long serialVersionUID = 1L;

	protected MapElementSize localRadius;

	/**
	 * @param element
	 * @throws RemoteException
	 */
	public CircleClient(MapElementCircleRemote element) throws RemoteException {
		super(element);
		localRadius = element.getRadius();
	}

	public Double getRadius() {
		return localRadius.getPixels(getScale());
	}

	@Override
	public Double getWidth() {
		return getRadius() * 2;
	}

	@Override
	public Double getHeight() {
		return getRadius() * 2;
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Shape shape = new Ellipse2D.Double(getX() * zoomFactor, getY()
				* zoomFactor, getRadius() * zoomFactor - STROKE_SIZE_LARGE,
				getRadius() * zoomFactor - STROKE_SIZE_LARGE);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		Double strokeSizeMiddle = (double) strokeSize / 2;
		Shape shape = new Ellipse2D.Double(getX() * zoomFactor
				- strokeSizeMiddle, getY() * zoomFactor - strokeSizeMiddle,
				getRadius() * zoomFactor - STROKE_SIZE_LARGE, getRadius()
						* zoomFactor - STROKE_SIZE_LARGE);
		return shape;
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}
}
