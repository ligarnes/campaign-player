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
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Polygon2D implements Externalizable {
	private ArrayList<ArrayList<Vector2D>> allRectangles;

	public Polygon2D() {
	}

	public Polygon2D(Area area) {
		super();
		allRectangles = new ArrayList<ArrayList<Vector2D>>();

		float[] coord = new float[6];
		int type;

		PathIterator pi = area.getPathIterator(AffineTransform
				.getRotateInstance(0));

		ArrayList<Vector2D> currentPolygon = null;
		while (!pi.isDone()) {

			type = pi.currentSegment(coord);
			switch (type) {
			case PathIterator.SEG_MOVETO:
				currentPolygon = new ArrayList<Vector2D>();
				// change pour un nouveau segment
				allRectangles.add(currentPolygon);
				currentPolygon.add(new Vector2D(coord[0], coord[1]));
				break;

			case PathIterator.SEG_LINETO:
				// trace une ligne
				currentPolygon.add(new Vector2D(coord[0], coord[1]));
				break;

			case PathIterator.SEG_QUADTO:
				// trace un arc de parabole
				currentPolygon.add(new Vector2D(coord[0], coord[1]));
				currentPolygon.add(new Vector2D(coord[2], coord[3]));
				break;

			case PathIterator.SEG_CUBICTO:
				// trace une courbe de bezier cubique
				currentPolygon.add(new Vector2D(coord[0], coord[1]));
				currentPolygon.add(new Vector2D(coord[2], coord[3]));
				currentPolygon.add(new Vector2D(coord[4], coord[5]));
				break;

			default:
				break;
			}

			pi.next();
		}
	}

	protected Polygon getPolygon(int idx) {
		Polygon p = new Polygon();

		for (Vector2D position : allRectangles.get(idx)) {
			p.addPoint(position.getX().intValue(), position.getY().intValue());
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

	public static List<Vector2D> findCollisionPoint(Polygon p1, Polygon p2) {
		List<Vector2D> collisionPoint = new ArrayList<Vector2D>();
		// chaque segment de p1
		for (int i = 0; i < p1.npoints; ++i) {
			Vector2D a1 = new Vector2D((float) p1.xpoints[i],
					(float) p1.ypoints[i]);
			int i2 = i + 1;
			if (i2 == p1.npoints) {
				i2 = 0;
			}
			Vector2D a2 = new Vector2D((float) p1.xpoints[i2],
					(float) p1.ypoints[i2]);

			// chaque segment de p2
			for (int j = 0; j < p2.npoints; ++j) {
				Vector2D b1 = new Vector2D((float) p2.xpoints[j],
						(float) p2.ypoints[j]);
				int j2 = j + 1;
				if (j2 == p2.npoints) {
					j2 = 0;
				}
				Vector2D b2 = new Vector2D((float) p2.xpoints[j2],
						(float) p2.ypoints[j2]);

				Vector2D collision = findCollisionPointOnSegment(a1, a2, b1, b2);
				if (collision != null) {
					collisionPoint.add(collision);
				}
			}
		}

		return collisionPoint;
	}

	public static Vector2D findCollisionPointOnSegment(Vector2D a1,
			Vector2D a2, Vector2D b1, Vector2D b2) {
		Vector2D result = null;

		Vector2D i = a2.sub(a1);
		Vector2D j = b2.sub(b1);

		Float denominateur = i.getX() * j.getY() - i.getY() * j.getX();

		// les droites ne sont pas parall�le
		if (!denominateur.equals(0f)) {
			Float a = i.getX() * a1.getY();
			Float b = i.getX() * b1.getY();
			Float c = i.getY() * a1.getX();
			Float d = i.getY() * b1.getX();

			Float m = -(-a + b + c - d) / denominateur;

			if (0f < m && m < 1f) {
				a = a1.getX() * j.getY();
				b = b1.getX() * j.getY();
				c = a1.getY() * j.getX();
				d = b1.getY() * j.getX();
				Float k = -(a - b - c + d) / denominateur;

				if (0f < k && k < 1f) {
					result = a1.add(i.multiply(k));
				}
			}

		}

		return result;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(allRectangles);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		allRectangles = (ArrayList<ArrayList<Vector2D>>) in.readObject();
	}
}
