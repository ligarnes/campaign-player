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

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface IMapElement {
	/**
	 * apply position on server
	 */
	void applyPosition();

	/**
	 * get position of the upper left corner of the shapeImage
	 */
	Point getPosition();

	/**
	 * get position of the center of the shapeImage
	 */
	Point getCenterPosition();

	/**
	 * the offset of the center from the position
	 * 
	 * @return
	 */
	Point getCenterOffset();

	/**
	 * set local position of shapeImage, need to call apply position to change
	 * on server
	 * 
	 * @param position
	 */
	void setPosition(Point position);

	void revertPosition();

	Double getAngle();

	void applyRotate();

	void setAngle(Double angle);

	Integer getWidth();

	Integer getHeight();

	BufferedImage getShape(double zoomFactor);

	Boolean isInside(Point point);
}
