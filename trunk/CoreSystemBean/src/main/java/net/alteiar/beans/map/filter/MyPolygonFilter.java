package net.alteiar.beans.map.filter;

import java.awt.Polygon;

import org.simpleframework.xml.Element;

public class MyPolygonFilter {

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