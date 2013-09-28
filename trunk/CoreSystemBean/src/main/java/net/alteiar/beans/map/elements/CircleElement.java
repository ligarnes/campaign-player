package net.alteiar.beans.map.elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.alteiar.beans.map.size.MapElementSize;

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
	protected Shape getShape() {
		Point p = getPosition();

		Double strokeSizeMiddle = STROKE_SIZE_LARGE / 2.0;

		double x = p.getX() + strokeSizeMiddle;
		double y = p.getY() + strokeSizeMiddle;

		double diameter = (getRadiusPixel() - STROKE_SIZE_LARGE) * 2;

		Shape shape = new Ellipse2D.Double(x, y, diameter, diameter);

		return shape;
	}

	@Override
	protected Shape getShapeBorder(int strokeSize) {
		return getShape();
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
			notifyLocal(PROP_RADIUS_PROPERTY, oldValue, radius);
		}
	}

	@Override
	public String getNameFormat() {
		return "Cercle " + getRadius().getValue() + " "
				+ getRadius().getShortUnitFormat();
	}

}
