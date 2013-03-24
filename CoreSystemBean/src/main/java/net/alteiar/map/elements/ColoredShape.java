package net.alteiar.map.elements;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.beans.PropertyVetoException;

public abstract class ColoredShape extends MapElement {
	private static final long serialVersionUID = 1L;

	public static final String PROP_COLOR_PROPERTY = "color";

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	private Color color;

	public ColoredShape(Long mapId, Point position, Color color) {
		super(mapId, position);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		Color oldValue = this.color;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_COLOR_PROPERTY,
					oldValue, color);
			this.color = color;
			propertyChangeSupport.firePropertyChange(PROP_COLOR_PROPERTY,
					oldValue, color);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	protected abstract Shape getShape(double zoomFactor);

	protected abstract Shape getShapeBorder(double zoomFactor, int strokeSize);

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		// Compute the size of the stroke
		Integer strokeSize = STROKE_SIZE_LARGE;
		if (getWidthPixels() < 100 || getHeightPixels() < 100) {
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
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
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
