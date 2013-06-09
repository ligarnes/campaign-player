package net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public class PolygonToMouse extends LineToMouse {

	public PolygonToMouse(MapEditableInfo map, Point origin) {
		super(map, origin);
	}

	@Override
	protected void realDraw(Graphics2D g2, Point mouse) {
		super.realDraw(g2, mouse);
		Point2D org = getMapEditor().convertPointStandardToPanel(
				getPts().get(0));
		g2.drawLine((int) org.getX(), (int) org.getY(), mouse.x, mouse.y);
	}
}
