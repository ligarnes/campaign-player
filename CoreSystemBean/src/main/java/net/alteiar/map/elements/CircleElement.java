package net.alteiar.map.elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.alteiar.utils.map.element.MapElementSize;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class CircleElement extends ColoredShape {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String PROP_RADIUS_PROPERTY = "radius";
	@Element
	private MapElementSize radius;

	protected CircleElement() {

	}

	public CircleElement(Point position, Color color, MapElementSize radius) {
		super(position, color);
		this.radius = radius;
	}

	public Double getRadiusPixel() {
		return radius.getPixels(getScale());
	}

	@Override
	protected Shape getShape(double zoomFactor) {
		Point p = getPosition();

		Double strokeSizeMiddle = STROKE_SIZE_LARGE / 2.0;

		double x = p.getX() * zoomFactor + strokeSizeMiddle;
		double y = p.getY() * zoomFactor + strokeSizeMiddle;

		double radius = getRadiusPixel() * zoomFactor - STROKE_SIZE_LARGE;

		Shape shape = new Ellipse2D.Double(x, y, radius, radius);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(double zoomFactor, int strokeSize) {
		return getShape(zoomFactor);
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
		if (notifyRemote(PROP_RADIUS_PROPERTY, oldValue, radius)) {
			this.radius = radius;
			propertyChangeSupport.firePropertyChange(PROP_RADIUS_PROPERTY,
					oldValue, radius);
		}
	}

}
