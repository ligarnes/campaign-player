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
package net.alteiar.campaign.player.gui.battle.plan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.battle.plan.details.PanelMapWithListener;
import net.alteiar.campaign.player.gui.battle.plan.listener.GlobalMapListener;
import net.alteiar.campaign.player.gui.battle.tools.PanelBattleCharacterList;
import net.alteiar.campaign.player.gui.battle.tools.PanelTools;
import net.alteiar.campaign.player.gui.tools.PanelMoveZoom;
import net.alteiar.campaign.player.gui.tools.test.PanelTool;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.character.DocumentCharacterCombatBuilder;
import net.alteiar.server.document.map.element.colored.circle.DocumentCircleBuilder;
import net.alteiar.server.document.map.element.colored.rectangle.DocumentRectangleBuilder;
import net.alteiar.server.document.map.element.size.MapElementSize;

/**
 * @author Cody Stoutenburg
 */
public class PanelGeneraBattle extends JPanel implements MapEditableInfo,
		Observer {
	private static final long serialVersionUID = 5502995543807006460L;

	private final BattleClient battle;

	private final PanelBattleCharacterList toolbarCharacterList;

	private final PanelMapWithListener mapPanel;
	private final PanelMoveZoom<PanelMapWithListener> movePanel;

	private Boolean fixGrid;
	private Boolean showDistance;

	public PanelGeneraBattle(final BattleClient battle) {
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

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(new PanelTool(this, battle));
		this.add(panelCenter1);
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
	public void setVisibleText(String text) {
		this.mapPanel.setShowText(text);
	}

	@Override
	public void addCircle(Point position, MapElementSize radius, Color c) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		CampaignClient.getInstance().createMapElement(battle,
				new DocumentCircleBuilder(battle, position, c, radius));
	}

	@Override
	public void addCone(Point position, MapElementSize width, Color c) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		// TODO
		CampaignClient.getInstance().createMapElement(battle,
				new DocumentCircleBuilder(battle, position, c, width));
	}

	@Override
	public void addRay(Point position, MapElementSize width,
			MapElementSize height, Color c) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		CampaignClient.getInstance()
				.createMapElement(
						battle,
						new DocumentRectangleBuilder(battle, position, c,
								width, height));
	}

	@Override
	public void addCharacterAt(CharacterClient character, Integer init,
			Point position) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		CampaignClient.getInstance()
				.createMapElement(
						battle,
						new DocumentCharacterCombatBuilder(battle, position,
								character));
	}

	@Override
	public void addMonsterAt(CharacterClient character, Integer init,
			Boolean isVisible, Point position) {
		if (fixGrid) {
			modifyPositionToFixGrid(position);
		}
		// TODO battle.addMonster(character, init, isVisible, position);
	}

	@Override
	public MapElementClient<?> getElementAt(Point position) {
		return mapPanel.getElementAt(position);
	}

	/*
	 * @Override public void removeCharacter(CharacterClient character) {
	 * this.battle.removeCharacter(character); }
	 */

	/*
	 * @Override public void removeElement(CharacterClient toRemove) {
	 * battle.removeMapElement(toRemove); }
	 */

	@Override
	public void changeScale(Scale echelle) {
		battle.setScale(echelle);
	}

	@Override
	public void drawPathToElement(Point first, MapElementClient<?> mapElement) {
		this.mapPanel.drawPathToElement(first, mapElement);
	}

	@Override
	public void addPointToPath(Point next) {
		this.mapPanel.addPointToPath(next);
	}

	@Override
	public void stopDrawPathToMouse() {
		this.mapPanel.stopDrawPathToMouse();
	}

	@Override
	public void setLineColor(Color lineColor) {
		this.mapPanel.setLineColor(lineColor);
	}

	@Override
	public void drawLineToMouse(Point first) {
		this.mapPanel.drawLineToMouse(first);
	}

	@Override
	public void addPointToLine(Point next) {
		// if (fixGrid) {
		// modifyPositionToFixGrid(next);
		// }
		this.mapPanel.addPointToLine(next);
	}

	@Override
	public void addPointToLine(MapElementClient<?> currentElement, Point next) {
		if (fixGrid) {
			// modifyPositionToFixGrid(next);
			// Point center = currentElement.getCenterOffset();
			// next.x += center.x;
			// next.y += center.y;
		}

		this.mapPanel.addPointToLine(next);
	}

	@Override
	public void stopDrawLineToMouse() {
		this.mapPanel.stopDrawLineToMouse();
	}

	@Override
	public void drawPolygonToMouse(Point first) {
		this.mapPanel.drawPolygonToMouse(first, Color.RED);
	}

	@Override
	public void stopDrawPolygon() {
		this.mapPanel.stopDrawPolygonToMouse();
	}

	@Override
	public void drawRectangleToMouse(Point origin) {
		this.mapPanel.drawRectangleToMouse(origin, Color.RED);
	}

	@Override
	public void stopDrawRectangle() {
		this.mapPanel.stopDrawRectangleToMouse();
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
	public Point getPositionOf(MapElementClient<?> currentElement) {
		Point position = currentElement.getPosition();
		// Point center = currentElement.getCenterOffset();
		// position.x += center.x;
		// position.y += center.y;
		return position;
	}

	@Override
	public void moveElementAt(MapElementClient<?> currentElement, Point position) {
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
	public void rescaleMap(Point begin, Point end) {
		Double distX = Math.sqrt(Math.pow(begin.getX() - end.x, 2));
		Double distY = Math.sqrt(Math.pow(begin.getY() - end.y, 2));
		Double distance = Math.max(distX, distY);

		int nbWidth = (int) (this.battle.getWidth() / distance);
		int dist = this.battle.getWidth() / nbWidth;
		changeScale(new Scale(dist, 1.5));
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

	@Override
	public void showRectangle(Point position, Integer width, Integer height) {
		this.battle.showRectangle(position.x, position.y, width, height);
	}

	@Override
	public void showPolygon(List<Point> cwPts) {
		Point[] pts = new Point[cwPts.size()];
		cwPts.toArray(pts);
		this.battle.showPolygon(pts);
	}

	@Override
	public void hidePolygon(List<Point> cwPts) {
		Point[] pts = new Point[cwPts.size()];
		cwPts.toArray(pts);
		this.battle.hidePolygon(pts);
	}

	@Override
	public void hideRectangle(Point position, Integer width, Integer height) {
		this.battle.hideRectangle(position.x, position.y, width, height);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (showDistance) {
			List<Point> lstOrigin = this.mapPanel.getTrajet();
			if (lstOrigin.size() > 0) {
				double distance = 0;
				for (int i = 0; i < lstOrigin.size() - 1; ++i) {
					Point first = lstOrigin.get(i);
					Point next = lstOrigin.get(i + 1);

					distance += first.distance(next);
				}
				Point last = this.mapPanel
						.convertPointPanelToStandard(this.mapPanel
								.getMousePosition());
				distance += lstOrigin.get(lstOrigin.size() - 1).distance(last);

				double distCase = distance / this.battle.getScale().getPixels();

				NumberFormat nf = new DecimalFormat("0.0");
				this.mapPanel.setShowText(nf.format(distCase) + " cases");
			}
		} else {
			this.mapPanel.setShowText(null);
		}

		this.mapPanel.repaint();
		this.mapPanel.revalidate();
	}

	@Override
	public void removeElement(MapElementClient<?> toRemove) {
		// TODO Auto-generated method stub

	}
}
