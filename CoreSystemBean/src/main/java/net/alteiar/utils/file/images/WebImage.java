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
package net.alteiar.utils.file.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.simpleframework.xml.Element;

/**
 * @author Cody Stoutenburg
 * 
 */
public class WebImage implements TransfertImage {
	@Element
	private final URL url;

	private transient BufferedImage image;

	public WebImage(URL url) {
		this.url = url;
	}

	@Override
	public BufferedImage restoreImage() throws IOException {
		if (image == null) {
			image = ImageIO.read(url);
		}
		return image;
	}

	@Override
	public void clearCache() {
		this.image = null;
	}
}
