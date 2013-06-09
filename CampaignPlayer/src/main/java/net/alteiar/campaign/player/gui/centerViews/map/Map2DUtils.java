package net.alteiar.campaign.player.gui.centerViews.map;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class Map2DUtils {
	public static GeneralPath getRoundedGeneralPath(Polygon polygon) {
		List<int[]> l = new ArrayList<int[]>();
		for (int i = 0; i < polygon.npoints; i++) {
			l.add(new int[] { polygon.xpoints[i], polygon.ypoints[i] });
		}
		return getRoundedGeneralPath(l);
	}

	public static GeneralPath getRoundedGeneralPath(List<int[]> l) {
		List<Point> list = new ArrayList<Point>();
		for (int[] point : l) {
			list.add(new Point(point[0], point[1]));
		}
		return getRoundedGeneralPathFromPoints(list);
	}

	public static GeneralPath getRoundedGeneralPathFromPoints(List<Point> l) {
		l.add(l.get(0));
		l.add(l.get(1));
		GeneralPath p = new GeneralPath();
		p.moveTo(l.get(0).x, l.get(0).y);
		for (int pointIndex = 1; pointIndex < l.size() - 1; pointIndex++) {
			Point p1 = l.get(pointIndex - 1);
			Point p2 = l.get(pointIndex);
			Point p3 = l.get(pointIndex + 1);
			Point mPoint = calculatePoint(p1, p2);
			p.lineTo(mPoint.x, mPoint.y);
			mPoint = calculatePoint(p3, p2);
			p.curveTo(p2.x, p2.y, p2.x, p2.y, mPoint.x, mPoint.y);
		}
		return p;
	}

	private static Point calculatePoint(Point p1, Point p2) {
		float arcSize = 10;
		double d1 = Math.sqrt(Math.pow(p1.x - p2.x, 2)
				+ Math.pow(p1.y - p2.y, 2));
		double per = arcSize / d1;
		double d_x = (p1.x - p2.x) * per;
		double d_y = (p1.y - p2.y) * per;
		int xx = (int) (p2.x + d_x);
		int yy = (int) (p2.y + d_y);
		return new Point(xx, yy);
	}

	public static void line(int x, int y, int x2, int y2, List<Point> pts) {
		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;

		for (int i = 0; i <= longest; i++) {
			pts.add(new Point(x, y));
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}
}
