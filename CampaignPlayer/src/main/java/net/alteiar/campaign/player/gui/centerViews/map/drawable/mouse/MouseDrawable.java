package net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse;

import java.awt.Graphics2D;
import java.awt.Point;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public abstract class MouseDrawable {
	private final MapEditableInfo mapPanel;
	private Point last;

	public MouseDrawable(MapEditableInfo mapPanel) {
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
