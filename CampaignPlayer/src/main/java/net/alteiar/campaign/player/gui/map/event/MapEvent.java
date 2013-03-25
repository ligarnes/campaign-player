package net.alteiar.campaign.player.gui.map.event;

import java.awt.Point;
import java.awt.event.MouseEvent;

import net.alteiar.map.elements.MapElement;

public class MapEvent {
	private final MouseEvent event;
	private final MapElement mapElement;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, MapElement mapElement, Point mapPosition) {
		super();
		this.event = e;
		this.mapElement = mapElement;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public MapElement getMapElement() {
		return mapElement;
	}

	public Point getMapPosition() {
		return mapPosition;
	}
}
