package net.alteiar.campaign.player.gui.battle.plan.details;

import java.awt.Point;
import java.awt.event.MouseEvent;

import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.character.CharacterCombatClient;

public class MapEvent {
	private final MouseEvent event;
	private final MapElementClient<?> mapElement;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, MapElementClient<?> mapElement,
			Point mapPosition) {
		super();
		this.event = e;
		this.mapElement = mapElement;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public MapElementClient<?> getMapElement() {
		return mapElement;
	}

	public Point getMapPosition() {
		return mapPosition;
	}

	public CharacterCombatClient getCharacter() {
		CharacterCombatClient character = null;
		if (mapElement instanceof CharacterCombatClient) {
			character = (CharacterCombatClient) mapElement;
		}
		return character;
	}

	public MapElementClient<?> getMapElementClient() {
		MapElementClient<?> character = null;
		if (mapElement instanceof MapElementClient<?>) {
			character = mapElement;
		}
		return character;
	}
}
