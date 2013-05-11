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
package net.alteiar.campaign.player.gui.map.battle;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import net.alteiar.campaign.player.gui.map.PanelBasicMap;
import net.alteiar.campaign.player.gui.map.drawable.mouse.MouseDrawable;
import net.alteiar.map.elements.MapElement;
import net.alteiar.utils.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface MapEditableInfo {

	Point getViewPosition();

	Point convertPointPanelToStandard(Point click);

	Point2D.Double convertPointStandardToPanel(Point2D position);

	/**
	 * 
	 * @param position
	 *            - in the map (no zoom)
	 * @return
	 */
	Point convertPointToSquare(Point position);

	Point2D.Double convertSquareToPoint(Point position);

	PanelBasicMap getPanelMap();

	Scale getScale();

	Integer getSquareDistance(double distancePixel);

	double getZoom();

	void zoom(int zoomFactor);

	void zoom(Point center, int zoomFactor);

	void move(int moveX, int moveY);

	Point getPositionOf(MapElement currentElement);

	Boolean getFixGrid();

	void fixGrid(Boolean fixGrid);

	void showGrid(Boolean showGrid);

	Boolean getShowGrid();

	void showDistance(Boolean showDistance);

	Boolean getShowDistance();

	// TODO void removeCharacter(ICharacterCombatClient character);

	void removeElement(MapElement toRemove);

	void moveElementAt(MapElement currentElement, Point position);

	int getPixelSquare();

	void rescaleMap(int pixelSquare);

	void changeScale(Scale echelle);

	void addDrawable(MouseDrawable draw);

	void removeDrawable(MouseDrawable draw);

	void showPolygon(List<Point> cwPts);

	void hidePolygon(List<Point> cwPts);

	void showRectangle(Point position, Integer width, Integer height);

	void hideRectangle(Point position, Integer width, Integer height);
}
