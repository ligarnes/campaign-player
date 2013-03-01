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
package net.alteiar.server.document.map.element.colored;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class CircleClient extends
		MapElementColoredClient<MapElementCircleRemote> {
	private static final long serialVersionUID = 1L;

	private final Long map;
	protected MapElementSize localRadius;

	/**
	 * @param element
	 * @throws RemoteException
	 */
	public CircleClient(MapElementCircleRemote element, Long map)
			throws RemoteException {
		super(element);
		// all remote element specific to circle
		localRadius = element.getRadius();
		this.map = map;
	}

	protected MapClient<?> getMap() {
		return (MapClient<?>) CampaignClient.getInstance().getDocument(map);
	}

	public MapElementSize getRadius() {
		return this.localRadius;
	}

	public Integer getWidth() {
		return localRadius.getPixels(getMap().getScale()).intValue();
	}

	public Integer getHeight() {
		return localRadius.getPixels(getMap().getScale()).intValue();
	}

	public BufferedImage getShape(double zoomFactor) {
		Double radiusInPixels = localRadius.getPixels(getMap().getScale())
				* zoomFactor;
		return generateCircle(radiusInPixels.intValue(), getColor());
	}

	/**
	 * this methode generate a circle how we want it
	 * 
	 * @param radius
	 *            - radius of the circle
	 * @param c
	 *            - color of background
	 * @return
	 */
	private final BufferedImage generateCircle(Integer radius, Color c) {
		// because half of the stroke goes outside
		Double strokeSizeMiddle = (double) STROKE_SIZE_LARGE / 2;
		Shape shape = new Ellipse2D.Double(strokeSizeMiddle, strokeSizeMiddle,
				radius - STROKE_SIZE_LARGE, radius - STROKE_SIZE_LARGE);

		// create an image from graphics
		BufferedImage img = new BufferedImage(radius, radius,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// set the color in the circle
		g2.setColor(c);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));

		g2.fill(shape);

		// draw border
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.setColor(Color.BLACK);
		Integer strokeSize = STROKE_SIZE_LARGE;
		if (radius < 100) {
			strokeSize = STROKE_SIZE_SMALL;
		}
		g2.setStroke(new BasicStroke(strokeSize));
		g2.draw(shape);

		g2.dispose();
		return img;
	}

	@Override
	protected void closeDocument() throws RemoteException {

	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
	}
}
