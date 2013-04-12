package net.alteiar.map.filter;

import java.awt.Polygon;
import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class MyPolygonFilter implements Serializable {
	@Attribute
	private static final long serialVersionUID = 1L;

	@Element
	private Polygon polygon;
	@Element
	private Boolean hide;

	public MyPolygonFilter(Polygon polygon, Boolean isHide) {
		super();
		this.polygon = polygon;
		this.hide = isHide;
	}

	public MyPolygonFilter() {
		super();
		this.polygon = new Polygon();
		this.hide = false;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}
}