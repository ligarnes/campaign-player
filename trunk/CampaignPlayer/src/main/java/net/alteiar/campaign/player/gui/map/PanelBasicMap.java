/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.campaign.player.gui.map;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.alteiar.campaign.player.gui.tools.Zoomable;
import net.alteiar.server.document.map.IMapListener;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.element.IMapElementListener;
import net.alteiar.server.document.map.element.MapElementClient;

/**
 * @author Cody Stoutenburg
 * 
 */
public abstract class PanelBasicMap extends JPanel implements IMapListener,
		IMapElementListener, Zoomable, ActionListener {
	private static final long serialVersionUID = -5027864086357387475L;

	protected final MapClient<?> map;
	private Double zoomFactor;

	private boolean drawPathToElement;
	private MapElementClient mapElement;

	private Color lineColor;

	private boolean drawLineToMouse;
	private boolean drawPolygonToMouse;
	private boolean drawRectangleToMouse;
	private Point origin;
	private final List<Point> lstOrigin;
	private String text;
	private Boolean showGrid;

	private final Timer refreshTime;

	public PanelBasicMap(MapClient<?> map) {
		super();

		this.map = map;
		this.map.addMapListener(this);

		for (MapElementClient element : this.map.getElements()) {
			element.addMapElementListener(this);
		}

		this.setOpaque(false);

		zoomFactor = 0.50;
		this.showGrid = true;

		drawLineToMouse = false;
		drawRectangleToMouse = false;
		drawPolygonToMouse = false;
		refreshTime = new Timer(20, this);

		lstOrigin = new ArrayList<Point>();

		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
	}

	public void showGrid(Boolean showGrid) {
		this.showGrid = showGrid;
		this.revalidate();
		this.repaint();
	}

	public Boolean getShowGrid() {
		return this.showGrid;
	}

	public List<Point> getTrajet() {
		return this.lstOrigin;
	}

	public void drawLineToMouse(Point origin) {
		drawLineToMouse(origin, Color.RED);
	}

	public void drawPathToElement(Point origin, MapElementClient mapElement) {
		drawPathToElement = true;
		this.mapElement = mapElement;
		refreshTime.start();

		lstOrigin.clear();
		lstOrigin.add(convertPointToSquare(origin));
	}

	public void addPointToPath(Point p) {
		lstOrigin.add(convertPointToSquare(p));
	}

	public void stopDrawPathToMouse() {
		drawPathToElement = false;
		mapElement = null;
		lstOrigin.clear();
		this.origin = null;
		refreshTime.stop();
		this.revalidate();
		this.repaint();
	}

	public Point convertPointToSquare(Point position) {
		Integer squareSize = this.map.getScale().getPixels();
		Point square = new Point();
		square.x = (int) Math.floor(position.x / squareSize.floatValue());
		square.y = (int) Math.floor(position.y / squareSize.floatValue());

		return square;
	}

	public Point2D.Double convertSquareToPoint(Point position) {
		Double squareSize = this.map.getScale().getPixels() * zoomFactor;
		return new Point2D.Double(squareSize * position.x, squareSize
				* position.y);
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

	public void drawLineToMouse(Point origin, Color color) {
		drawLineToMouse = true;
		refreshTime.start();

		lstOrigin.clear();
		lstOrigin.add(origin);

		lineColor = color;
	}

	public void drawPolygonToMouse(Point origin, Color color) {
		drawPolygonToMouse = true;
		refreshTime.start();

		lstOrigin.clear();
		lstOrigin.add(origin);

		lineColor = color;
	}

	public void addPointToLine(Point p) {
		lstOrigin.add(p);
	}

	public void drawRectangleToMouse(Point origin, Color color) {
		drawRectangleToMouse = true;
		refreshTime.start();
		this.origin = origin;

		lineColor = color;
	}

	public void setLineColor(Color color) {
		lineColor = color;
	}

	public void setShowText(String text) {
		this.text = text;
	}

	public void stopDrawLineToMouse() {
		drawLineToMouse = false;
		lstOrigin.clear();
		this.origin = null;
		refreshTime.stop();
		this.revalidate();
		this.repaint();
	}

	public void stopDrawPolygonToMouse() {
		drawPolygonToMouse = false;
		lstOrigin.clear();
		this.origin = null;
		refreshTime.stop();
		this.revalidate();
		this.repaint();
	}

	public void stopDrawRectangleToMouse() {
		drawRectangleToMouse = false;
		this.origin = null;
		refreshTime.stop();
		this.revalidate();
		this.repaint();
	}

	protected abstract void drawBackground(Graphics2D g2);

	protected abstract void drawElements(Graphics2D g2);

	protected abstract void drawGrid(Graphics2D g2);

	protected abstract void drawFilter(Graphics2D g2);

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

	protected void drawPath(Graphics2D g2, List<Point> pts) {
		pts = removeDuplicate(pts);
		Double squareSize = this.map.getScale().getPixels() * zoomFactor;
		int i = 0;
		for (Point point : pts) {
			if (point == null) {
				++i;
				continue;
			}
			Point2D.Double next = convertSquareToPoint(point);

			double gap = this.map.getScale().getPixels() * 0.1;
			double width = gap * zoomFactor;

			g2.setColor(Color.GRAY);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));

			g2.fillRoundRect((int) (next.x + width), (int) (next.y + width),
					(int) (squareSize - width * 2),
					(int) (squareSize - width * 2), (int) width, (int) width);

			g2.setColor(Color.BLACK);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.8f));

			if (i > 0) {
				String str = "" + i;
				Font f = g2.getFont();
				f = f.deriveFont(14f);
				f = f.deriveFont(Font.BOLD);
				g2.setFont(f);
				FontMetrics metrics = this.getFontMetrics(g2.getFont());
				Rectangle2D rect = metrics.getStringBounds(str, g2);

				double centerX = (squareSize - rect.getWidth()) / 2.0;
				double centerY = rect.getHeight()
						+ ((squareSize - rect.getHeight()) / 2.0);

				if (rect.getHeight() < (squareSize + width * 2)) {
					g2.drawString(str, (int) (next.x + centerX),
							(int) (next.y + centerY));
				}
			}
			++i;
		}
	}

	@Override
	public void paint(Graphics g) {
		if (map.getBackground() != null) {
			Graphics2D g2 = (Graphics2D) g;

			// Anti-alias!
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			Composite defaultComp = g2.getComposite();

			// draw background
			this.drawBackground(g2);

			// Draw all elements on map (monstre, characters, spell area, ...)
			this.drawElements(g2);

			// Draw grid
			if (showGrid) {
				this.drawGrid(g2);
			}

			// Draw the filter
			this.drawFilter(g2);

			// Draw other info
			g2.setComposite(defaultComp);
			if (drawPathToElement) {
				// compute last line
				List<Point> pts = new ArrayList<Point>();
				for (int i = 0; i < lstOrigin.size() - 1; ++i) {
					pts.addAll(computeLine(lstOrigin.get(i),
							lstOrigin.get(i + 1)));
				}

				pts.addAll(computeLine(lstOrigin.get(lstOrigin.size() - 1),
						convertPointToSquare(mapElement.getCenterPosition())));
				drawPath(g2, pts);
			}

			if (drawLineToMouse || drawRectangleToMouse || drawPolygonToMouse) {
				g2.setColor(lineColor);
				g2.setStroke(new BasicStroke(3));
				Point second = this.getMousePosition();
				if (second != null) {
					if (drawLineToMouse || drawPolygonToMouse) {
						for (int i = 0; i < lstOrigin.size() - 1; ++i) {
							Point2D.Double first = convertPointStandardToPanel(lstOrigin
									.get(i));
							Point2D.Double next = convertPointStandardToPanel(lstOrigin
									.get(i + 1));

							g2.drawLine((int) first.x, (int) first.y,
									(int) next.x, (int) next.y);
						}

						Point2D.Double org = convertPointStandardToPanel(lstOrigin
								.get(lstOrigin.size() - 1));
						g2.drawLine((int) org.x, (int) org.y, second.x,
								second.y);

						if (drawPolygonToMouse) {
							org = convertPointStandardToPanel(lstOrigin.get(0));
							second = this.getMousePosition();
							g2.drawLine((int) org.x, (int) org.y, second.x,
									second.y);
						}

					} else if (drawRectangleToMouse) {
						Point2D.Double org = convertPointStandardToPanel(origin);
						int x = (int) Math.min(org.x, second.x);
						int y = (int) Math.min(org.y, second.y);
						int width = (int) (org.x - second.x);
						int height = (int) (org.y - second.y);
						if (width < 0)
							width = -width;
						if (height < 0)
							height = -height;

						g2.drawRect(x, y, width, height);
					}
					if (text != null) {
						g2.setColor(Color.BLACK);
						g2.drawString(text, second.x, second.y);
					}
				}
			}

			g2.dispose();
		}
	}

	public Point2D.Double convertPointStandardToPanel(Point position) {
		return new Point2D.Double(position.x * zoomFactor, position.y
				* zoomFactor);
	}

	public Point convertPointPanelToStandard(Point click) {
		if (click == null) {
			return new Point();
		}
		return new Point((int) (click.x / zoomFactor),
				(int) (click.y / zoomFactor));
	}

	/**
	 * this method return the map element in the map at the position p on the
	 * panel
	 * 
	 * @param p
	 *            - the position of the map element on the panel
	 * @return the map element
	 */
	public MapElementClient getElementAt(Point p) {
		Collection<MapElementClient> elements = map.getElementsAt(p);
		// return the first element of the list
		return elements.size() > 0 ? elements.iterator().next() : null;
	}

	@Override
	public Double getZoomFactor() {
		return zoomFactor;
	}

	@Override
	public void zoom(double value) {
		zoomFactor = value;
		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mapElementAdded(MapElementClient element) {
		element.addMapElementListener(this);
		mapChanged();
	}

	@Override
	public void mapElementRemoved(MapElementClient element) {
		element.removeMapElementListener(this);
		mapChanged();
	}

	@Override
	public void mapRescale(Scale scale) {
		mapChanged();
	}

	@Override
	public void filterChanged() {
		mapChanged();
	}

	@Override
	public void elementChanged() {
		mapChanged();
	}

	private void mapChanged() {
		Dimension dim = new Dimension((int) (map.getWidth() * zoomFactor),
				(int) (map.getHeight() * zoomFactor));
		this.setPreferredSize(dim);
		this.revalidate();
		this.repaint();
	}

}
