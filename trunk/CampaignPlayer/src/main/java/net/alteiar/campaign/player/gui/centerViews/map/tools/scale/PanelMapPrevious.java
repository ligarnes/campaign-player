package net.alteiar.campaign.player.gui.centerViews.map.tools.scale;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.Scale;
import net.alteiar.zoom.Zoomable;

public class PanelMapPrevious extends JPanel implements Zoomable {
	private static final long serialVersionUID = 1L;

	protected final MapBean map;

	private Scale scale;
	private Double zoomFactor;

	public PanelMapPrevious(MapBean map) {
		this(map, map.getScale());
	}

	public PanelMapPrevious(MapBean map, Scale scale) {
		super();
		setLayout(null);

		this.map = map;
		this.scale = scale;

		this.setOpaque(false);

		zoomFactor = 2.00;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Anti-alias!
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

		Graphics2D graphicsMap = (Graphics2D) g2.create();
		graphicsMap.scale(zoomFactor, zoomFactor);
		// draw background
		map.drawBackground(graphicsMap);

		graphicsMap.dispose();

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		drawGrid(g2);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		drawGrid(g2);
	}

	public void setScale(Scale scale) {
		this.scale = scale;
		redraw();
	}

	protected void drawGrid(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		Double squareSize = scale.getPixels() * zoomFactor;

		Double width = map.getWidth() * zoomFactor;
		Double height = map.getHeight() * zoomFactor;

		for (double i = 0; i < width; i += squareSize) {
			g2.drawLine((int) i, 0, (int) i, height.intValue());
		}
		for (double i = 0; i < height; i += squareSize) {
			g2.drawLine(0, (int) i, width.intValue(), (int) i);
		}
		g2.dispose();
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
		redraw();
	}

	public void redraw() {
		this.revalidate();
		this.repaint();
	}
}
