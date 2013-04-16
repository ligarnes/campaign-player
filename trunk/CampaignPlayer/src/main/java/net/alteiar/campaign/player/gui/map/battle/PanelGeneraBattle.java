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

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.PanelWest;
import net.alteiar.campaign.player.gui.map.PanelBasicMap;
import net.alteiar.campaign.player.gui.map.PanelMapWithListener;
import net.alteiar.campaign.player.gui.map.battle.tools.PanelBattleCharacterList;
import net.alteiar.campaign.player.gui.map.battle.tools.PanelTools;
import net.alteiar.campaign.player.gui.map.drawable.Drawable;
import net.alteiar.campaign.player.gui.map.listener.GlobalMapListener;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.utils.map.Scale;
import net.alteiar.zoom.PanelMoveZoom;

/**
 * @author Cody Stoutenburg
 */
public class PanelGeneraBattle extends JPanel implements MapEditableInfo,
		Observer {
	private static final long serialVersionUID = 5502995543807006460L;

	private final Battle battle;

	private final PanelBattleCharacterList toolbarCharacterList;

	private final PanelMapWithListener mapPanel;
	private final PanelMoveZoom<PanelMapWithListener> movePanel;

	private Boolean fixGrid;
	private Boolean showDistance;

	public PanelGeneraBattle(final Battle battle) {
		this.battle = battle;

		fixGrid = true;
		showDistance = true;

		mapPanel = new PanelMapWithListener(battle);

		GlobalMapListener mapListener = new GlobalMapListener(battle, this);
		mapPanel.addMapListener(mapListener);

		movePanel = new PanelMoveZoom<PanelMapWithListener>(mapPanel);

		toolbarCharacterList = new PanelBattleCharacterList(this.battle);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(toolbarCharacterList, BorderLayout.NORTH);
		panelCenter.add(movePanel, BorderLayout.CENTER);

		JPanel panelCenter1 = new JPanel(new BorderLayout());
		panelCenter1.add(panelCenter, BorderLayout.CENTER);
		panelCenter1.add(new PanelTools(mapListener, this, this.battle),
				BorderLayout.NORTH);

		this.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(new PanelWest());
		this.add(scroll, BorderLayout.WEST);
		this.add(panelCenter1, BorderLayout.CENTER);
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
	public Point convertPointToSquare(Point position) {
		Integer squareSize = getScale().getPixels();
		Point square = new Point();
		square.x = (int) Math.floor(position.x / squareSize.floatValue());
		square.y = (int) Math.floor(position.y / squareSize.floatValue());

		return square;
	}

	@Override
	public Point2D.Double convertSquareToPoint(Point position) {
		double zoomFactor = getZoom();
		Double squareSize = getScale().getPixels() * zoomFactor;
		return new Point2D.Double(squareSize * position.x, squareSize
				* position.y);
	}

	@Override
	public PanelBasicMap getPanelMap() {
		return mapPanel;
	}

	@Override
	public void addDrawable(Drawable draw) {
		mapPanel.addDrawable(draw);
	}

	@Override
	public void removeDrawable(Drawable draw) {
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
	public void showDistance(Boolean showDistance) {
		this.showDistance = showDistance;
	}

	@Override
	public Boolean getShowDistance() {
		return this.showDistance;
	}

	@Override
	public MapElement getElementAt(Point position) {
		return mapPanel.getElementAt(position);
	}

	@Override
	public void changeScale(Scale echelle) {
		battle.setScale(echelle);
	}

	@Override
	public Scale getScale() {
		return battle.getScale();
	}

	@Override
	public Integer getSquareDistance(double distancePixel) {
		Double squareDistance = Math.ceil(distancePixel
				/ (battle.getScale().getPixels() * mapPanel.getZoomFactor()));
		return squareDistance.intValue();
	}

	@Override
	public Point getPositionOf(MapElement currentElement) {
		Point position = currentElement.getCenterPosition();
		// Point center = currentElement.getCenterOffset();
		// position.x += center.x;
		// position.y += center.y;
		return position;
	}

	@Override
	public void moveElementAt(MapElement currentElement, Point position) {
		if (!fixGrid) {
			// we move the character from the center
			// Point center = currentElement.getCenterOffset();
			// position.x -= center.x;
			// position.y -= center.y;
		}

		position.x = Math.max(0, position.x);
		position.y = Math.max(0, position.y);
		// position.x = Math.min(battle.getWidth() - currentElement.getWidth(),
		// position.x);
		// position.y = Math.min(battle.getHeight() -
		// currentElement.getHeight(),
		// position.y);

		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		currentElement.setPosition(position);
	}

	private void modifyPositionToFixGrid(Point position) {
		Integer squareSize = this.battle.getScale().getPixels();
		position.x = squareSize
				* (int) Math.floor(position.x / squareSize.floatValue());
		position.y = squareSize
				* (int) Math.floor(position.y / squareSize.floatValue());
	}

	@Override
	public int getPixelSquare() {
		return getScale().getPixels();
	}

	@Override
	public void rescaleMap(int pixelSquare) {
		changeScale(new Scale(pixelSquare, 1.5));
	}

	@Override
	public void move(int moveX, int moveY) {
		movePanel.moveTo(moveX, moveY);
	}

	@Override
	public double getZoom() {
		return this.mapPanel.getZoomFactor();
	}

	@Override
	public void zoom(int zoomFactor) {
		movePanel.zoom(zoomFactor);
	}

	@Override
	public void zoom(Point center, int zoomFactor) {
		movePanel.zoom(center, zoomFactor);
	}

	public MapFilter getMapFilter() {
		return CampaignClient.getInstance().getBean(this.battle.getFilter());
	}

	@Override
	public void showRectangle(Point position, Integer width, Integer height) {
		MapFilter filter = getMapFilter();

		int x1 = position.x;
		int x2 = position.x + width;
		int y1 = position.y;
		int y2 = position.y + height;

		filter.showPolygon(new Polygon(new int[] { x1, x2, x2, x1 }, new int[] {
				y1, y1, y2, y2 }, 4));
	}

	@Override
	public void showPolygon(List<Point> cwPts) {
		MapFilter filter = getMapFilter();
		int[] x = new int[cwPts.size()];
		int[] y = new int[cwPts.size()];

		for (int i = 0; i < cwPts.size(); i++) {
			x[i] = cwPts.get(i).x;
			y[i] = cwPts.get(i).y;
		}

		filter.showPolygon(new Polygon(x, y, cwPts.size()));
	}

	@Override
	public void hidePolygon(List<Point> cwPts) {
		MapFilter filter = getMapFilter();
		int[] x = new int[cwPts.size()];
		int[] y = new int[cwPts.size()];

		for (int i = 0; i < cwPts.size(); i++) {
			x[i] = cwPts.get(i).x;
			y[i] = cwPts.get(i).y;
		}

		filter.hidePolygon(new Polygon(x, y, cwPts.size()));
	}

	@Override
	public void hideRectangle(Point position, Integer width, Integer height) {
		MapFilter filter = getMapFilter();

		int x1 = position.x;
		int x2 = position.x + width;
		int y1 = position.y;
		int y2 = position.y + height;

		filter.hidePolygon(new Polygon(new int[] { x1, x2, x2, x1 }, new int[] {
				y1, y1, y2, y2 }, 4));
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * if (showDistance) { List<Point> lstOrigin =
		 * this.mapPanel.getTrajet(); if (lstOrigin.size() > 0) { double
		 * distance = 0; for (int i = 0; i < lstOrigin.size() - 1; ++i) { Point
		 * first = lstOrigin.get(i); Point next = lstOrigin.get(i + 1);
		 * 
		 * distance += first.distance(next); } Point last = this.mapPanel
		 * .convertPointPanelToStandard(this.mapPanel .getMousePosition());
		 * distance += lstOrigin.get(lstOrigin.size() - 1).distance(last);
		 * 
		 * double distCase = distance / this.battle.getScale().getPixels();
		 * 
		 * NumberFormat nf = new DecimalFormat("0.0");
		 * this.mapPanel.setShowText(nf.format(distCase) + " cases"); } } else {
		 * this.mapPanel.setShowText(null); }
		 * 
		 * this.mapPanel.repaint(); this.mapPanel.revalidate();
		 */
	}

	@Override
	public void removeElement(MapElement toRemove) {
		// TODO Auto-generated method stub

	}
}