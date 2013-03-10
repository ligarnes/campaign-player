package net.alteiar.server.document.map.element.character;

import java.awt.Graphics2D;
import java.io.Serializable;

import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.Scale;

public abstract class BasicCharacterMap implements Serializable {
	private static final long serialVersionUID = 1L;

	private final MapElementCharacterClient client;

	public BasicCharacterMap(MapElementCharacterClient mapElement) {
		this.client = mapElement;
	}

	/*
	 * public ICharacter getCharacter() { return client.getCharacter(); }
	 */

	public CharacterClient getCharacter() {
		return client.getCharacter();
	}

	public Scale getScale() {
		return client.getScale();
	}

	public int getX() {
		return client.getX();
	}

	public int getY() {
		return client.getY();
	}

	public abstract Double getWidth();

	public abstract Double getHeight();

	public abstract void draw(Graphics2D g, double zoomFactor);
}
