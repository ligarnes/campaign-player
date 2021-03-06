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

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse.MouseDrawable;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.GlobalMapListener;
import net.alteiar.campaign.player.gui.centerViews.map.listener.mapElement.MapElementListener;
import net.alteiar.campaign.player.gui.centerViews.map.tools.PanelToolsAdventure;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.factory.MapElementFactory;
import net.alteiar.zoom.PanelMoveZoom;
import net.alteiar.zoom.Zoomable;

/**
 * @author Cody Stoutenburg
 */
public class PanelGlobalMap extends JPanel implements MapEditableInfo, Zoomable {
	private static final long serialVersionUID = 5502995543807006460L;

	private final MapBean map;

	private final PanelMapWithListener mapPanel;
	private final PanelMoveZoom<PanelMapWithListener> movePanel;

	private Boolean fixGrid;

	private final GlobalMapListener mapListener;

	public PanelGlobalMap(final MapBean map) {
		super();
		this.map = map;

		fixGrid = true;

		DrawFilter drawInfo = PluginSystem.getInstance().getDrawInfo(this);
		mapPanel = new PanelMapWithListener(map, drawInfo);
		mapPanel.addDrawableElementListener(new MapElementListener(this));

		movePanel = new PanelMoveZoom<PanelMapWithListener>(mapPanel);

		mapListener = new GlobalMapListener(this);
		mapPanel.addMapListener(mapListener);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(movePanel, BorderLayout.CENTER);

		JPanel panelCenter1 = new JPanel(new BorderLayout());
		panelCenter1.add(panelCenter, BorderLayout.CENTER);
		panelCenter1.add(new PanelToolsAdventure(this, movePanel),
				BorderLayout.NORTH);

		this.setLayout(new BorderLayout());

		this.add(panelCenter1, BorderLayout.CENTER);
	}

	@Override
	public PanelMoveZoom<PanelMapWithListener> getMoveZoomPanel() {
		return movePanel;
	}

	@Override
	public GlobalMapListener getMapListener() {
		return mapListener;
	}

	@Override
	public Point getViewPosition() {
		return movePanel.getViewPosition();
	}

	@Override
	public Point2D.Double convertPointStandardToPanel(Point2D position) {
		return this.mapPanel.convertPointStandardToPanel(position);
	}

	@Override
	public Point convertPointPanelToStandard(Point click) {
		return this.mapPanel.convertPointPanelToStandard(click);
	}

	@Override
	public PanelMapWithListener getPanelMap() {
		return mapPanel;
	}

	@Override
	public void addDrawable(MouseDrawable draw) {
		mapPanel.addDrawable(draw);
	}

	@Override
	public void removeDrawable(MouseDrawable draw) {
		mapPanel.removeDrawable(draw);
	}

	@Override
	public Boolean getFixGrid() {
		return this.fixGrid;
	}

	@Override
	public void fixGrid(Boolean fixGrid) {
		this.fixGrid = fixGrid;
	}

	@Override
	public void showGrid(Boolean showGrid) {
		this.mapPanel.showGrid(showGrid);
	}

	@Override
	public Boolean getShowGrid() {
		return this.mapPanel.getShowGrid();
	}

	@Override
	public MapBean getMap() {
		return map;
	}

	@Override
	public Integer getSquareDistance(double distancePixel) {
		Double squareDistance = Math.ceil(distancePixel
				/ (map.getScale().getPixels() * mapPanel.getZoomFactor()));
		return squareDistance.intValue();
	}

	@Override
	public void moveElementAt(MapElement currentElement, Point position) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		currentElement.moveTo(position);
	}

	private void modifyPositionToFixGrid(Point position) {
		Integer squareSize = this.map.getScale().getPixels();
		position.x = squareSize
				* (int) Math.floor(position.x / squareSize.floatValue());
		position.y = squareSize
				* (int) Math.floor(position.y / squareSize.floatValue());
	}

	@Override
	public void move(int moveX, int moveY) {
		movePanel.moveTo(moveX, moveY);
	}

	@Override
	public void zoom(Point center, int zoomFactor) {
		movePanel.zoom(center, zoomFactor);
	}

	@Override
	public void removeElement(MapElement toRemove) {
		MapElementFactory.removeMapElement(toRemove, map);
	}

	@Override
	public void zoom(double value) {
		movePanel.zoom(value);
	}

	@Override
	public Double getZoomFactor() {
		return movePanel.getZoomFactor();
	}

	@Override
	public void viewAsPlayer() {
		mapPanel.setViewAsDm(false);
	}

	@Override
	public void viewAsMj() {
		mapPanel.setViewAsDm(true);
	}
}
