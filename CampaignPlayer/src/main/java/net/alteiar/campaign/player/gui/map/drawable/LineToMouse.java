package net.alteiar.campaign.player.gui.map.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;

public class LineToMouse extends Drawable {

	private final ArrayList<Point> pts;
	private Point last;

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
	public void draw(Graphics2D g2, Point mouse) {
		if (mouse != last && mouse != null) {
			last = mouse;
		}

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
		g2.drawLine((int) org.x, (int) org.y, last.x, last.y);
	}
}
