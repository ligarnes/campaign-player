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
package net.alteiar.server.shared.geometry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Vector2D implements Externalizable, Cloneable {
	public final static Vector2D NULL_VECTOR = new Vector2D(0f, 0f);

	private Float x;
	private Float y;

	public Vector2D() {
		this(0.0f, 0.0f);
	}

	public Vector2D(Float x, Float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D add(Vector2D d) {
		return new Vector2D(this.x + d.getX(), this.y + d.getY());
	}

	public Vector2D multiply(Float d) {
		return new Vector2D(this.x * d, this.y * d);
	}

	public Vector2D divise(Float d) {
		return new Vector2D(this.x / d, this.y / d);
	}

	public Vector2D sub(Vector2D d) {
		return new Vector2D(this.x - d.getX(), this.y - d.getY());
	}

	public Float dotProduct(final Vector2D n) {
		return getX() * n.getX() + getY() * n.getY();
	}

	public double crossProduct(final Vector2D n) {
		return getX() * n.getY() - getY() * n.getX();
	}

	public Vector2D projecting(final Vector2D n) {
		return n.multiply(dotProduct(n) / n.getMagnitude());
	}

	public Vector2D getLeftNormal() {
		return new Vector2D(-getY(), getX());
	}

	public Vector2D getRightNormal() {
		return new Vector2D(getY(), -getX());
	}

	/**
	 * 
	 * @return angle - return the angle of the vector in radian (-pi to pi)
	 */
	public Float getAt() {
		return Double.valueOf(Math.atan2(y, x)).floatValue();
	}

	public void setAt(Float angle) {
		rotate(angle - getAt());
	}

	public Float getSquaredMagnitude() {
		return Double.valueOf(Math.pow(getX(), 2) + Math.pow(getY(), 2))
				.floatValue();
	}

	public Float getMagnitude() {
		return Double.valueOf(Math.sqrt(getSquaredMagnitude())).floatValue();
	}

	public Float getX() {
		return x;
	}

	public Float getY() {
		return y;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public void addX(Float x) {
		this.x += x;
	}

	public void addY(Float y) {
		this.y += y;
	}

	public void rotate(double angle) {
		Float tempX = Double.valueOf(x * Math.cos(angle) - y * Math.sin(angle))
				.floatValue();
		y = Double.valueOf(y * Math.cos(angle) + x * Math.sin(angle))
				.floatValue();
		x = tempX;
	}

	public void normalize() {
		// �viter de calculer atan2 si un des cot� est de longueur 0
		// c'est moins long et �vite les probl�mes de pr�cision
		if (x == 0) {
			if (y > 0) {
				y = 1.0f;
			} else {
				y = -1.0f;
			}
		} else if (y == 0) {
			if (x > 0) {
				x = 1.0f;
			} else {
				x = -1.0f;
			}
		} else {
			double at = getAt();
			x = Double.valueOf(Math.cos(at)).floatValue();
			y = Double.valueOf(Math.sin(at)).floatValue();
		}
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}

	public Float getSquaredDistance(Vector2D dest) {
		return Double.valueOf(
				Math.pow(this.getX() - dest.getX(), 2)
						+ Math.pow(this.getY() - dest.getY(), 2)).floatValue();
	}

	public Float getDistance(Vector2D dest) {
		return Double.valueOf(Math.sqrt(getSquaredDistance(dest))).floatValue();
	}

	@Override
	public Vector2D clone() {
		Vector2D clone = null;
		try {
			clone = (Vector2D) super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeFloat(x);
		out.writeFloat(y);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		x = in.readFloat();
		y = in.readFloat();
	}
}
