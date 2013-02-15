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
package net.alteiar.client.shared.campaign.map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;

import net.alteiar.ExceptionTool;
import net.alteiar.server.shared.campaign.battle.map.Scale;

/**
 * @author Cody Stoutenburg
 * 
 */
@XmlRootElement(name = "Map2DSave")
public class Map2DSave {
	private String name;
	private byte[] background;
	private Integer pixels;
	private Double metre;

	public Map2DSave() {
	}

	public Map2DSave(IMap2DClient<?> map2D) {
		name = map2D.getName();
		pixels = map2D.getScale().getPixels();
		metre = map2D.getScale().getMetre();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(map2D.getBackground(), "png", baos);
			baos.flush();
			background = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			ExceptionTool.showError(e, "impossible de sauvegarder l'image");
		}
	}

	public Scale getScale() {
		return new Scale(pixels, metre);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBackground() {
		return background;
	}

	public void setBackground(byte[] background) {
		this.background = background;
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
}
