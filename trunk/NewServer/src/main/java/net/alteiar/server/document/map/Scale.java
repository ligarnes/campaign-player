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
package net.alteiar.server.document.map;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Cody Stoutenburg
 * 
 * 
 *         the scale represent the number of pixel and of meter for 1 square
 */
public class Scale implements Externalizable {

	private Integer pixels;
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

	public Double getMetre() {
		return metre;
	}

	public void setMetre(Double metre) {
		this.metre = metre;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(pixels);
		out.writeDouble(metre);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		pixels = in.readInt();
		metre = in.readDouble();
	}
}
