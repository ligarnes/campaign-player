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
public class MapElementSizeSquare extends MapElementSize {
	private static final long serialVersionUID = -9152421144887148374L;
	@Element
	private Double squareSize;

	public MapElementSizeSquare() {
		this(0.0);
	}

	public MapElementSizeSquare(double squareSize) {
		this.squareSize = squareSize;
	}

	public Double getSquareSize() {
		return squareSize;
	}

	@Override
	public Double getPixels(Scale scale) {
		return (double) (scale.getPixels() * squareSize);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeDouble(squareSize);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		squareSize = in.readDouble();
	}

	@Override
	public Double getValue() {
		return squareSize;
	}

	@Override
	public String getUnitFormat() {
		return "cases";
	}

	@Override
	public String getShortUnitFormat() {
		return getUnitFormat();
	}

}
