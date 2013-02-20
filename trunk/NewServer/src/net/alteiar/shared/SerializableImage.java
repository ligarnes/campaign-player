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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SerializableImage extends SerializableFile implements
		TransfertImage {
	private static final long serialVersionUID = 4786344613415239528L;

	public SerializableImage() {
		super();
	}

	public SerializableImage(byte[] bytes) {
		super(bytes);
	}

	public SerializableImage(File file) throws IOException {
		super(file);
	}

	@Override
	public BufferedImage restoreImage() throws IOException {
		return ImageIO.read(new ByteArrayInputStream(file));
	}
}
