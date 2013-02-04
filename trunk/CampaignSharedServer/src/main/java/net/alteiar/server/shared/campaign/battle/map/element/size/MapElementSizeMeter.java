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
package net.alteiar.server.shared.campaign.battle.map.element.size;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import net.alteiar.server.shared.campaign.battle.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
public class MapElementSizeMeter extends MapElementSize {
	private static final long serialVersionUID = 4046777176021745119L;

	private Double meters;

	public MapElementSizeMeter() {
		this(0.0);
	}

	public MapElementSizeMeter(Double meters) {
		this.meters = meters;
	}

	public Double getMeters() {
		return this.meters;
	}

	@Override
	public Double getPixels(Scale scale) {
		Double nbMetre = scale.getMetre();
		Integer nbPixel = scale.getPixels();

		// calculate pixel/meter*size in meters
		Double size = (nbPixel / nbMetre) * meters;
		return size;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeDouble(meters);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		meters = in.readDouble();
	}
}
