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
package net.alteiar.beans.map;

import java.io.Serializable;

import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 * 
 *         the scale represent the number of pixel and of meter for 1 square
 */
public class Scale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Element
	private Integer pixels;
	@Element
	private Double metre;

	public Scale() {
		this(0, 0.0);
	}

	/**
	 * @param pixels
	 * @param metre
	 */
	public Scale(Integer pixels, Double metre) {
		super();
		this.pixels = pixels;
		this.metre = metre;
	}

	public Integer getPixels() {
		return pixels;
	}

	public void setPixels(Integer pixels) {
		this.pixels = pixels;
	}

	public Double getMeter() {
		return metre;
	}

	public void setMeter(Double metre) {
		this.metre = metre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metre == null) ? 0 : metre.hashCode());
		result = prime * result + ((pixels == null) ? 0 : pixels.hashCode());
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
		Scale other = (Scale) obj;
		if (metre == null) {
			if (other.metre != null)
				return false;
		} else if (!metre.equals(other.metre))
			return false;
		if (pixels == null) {
			if (other.pixels != null)
				return false;
		} else if (!pixels.equals(other.pixels))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Scale [pixels=" + pixels + ", metre=" + metre + "]";
	}
}
