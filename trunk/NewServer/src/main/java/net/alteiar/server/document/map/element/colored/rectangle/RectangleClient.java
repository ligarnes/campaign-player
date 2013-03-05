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

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
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
public class RectangleClient extends MapElementColoredClient<RectangleRemote> {
	private static final long serialVersionUID = 1L;

	protected MapElementSize localWidth;
	protected MapElementSize localHeight;

	/**
	 * @param element
	 * @throws RemoteException
	 */
	public RectangleClient(RectangleRemote element) throws RemoteException {
		super(element);
		localWidth = element.getWidth();
		localHeight = element.getHeight();
	}

	public MapElementSize getWidth() {
		return this.localWidth;
	}

	public MapElementSize getHeight() {
		return this.localHeight;
	}

	@Override
	protected double getWidth(Scale scale, double zoomFactor) {
		return localWidth.getPixels(scale) * zoomFactor;
	}

	@Override
	protected double getHeight(Scale scale, double zoomFactor) {
		return localHeight.getPixels(scale) * zoomFactor;
	}

	@Override
	protected Shape getShape(Scale scale, double zoomFactor) {
		double x = getX(scale, zoomFactor);
		double y = getY(scale, zoomFactor);
		double widthInPixels = getWidth(scale, zoomFactor);
		double heightInPixels = getHeight(scale, zoomFactor);

		return new Rectangle2D.Double(x, y, widthInPixels, heightInPixels);
	}

	@Override
	protected Shape getShapeBorder(Scale scale, double zoomFactor,
			int strokeSize) {
		double x = getX(scale, zoomFactor);
		double y = getY(scale, zoomFactor);
		double width = getWidth(scale, zoomFactor);
		double height = getHeight(scale, zoomFactor);

		Double strokeSizeMiddle = (double) strokeSize / 2;

		return new Rectangle2D.Double(x + strokeSizeMiddle, y
				+ strokeSizeMiddle, width - 2 * strokeSizeMiddle, height - 2
				* strokeSizeMiddle);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}
}
