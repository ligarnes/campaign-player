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
package net.alteiar.client.shared.campaign.map.element;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import net.alteiar.ExceptionTool;
import net.alteiar.client.shared.campaign.map.IMap2DClient;
import net.alteiar.client.shared.observer.campaign.map.element.BaseMapElementClient;
import net.alteiar.server.shared.campaign.battle.map.element.IConeRemote;
import net.alteiar.server.shared.campaign.battle.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 * 
 */
public class ConeClient extends BaseMapElementClient {
	private static final long serialVersionUID = 1L;

	private final IMap2DClient<?> map;
	protected MapElementSize localHeight;
	protected Color localColor;

	/**
	 * @param element
	 */
	public ConeClient(IConeRemote element, IMap2DClient<?> client) {
		super(element);

		// all remote element specific to cone
		try {
			localColor = element.getColor();
			localHeight = element.getHeight();
		} catch (RemoteException e) {
			ExceptionTool.showError(e);
		}

		map = client;
	}

	public Color getColor() {
		return localColor;
	}

	public MapElementSize getLocalHeight() {
		return localHeight;
	}

	@Override
	public Integer getWidth() {
		return localHeight.getPixels(map.getScale()).intValue();
	}

	@Override
	public Integer getHeight() {
		return localHeight.getPixels(map.getScale()).intValue();
	}

	@Override
	public BufferedImage getShape(double zoomFactor) {
		Double radiusInPixels = localHeight.getPixels(map.getScale())
				* zoomFactor;
		return generateCone(radiusInPixels.intValue(), localColor, getAngle());
	}

	private final BufferedImage generateCone(Integer height, Color c,
			Double angle) {
		Polygon shape = new Polygon();
		shape.addPoint(0, 0);
		for (int x = 0; x <= height; ++x) {
			int y = (int) Math.sqrt(Math.pow(height, 2) - Math.pow(x, 2));
			shape.addPoint(x, y);
		}

		BufferedImage img = new BufferedImage(height, height,
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
		if (height < 100) {
			strokeSize = STROKE_SIZE_SMALL;
		}
		g2.setStroke(new BasicStroke(strokeSize));
		g2.draw(shape);

		g2.dispose();
		return img;
	}

	@Override
	public Point getCenterOffset() {
		return new Point(0, 0);
	}
}
