package pathfinder.mapElement.shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.alteiar.server.document.map.element.size.MapElementSize;

public class Circle extends ColoredShape {
	private static final long serialVersionUID = 1L;

	protected MapElementSize radius;

	public Circle(Color color, MapElementSize radius) {
		super(color);
		this.radius = radius;
	}

	public Double getRadius() {
		return radius.getPixels(getScale());
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Point p = getPosition();

		Shape shape = new Ellipse2D.Double(p.getX() * zoomFactor, p.getY()
				* zoomFactor, getRadius() * zoomFactor - STROKE_SIZE_LARGE,
				getRadius() * zoomFactor - STROKE_SIZE_LARGE);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		Point p = getPosition();
		Double strokeSizeMiddle = (double) strokeSize / 2;
		Shape shape = new Ellipse2D.Double(p.getX() * zoomFactor
				- strokeSizeMiddle, p.getY() * zoomFactor - strokeSizeMiddle,
				getRadius() * zoomFactor - STROKE_SIZE_LARGE, getRadius()
						* zoomFactor - STROKE_SIZE_LARGE);
		return shape;
	}

	@Override
	public Double getWidth() {
		return getRadius() * 2;
	}

	@Override
	public Double getHeight() {
		return getRadius() * 2;
	}
}
