package net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public class LineToMouse extends MouseDrawable {

	private final ArrayList<Point> pts;

	public LineToMouse(MapEditableInfo map, Point origin) {
		super(map);
		pts = new ArrayList<Point>();
		pts.add(origin);
	}

	public void addPoint(Point pt) {
		pts.add(pt);
	}

	public ArrayList<Point> getPts() {
		return pts;
	}

	@Override
	protected void realDraw(Graphics2D g2, Point mouse) {
		for (int i = 0; i < pts.size() - 1; ++i) {
			Point2D.Double first = getMapEditor().convertPointStandardToPanel(
					pts.get(i));
			Point2D.Double next = getMapEditor().convertPointStandardToPanel(
					pts.get(i + 1));

			g2.drawLine((int) first.x, (int) first.y, (int) next.x,
					(int) next.y);
		}

		Point2D.Double org = getMapEditor().convertPointStandardToPanel(
				pts.get(pts.size() - 1));
		g2.drawLine((int) org.x, (int) org.y, mouse.x, mouse.y);
	}
}
