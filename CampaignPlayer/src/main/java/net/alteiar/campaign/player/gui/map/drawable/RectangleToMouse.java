package net.alteiar.campaign.player.gui.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;

public class RectangleToMouse extends Drawable {

	private final Point origin;

	public RectangleToMouse(MapEditableInfo map, Point origin) {
		super(map);
		this.origin = origin;
	}

	@Override
	protected void realDraw(Graphics2D g2, Point mouse) {
		Point2D org = this.getMapEditor().convertPointStandardToPanel(origin);

		int x = (int) Math.min(org.getX(), mouse.getX());
		int y = (int) Math.min(org.getY(), mouse.getY());
		int width = (int) Math.abs(mouse.getX() - org.getX());
		int height = (int) Math.abs(mouse.getY() - org.getY());

		g2.drawRect(x, y, width, height);
	}
}
