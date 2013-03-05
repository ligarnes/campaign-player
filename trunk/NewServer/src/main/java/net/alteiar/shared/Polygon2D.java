/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg . All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.shared;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Polygon2D implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Point2D>> allRectangles;

	public Polygon2D() {
	}

	public Polygon2D(Area area) {
		super();
		allRectangles = new ArrayList<ArrayList<Point2D>>();

		float[] coord = new float[6];
		int type;

		PathIterator pi = area.getPathIterator(AffineTransform
				.getRotateInstance(0));

		ArrayList<Point2D> currentPolygon = null;
		while (!pi.isDone()) {

			type = pi.currentSegment(coord);
			switch (type) {
			case PathIterator.SEG_MOVETO:
				currentPolygon = new ArrayList<Point2D>();
				// change pour un nouveau segment
				allRectangles.add(currentPolygon);
				currentPolygon.add(new Point2D.Float(coord[0], coord[1]));
				break;

			case PathIterator.SEG_LINETO:
				// trace une ligne
				currentPolygon.add(new Point2D.Float(coord[0], coord[1]));
				break;

			case PathIterator.SEG_QUADTO:
				// trace un arc de parabole
				currentPolygon.add(new Point2D.Float(coord[0], coord[1]));
				currentPolygon.add(new Point2D.Float(coord[2], coord[3]));
				break;

			case PathIterator.SEG_CUBICTO:
				// trace une courbe de bezier cubique
				currentPolygon.add(new Point2D.Float(coord[0], coord[1]));
				currentPolygon.add(new Point2D.Float(coord[2], coord[3]));
				currentPolygon.add(new Point2D.Float(coord[4], coord[5]));
				break;

			default:
				break;
			}

			pi.next();
		}
	}

	protected Polygon getPolygon(int idx) {
		Polygon p = new Polygon();

		for (Point2D position : allRectangles.get(idx)) {
			p.addPoint((int) position.getX(), (int) position.getY());
		}

		return p;
	}

	private List<Polygon> getPolygons() {
		List<Polygon> result = new ArrayList<Polygon>();
		for (int i = 0; i < allRectangles.size(); i++) {
			result.add(getPolygon(i));
		}

		return result;
	}

	public List<Polygon> getInternals() {
		List<Polygon> all = getPolygons();
		List<Polygon> internals = new ArrayList<Polygon>();
		List<Polygon> externals = new ArrayList<Polygon>();

		if (all.size() <= 1) {
			return internals;
		}

		for (Polygon p1 : all) {
			Boolean isInternal = false;
			for (Polygon p2 : all) {
				if (!p1.equals(p2)) {
					if (p2.contains(p1.getBounds2D())) {
						isInternal = true;
						break;
					}
				}
			}

			if (isInternal) {
				internals.add(p1);
			} else {
				externals.add(p1);
			}
		}
		return internals;
	}

	public List<Polygon> getExternals() {
		List<Polygon> all = getPolygons();
		List<Polygon> internals = new ArrayList<Polygon>();
		List<Polygon> externals = new ArrayList<Polygon>();

		if (all.size() <= 1) {
			return all;
		}

		for (Polygon p1 : all) {
			Boolean isInternal = false;
			for (Polygon p2 : all) {
				if (!p1.equals(p2)) {
					if (p2.contains(p1.getBounds2D())) {
						isInternal = true;
						break;
					}
				}
			}

			if (isInternal) {
				internals.add(p1);
			} else {
				externals.add(p1);
			}
		}
		return externals;
	}
}
