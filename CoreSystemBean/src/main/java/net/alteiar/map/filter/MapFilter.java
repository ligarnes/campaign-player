package net.alteiar.map.filter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class MapFilter extends BasicBean {
	@Attribute
	private static final long serialVersionUID = 1L;

	public static final String METH_SHOW_POLYGON_METHOD = "showPolygon";
	public static final String METH_HIDE_POLYGON_METHOD = "hidePolygon";

	public static final String PROP_POLYGONS_PROPERTY = "polygons";

	// @Element
	private transient Area hiddenArea;
	@Element
	private Integer width;
	@Element
	private Integer height;
	@ElementList
	private ArrayList<MyPolygonFilter> polygons;

	public MapFilter(Integer width, Integer height) {
		hiddenArea = new Area(new Rectangle2D.Double(0, 0, width, height));

		polygons = new ArrayList<MyPolygonFilter>();
		this.width = width;
		this.height = height;
	}

	public MapFilter() {
		hiddenArea = new Area();

		polygons = new ArrayList<MyPolygonFilter>();
		this.width = 0;
		this.height = 0;
	}

	public void showAll() {
		ArrayList<MyPolygonFilter> pol = new ArrayList<MyPolygonFilter>();
		pol.add(new MyPolygonFilter(new Polygon(
				new int[] { 0, width, width, 0 }, new int[] { 0, 0, height,
						height }, 4), false));
		setPolygons(pol);
	}

	public void hideAll() {
		setPolygons(new ArrayList<MyPolygonFilter>());
	}

	public void showPolygon(Polygon polygon) {
		if (notifyRemote(METH_SHOW_POLYGON_METHOD, null, polygon)) {
			synchronized (polygons) {
				polygons.add(new MyPolygonFilter(polygon, false));
			}
			hiddenArea.subtract(new Area(polygon));

			propertyChangeSupport.firePropertyChange(METH_SHOW_POLYGON_METHOD,
					null, polygon);
		}
	}

	public void hidePolygon(Polygon polygon) {
		if (notifyRemote(METH_HIDE_POLYGON_METHOD, null, polygon)) {
			synchronized (polygons) {
				polygons.add(new MyPolygonFilter(polygon, true));
			}
			hiddenArea.add(new Area(polygon));

			propertyChangeSupport.firePropertyChange(METH_HIDE_POLYGON_METHOD,
					null, polygon);
		}
	}

	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.scale(zoomFactor, zoomFactor);
		g2.setColor(Color.BLACK);

		if (CampaignClient.getInstance().getCurrentPlayer().isDm()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));
		} else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1.0f));
		}
		g2.fill(hiddenArea);

		g2.dispose();
	}

	// GETTERS AND SETTERS
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public ArrayList<MyPolygonFilter> getPolygons() {
		return polygons;
	}

	public void setPolygons(ArrayList<MyPolygonFilter> polygons) {
		ArrayList<MyPolygonFilter> oldValue = this.polygons;
		if (notifyRemote(PROP_POLYGONS_PROPERTY, oldValue, polygons)) {

			this.polygons = polygons;
			hiddenArea = new Area(new Rectangle2D.Double(0, 0, width, height));

			for (MyPolygonFilter polygon : polygons) {
				if (polygon.getHide()) {
					hiddenArea.add(new Area(polygon.getPolygon()));
				} else {
					hiddenArea.subtract(new Area(polygon.getPolygon()));
				}
			}
			this.propertyChangeSupport.firePropertyChange(
					PROP_POLYGONS_PROPERTY, oldValue, polygons);
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		hiddenArea = new Area(new Rectangle2D.Double(0, 0, width, height));

		for (MyPolygonFilter polygon : polygons) {
			if (polygon.getHide()) {
				hiddenArea.add(new Area(polygon.getPolygon()));
			} else {
				hiddenArea.subtract(new Area(polygon.getPolygon()));
			}
		}
	}

}
