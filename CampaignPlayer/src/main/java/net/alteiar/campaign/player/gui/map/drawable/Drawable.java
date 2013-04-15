package net.alteiar.campaign.player.gui.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;

public abstract class Drawable {
	private final MapEditableInfo mapPanel;

	public Drawable(MapEditableInfo mapPanel) {
		this.mapPanel = mapPanel;
	}

	protected MapEditableInfo getMapEditor() {
		return mapPanel;
	}

	public abstract void draw(Graphics2D g2, Point mouse);
}
