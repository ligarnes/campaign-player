package net.alteiar.campaign.player.gui.centerViews.map.drawable.mouse;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.alteiar.beans.map.MapUtils;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.gui.centerViews.map.Map2DUtils;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public class PathToMouse extends LineToMouse {

	public PathToMouse(MapEditableInfo map, MapElement element) {
		super(map, MapUtils.convertPointToSquare(map.getMap(),
				element.getCenterPosition()));
	}

	@Override
	public void addPoint(Point pt) {
		super.addPoint(MapUtils.convertPointToSquare(getMapEditor().getMap(),
				pt));
	}

	@Override
	protected void realDraw(Graphics2D g2, Point mouse) {
		// compute last line
		List<Point> ptsPath = getPts();
		List<Point> pts = new ArrayList<Point>();
		for (int i = 0; i < ptsPath.size() - 1; ++i) {
			pts.addAll(computeLine(ptsPath.get(i), ptsPath.get(i + 1)));
		}

		Point point = getMapEditor().convertPointPanelToStandard(mouse);
		pts.addAll(computeLine(ptsPath.get(ptsPath.size() - 1),
				MapUtils.convertPointToSquare(getMapEditor().getMap(), point)));
		drawPath(g2, pts);
	}

	protected void drawPath(Graphics2D g, List<Point> pts) {
		Graphics2D g2 = (Graphics2D) g.create();

		pts = removeDuplicate(pts);
		double zoomFactor = getMapEditor().getZoomFactor();
		Double squareSize = getMapEditor().getMap().getScale().getPixels()
				* zoomFactor;

		int i = 0;
		for (Point point : pts) {
			if (point == null) {
				++i;
				continue;
			}

			Point2D.Double next = MapUtils.convertSquareToPoint(getMapEditor()
					.getMap(), point, zoomFactor);

			g2.translate(next.x, next.y);

			double gap = getMapEditor().getMap().getScale().getPixels() * 0.1;
			double width = gap * zoomFactor;

			Color color = CampaignClient.getInstance().getCurrentPlayer()
					.getRealColor();
			g2.setColor(color);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));

			g2.fillRoundRect((int) width, (int) width,
					(int) (squareSize - width * 2),
					(int) (squareSize - width * 2), (int) width, (int) width);

			g2.setColor(Color.BLACK);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.8f));

			if (i > 0) {
				// draw the cell move
				String str = "" + i;
				Font f = g2.getFont();
				f = f.deriveFont(14f);
				f = f.deriveFont(Font.BOLD);
				g2.setFont(f);
				FontMetrics metrics = g2.getFontMetrics(g2.getFont());
				Rectangle2D rect = metrics.getStringBounds(str, g2);

				double centerX = (squareSize - rect.getWidth()) / 2.0;
				double centerY = rect.getHeight()
						+ ((squareSize - rect.getHeight()) / 2.0);

				if (rect.getHeight() < (squareSize + width * 2)) {
					g2.drawString(str, (int) (centerX), (int) (centerY));
				}
			}

			g2.translate(-next.x, -next.y);
			++i;
		}

		g2.dispose();
	}

	private List<Point> removeDuplicate(List<Point> pts) {
		Collections.reverse(pts);
		HashSet<Point> sets = new HashSet<Point>();
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point point : pts) {
			if (sets.add(point)) {
				result.add(point);
			} else {
				result.add(null);
			}
		}
		Collections.reverse(result);
		return result;
	}

	public List<Point> computeLine(Point start, Point end) {
		List<Point> pts = new ArrayList<Point>();

		boolean invert = false;
		if (start.x > end.x) {
			Point tmp = end;
			end = start;
			start = tmp;

			invert = true;
		}

		Map2DUtils.line(start.x, start.y, end.x, end.y, pts);

		Point previous = pts.get(0);
		for (int i = 0; i < pts.size(); ++i) {
			Point next = pts.get(i);
			if (next.distance(previous) > 1) {
				pts.add(i, new Point(previous.x, next.y));
				i++;
			}
			previous = next;
		}

		if (invert) {
			Collections.reverse(pts);
		}
		pts.remove(pts.size() - 1);

		return pts;
	}
}
