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

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.alteiar.utils.file.SerializableFile;

import org.apache.log4j.Logger;

/**
 * @author Cody Stoutenburg
 * 
 */
public class SerializableImage extends SerializableFile implements
		TransfertImage {
	private transient BufferedImage image;

	public SerializableImage() {
		super();
	}

	public SerializableImage(File file) throws IOException {
		super(file);
	}

	@Override
	public BufferedImage restoreImage() throws IOException {
		if (image == null) {
			final MediaTracker tracker = new MediaTracker(new JPanel());

			DataInputStream datainputstream = new DataInputStream(
					new FileInputStream(getLocalFile()));

			byte bytes[] = new byte[datainputstream.available()];

			datainputstream.readFully(bytes);
			datainputstream.close();

			Image img = Toolkit.getDefaultToolkit().createImage(bytes);

			tracker.addImage(img, 1);
			try {
				tracker.waitForID(1);
				tracker.removeImage(img);

				image = new BufferedImage(img.getWidth(null),
						img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

				image.getGraphics().drawImage(img, 0, 0, null);
			} catch (InterruptedException e) {
				Logger.getLogger(getClass()).warn("Probleme lors de l'attente",
						e);
				image = ImageIO.read(getLocalFile());
			}
		}
		return image;
	}

	@Override
	public void clearCache() {
		this.image = null;
	}
}
