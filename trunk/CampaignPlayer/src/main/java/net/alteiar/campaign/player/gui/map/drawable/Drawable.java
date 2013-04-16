package net.alteiar.campaign.player.gui.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;

public abstract class Drawable {
	private final MapEditableInfo mapPanel;
	private Point last;

	public Drawable(MapEditableInfo mapPanel) {
		this.mapPanel = mapPanel;
	}

	protected MapEditableInfo getMapEditor() {
		return mapPanel;
	}

	public final void draw(Graphics2D g2, Point mouse) {
		if (mouse != last && mouse != null) {
			last = mouse;
		}
		realDraw((Graphics2D) g2.create(), last);
	}

	protected abstract void realDraw(Graphics2D g2, Point mouse);
}
