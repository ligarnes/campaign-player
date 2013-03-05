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

import net.alteiar.server.document.map.Scale;
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

	public MapElementSize getRadius() {
		return this.localRadius;
	}

	@Override
	protected double getWidth(Scale scale, double zoomFactor) {
		return localRadius.getPixels(scale) * zoomFactor * 2;
	}

	@Override
	protected double getHeight(Scale scale, double zoomFactor) {
		return localRadius.getPixels(scale) * zoomFactor * 2;
	}

	@Override
	protected Shape getShape(Scale scale, double zoomFactor) {
		double x = getX(scale, zoomFactor);
		double y = getY(scale, zoomFactor);

		double radius = getWidth(scale, zoomFactor) / 2;

		Shape shape = new Ellipse2D.Double(x, y, radius - STROKE_SIZE_LARGE,
				radius - STROKE_SIZE_LARGE);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(Scale scale, double zoomFactor,
			int strokeSize) {
		double x = getX(scale, zoomFactor);
		double y = getY(scale, zoomFactor);

		double radius = getWidth(scale, zoomFactor) / 2;

		Double strokeSizeMiddle = (double) strokeSize / 2;
		Shape shape = new Ellipse2D.Double(x + strokeSizeMiddle, y
				+ strokeSizeMiddle, radius - STROKE_SIZE_LARGE, radius
				- STROKE_SIZE_LARGE);
		return shape;
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}
}
