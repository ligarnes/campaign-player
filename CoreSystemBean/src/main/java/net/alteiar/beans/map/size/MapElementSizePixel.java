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
package net.alteiar.beans.map.size;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import net.alteiar.beans.map.Scale;

import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementSizePixel extends MapElementSize {
	private static final long serialVersionUID = -376388590241288668L;
	@Element
	private Double pixels;

	public MapElementSizePixel() {
		this(0.0);
	}

	public MapElementSizePixel(Double pixels) {
		this.pixels = pixels;
	}

	public Double getPixels() {
		return pixels;
	}

	@Override
	public Double getPixels(Scale scale) {
		return pixels;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeDouble(pixels);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		pixels = in.readDouble();
	}

	@Override
	public Double getValue() {
		return pixels;
	}

	@Override
	public String getUnitFormat() {
		return "pixels";
	}

	@Override
	public String getShortUnitFormat() {
		return "px";
	}

}
