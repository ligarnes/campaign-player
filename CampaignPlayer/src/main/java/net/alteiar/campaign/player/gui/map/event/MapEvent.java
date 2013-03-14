package net.alteiar.campaign.player.gui.map.event;

import java.awt.Point;
import java.awt.event.MouseEvent;

import net.alteiar.server.document.map.element.MapElementClient;

public class MapEvent {
	private final MouseEvent event;
	private final MapElementClient mapElement;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, MapElementClient mapElement, Point mapPosition) {
		super();
		this.event = e;
		this.mapElement = mapElement;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public MapElementClient getMapElement() {
		return mapElement;
	}

	public Point getMapPosition() {
		return mapPosition;
	}
}
