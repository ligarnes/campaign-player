package net.alteiar.campaign.player.gui.centerViews.map.event;

import java.awt.Point;
import java.awt.event.MouseEvent;

import net.alteiar.documents.map.MapBean;

public class MapEvent {
	private final MouseEvent event;

	private final MapBean map;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, MapBean map, Point mapPosition) {
		this.event = e;
		this.map = map;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public MapBean getMap() {
		return map;
	}

	public Point getMapPosition() {
		return mapPosition;
	}

	public Point getFixToGridPosition() {
		Point pos = new Point(mapPosition);
		modifyPositionToFixGrid(pos);
		return pos;
	}

	private void modifyPositionToFixGrid(Point position) {
		Integer squareSize = this.map.getScale().getPixels();
		position.x = squareSize
				* (int) Math.floor(position.x / squareSize.floatValue());
		position.y = squareSize
				* (int) Math.floor(position.y / squareSize.floatValue());
	}
}
