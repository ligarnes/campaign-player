package net.alteiar.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;

public class MapFilter extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String METH_SHOW_POLYGON_METHOD = "showPolygon";
	public static final String METH_HIDE_POLYGON_METHOD = "hidePolygon";

	private transient Area hiddenArea;
	private final Integer width;
	private final Integer height;

	private final ArrayList<MyPolygonFilter> polygons;

	public MapFilter(Integer width, Integer height) {
		hiddenArea = new Area(new Rectangle2D.Double(0, 0, width, height));

		polygons = new ArrayList<MapFilter.MyPolygonFilter>();
		this.width = width;
		this.height = height;
	}

	public void showPolygon(Polygon polygon) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_SHOW_POLYGON_METHOD, null, polygon);

			synchronized (polygons) {
				polygons.add(new MyPolygonFilter(polygon, false));
			}
			hiddenArea.subtract(new Area(polygon));

			propertyChangeSupport.firePropertyChange(METH_SHOW_POLYGON_METHOD,
					null, polygon);
		} catch (PropertyVetoException e) {
			// TODO do not care
			// e.printStackTrace();
		}
	}

	public void hidePolygon(Polygon polygon) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_HIDE_POLYGON_METHOD, null, polygon);

			synchronized (polygons) {
				polygons.add(new MyPolygonFilter(polygon, true));
			}
			hiddenArea.add(new Area(polygon));

			propertyChangeSupport.firePropertyChange(METH_HIDE_POLYGON_METHOD,
					null, polygon);
		} catch (PropertyVetoException e) {
			// TODO do not care
			// e.printStackTrace();
		}
	}

	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.scale(zoomFactor, zoomFactor);
		g2.setColor(Color.BLACK);

		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));
		} else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1.0f));
		}
		g2.fill(hiddenArea);

		g2.dispose();
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

	private class MyPolygonFilter implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Polygon polygon;
		private final Boolean hide;

		public MyPolygonFilter(Polygon polygon, Boolean isHide) {
			super();
			this.polygon = polygon;
			this.hide = isHide;
		}

		public Polygon getPolygon() {
			return polygon;
		}

		public Boolean getHide() {
			return hide;
		}
	}
}
