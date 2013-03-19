package net.alteiar.map.elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyVetoException;

import net.alteiar.utils.map.element.MapElementSize;

public class Circle extends ColoredShape {
	private static final long serialVersionUID = 1L;

	public static final String PROP_RADIUS_PROPERTY = "radius";

	private MapElementSize radius;

	public Circle(Color color, MapElementSize radius) {
		super(color);
		this.radius = radius;
	}

	public Double getRadiusPixel() {
		return radius.getPixels(getScale());
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Point p = getPosition();

		Shape shape = new Ellipse2D.Double(p.getX() * zoomFactor, p.getY()
				* zoomFactor,
				getRadiusPixel() * zoomFactor - STROKE_SIZE_LARGE,
				getRadiusPixel() * zoomFactor - STROKE_SIZE_LARGE);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		Point p = getPosition();
		Double strokeSizeMiddle = (double) strokeSize / 2;
		Shape shape = new Ellipse2D.Double(p.getX() * zoomFactor
				- strokeSizeMiddle, p.getY() * zoomFactor - strokeSizeMiddle,
				getRadiusPixel() * zoomFactor - STROKE_SIZE_LARGE,
				getRadiusPixel() * zoomFactor - STROKE_SIZE_LARGE);
		return shape;
	}

	@Override
	public Double getWidthPixels() {
		return getRadiusPixel() * 2;
	}

	@Override
	public Double getHeightPixels() {
		return getRadiusPixel() * 2;
	}

	// ///////////////// BEAN METHODS ///////////////////////
	public MapElementSize getRadius() {
		return this.radius;
	}

	public void setRadius(MapElementSize radius) {
		MapElementSize oldValue = this.radius;
		try {
			vetoableChangeSupport.fireVetoableChange(PROP_RADIUS_PROPERTY,
					oldValue, radius);
			this.radius = radius;
			propertyChangeSupport.firePropertyChange(PROP_RADIUS_PROPERTY,
					oldValue, radius);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
