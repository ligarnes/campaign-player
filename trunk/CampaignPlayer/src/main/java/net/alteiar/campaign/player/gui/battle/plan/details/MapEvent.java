package net.alteiar.campaign.player.gui.battle.plan.details;

import java.awt.Point;
import java.awt.event.MouseEvent;

import net.alteiar.client.shared.campaign.battle.character.ICharacterCombatClient;
import net.alteiar.client.shared.campaign.map.element.IMapElement;
import net.alteiar.client.shared.campaign.map.element.IMapElementClient;

public class MapEvent {
	private final MouseEvent event;
	private final IMapElement mapElement;
	private final Point mapPosition;

	public MapEvent(MouseEvent e, IMapElement mapElement, Point mapPosition) {
		super();
		this.event = e;
		this.mapElement = mapElement;
		this.mapPosition = mapPosition;
	}

	public MouseEvent getMouseEvent() {
		return event;
	}

	public IMapElement getMapElement() {
		return mapElement;
	}

	public Point getMapPosition() {
		return mapPosition;
	}

	public ICharacterCombatClient getCharacter() {
		ICharacterCombatClient character = null;
		if (mapElement instanceof ICharacterCombatClient) {
			character = (ICharacterCombatClient) mapElement;
		}
		return character;
	}

	public IMapElementClient getMapElementClient() {
		IMapElementClient character = null;
		if (mapElement instanceof IMapElementClient) {
			character = (IMapElementClient) mapElement;
		}
		return character;
	}
}
