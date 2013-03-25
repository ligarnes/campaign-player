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

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import net.alteiar.map.elements.MapElement;
import net.alteiar.utils.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface MapEditableInfo {

	Scale getScale();

	Integer getSquareDistance(double distancePixel);

	double getZoom();

	void zoom(int zoomFactor);

	void zoom(Point center, int zoomFactor);

	void move(int moveX, int moveY);

	void setVisibleText(String text);

	Point getPositionOf(MapElement currentElement);

	/**
	 * 
	 * @param position
	 *            - the position where the element should be
	 * @return the element at the position or null if nothing is here
	 */
	MapElement getElementAt(Point position);

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

	void setLineColor(Color lineColor);

	void drawPathToElement(Point first, MapElement mapElement);

	void addPointToPath(Point next);

	void stopDrawPathToMouse();

	void drawLineToMouse(Point first);

	void drawPolygonToMouse(Point first);

	void stopDrawPolygon();

	void addPointToLine(Point next);

	void addPointToLine(MapElement currentElement, Point next);

	void stopDrawLineToMouse();

	void drawRectangleToMouse(Point origin);

	void stopDrawRectangle();

	void showPolygon(List<Point> cwPts);

	void hidePolygon(List<Point> cwPts);

	void showRectangle(Point position, Integer width, Integer height);

	void hideRectangle(Point position, Integer width, Integer height);
}
