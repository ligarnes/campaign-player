package net.alteiar.campaign.player.gui.map.event;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import net.alteiar.map.elements.MapElement;

public class MapEvent {
	private final MouseEvent event;
	private final List<MapElement> elements;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, Point mapPosition, List<MapElement> mapElement) {
		this.event = e;
		elements = mapElement;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public List<MapElement> getMapElements() {
		return elements;
	}

	public Point getMapPosition() {
		return mapPosition;
	}
}
