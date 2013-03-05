package net.alteiar.server.document.map.element.colored;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.rmi.RemoteException;

import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.element.MapElementClient;

public abstract class MapElementColoredClient<E extends MapElementColoredRemote>
		extends MapElementClient<E> {
	private static final long serialVersionUID = 1L;

	// the position is the position of the upper left corner
	private final Color color;

	public MapElementColoredClient(E remote) throws RemoteException {
		super(remote);

		color = remote.getColor();
	}

	public Color getColor() {
		return color;
	}

	protected abstract double getWidth(Scale scale, double zoomFactor);

	protected abstract double getHeight(Scale scale, double zoomFactor);

	protected abstract Shape getShape(Scale scale, double zoomFactor);

	protected abstract Shape getShapeBorder(Scale scale, double zoomFactor,
			int strokeSize);

	protected double getX(Scale scale, double zoomFactor) {
		return getPosition().x * zoomFactor;
	}

	protected double getY(Scale scale, double zoomFactor) {
		return getPosition().y * zoomFactor;
	}

	@Override
	public void draw(Graphics2D g2, Scale scale, double zoomFactor) {
		// Compute the size of the stroke
		Integer strokeSize = STROKE_SIZE_LARGE;
		if (getWidth(scale, zoomFactor) < 100
				|| getHeight(scale, zoomFactor) < 100) {
			strokeSize = STROKE_SIZE_SMALL;
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the element
		g2.setColor(getColor());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));
		g2.fill(getShape(scale, zoomFactor));

		// Draw the border
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(strokeSize));
		g2.draw(getShapeBorder(scale, zoomFactor, strokeSize));
	}

	public boolean contain(Point p, Scale scale, double zoomFactor) {
		return getShape(scale, zoomFactor).contains(p);
	}
}
