package net.alteiar.map.filter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.ElementList;

public class ManualMapFilter extends MapFilter {
	private static final long serialVersionUID = 1L;

	public static final String METH_SHOW_POLYGON_METHOD = "showPolygon";
	public static final String METH_HIDE_POLYGON_METHOD = "hidePolygon";

	public static final String PROP_POLYGONS_PROPERTY = "polygons";

	private transient Area hiddenArea;

	@ElementList
	private ArrayList<MyPolygonFilter> polygons;

	public ManualMapFilter(UniqueID map) {
		super(map);
		hiddenArea = new Area(new Rectangle2D.Double(0, 0, getWidth(),
				getHeight()));

		polygons = new ArrayList<MyPolygonFilter>();
	}

	public ManualMapFilter() {
		hiddenArea = new Area();

		polygons = new ArrayList<MyPolygonFilter>();
	}

	public void showAll() {
		ArrayList<MyPolygonFilter> pol = new ArrayList<MyPolygonFilter>();
		pol.add(new MyPolygonFilter(new Polygon(new int[] { 0, getWidth(),
				getWidth(), 0 }, new int[] { 0, 0, getHeight(), getHeight() },
				4), false));
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

			notifyLocal(METH_SHOW_POLYGON_METHOD, null, polygon);
		}
	}

	public void hidePolygon(Polygon polygon) {
		if (notifyRemote(METH_HIDE_POLYGON_METHOD, null, polygon)) {
			synchronized (polygons) {
				polygons.add(new MyPolygonFilter(polygon, true));
			}
			hiddenArea.add(new Area(polygon));

			notifyLocal(METH_HIDE_POLYGON_METHOD, null, polygon);
		}
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor, boolean isDm) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.scale(zoomFactor, zoomFactor);
		g2.setColor(Color.BLACK);

		if (isDm) {
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

	public ArrayList<MyPolygonFilter> getPolygons() {
		return polygons;
	}

	public void setPolygons(ArrayList<MyPolygonFilter> polygons) {
		ArrayList<MyPolygonFilter> oldValue = this.polygons;
		if (notifyRemote(PROP_POLYGONS_PROPERTY, oldValue, polygons)) {

			this.polygons = polygons;
			hiddenArea = new Area(new Rectangle2D.Double(0, 0, getWidth(),
					getHeight()));

			for (MyPolygonFilter polygon : polygons) {
				if (polygon.getHide()) {
					hiddenArea.add(new Area(polygon.getPolygon()));
				} else {
					hiddenArea.subtract(new Area(polygon.getPolygon()));
				}
			}
			notifyLocal(PROP_POLYGONS_PROPERTY, oldValue, polygons);
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		hiddenArea = new Area(new Rectangle2D.Double(0, 0, getWidth(),
				getHeight()));

		for (MyPolygonFilter polygon : polygons) {
			if (polygon.getHide()) {
				hiddenArea.add(new Area(polygon.getPolygon()));
			} else {
				hiddenArea.subtract(new Area(polygon.getPolygon()));
			}
		}
	}

}
