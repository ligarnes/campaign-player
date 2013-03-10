package net.alteiar.server.document.map.element.colored;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.rmi.RemoteException;

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

	protected abstract Shape getShape(double zoomFactor);

	protected abstract Shape getShapeBorder(double zoomFactor, int strokeSize);

	private Shape transform(Shape shape, double zoomFactor) {
		// work fine for rotation but not ideal for the moment because the shape
		// need to be drawn at (0, 0)
		double angle = Math.toRadians(getAngle());

		AffineTransform transform = AffineTransform.getTranslateInstance(getX()
				* zoomFactor, getY() * zoomFactor);
		transform.rotate(angle, (getWidth() * zoomFactor) / 2.0,
				(getHeight() * zoomFactor) / 2.0);

		return transform.createTransformedShape(shape);
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		// Compute the size of the stroke
		Integer strokeSize = STROKE_SIZE_LARGE;
		if (getWidth() < 100 || getHeight() < 100) {
			strokeSize = STROKE_SIZE_SMALL;
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the element
		g2.setColor(getColor());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));

		g2.fill(getShape(zoomFactor));

		// Draw the border
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(strokeSize));
		g2.draw(getShapeBorder(zoomFactor, strokeSize));

		g2.dispose();
	}

	@Override
	public Boolean contain(Point p) {
		return getShape(1.0).contains(p);
	}
}
