package test.pathfinder.mapElement.shape;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.server.document.map.element.IAction;
import net.alteiar.server.document.map.element.MapElement;

public abstract class TestColoredShape extends MapElement {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	private final Color color;

	public TestColoredShape(Color color) {
		super();
		this.color = color;
	}

	@Override
	protected void load() {

	}

	public Color getColor() {
		return color;
	}

	protected abstract Shape getShape(double zoomFactor);

	protected abstract Shape getShapeBorder(double zoomFactor, int strokeSize);

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

	@Override
	public List<IAction> getActions() {
		return new ArrayList<IAction>();
	}
}
