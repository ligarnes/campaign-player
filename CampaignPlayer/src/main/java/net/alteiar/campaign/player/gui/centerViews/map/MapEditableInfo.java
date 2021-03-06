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
package net.alteiar.campaign.player.gui.centerViews.map;

import java.awt.Point;
import java.awt.geom.Point2D;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.MouseDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.GlobalMapListener;
import net.alteiar.zoom.PanelMoveZoom;
import net.alteiar.zoom.Zoomable;

/**
 * @author Cody Stoutenburg
 * 
 */
public interface MapEditableInfo extends Zoomable {

	PanelMoveZoom<PanelMapWithListener> getMoveZoomPanel();

	MapBean getMap();

	GlobalMapListener getMapListener();

	Point getViewPosition();

	Point convertPointPanelToStandard(Point click);

	Point2D.Double convertPointStandardToPanel(Point2D position);

	PanelMapWithListener getPanelMap();

	Integer getSquareDistance(double distancePixel);

	void zoom(Point center, int zoomFactor);

	void move(int moveX, int moveY);

	Boolean getFixGrid();

	void fixGrid(Boolean fixGrid);

	void showGrid(Boolean showGrid);

	Boolean getShowGrid();

	void removeElement(MapElement toRemove);

	void moveElementAt(MapElement currentElement, Point position);

	void addDrawable(MouseDrawable draw);

	void removeDrawable(MouseDrawable draw);

	void viewAsPlayer();

	void viewAsMj();
}
