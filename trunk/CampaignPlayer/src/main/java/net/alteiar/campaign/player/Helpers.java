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
package net.alteiar.campaign.player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.alteiar.shared.ExceptionTool;
import net.alteiar.shared.ImageUtil;

/**
 * @author Cody Stoutenburg
 * 
 */
public class Helpers {

	private static GlobalProperties globalProperties = new GlobalProperties();

	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	public static final String PATH_ICONS = "./ressources/icons/";
	public static final String PATH_TEXTURE = "./ressources/texture/";
	public static final String PATH_PROPERTIES = "./ressources/data/";

	public static final String PATH_PLUGIN = "./ressources/plugin/";

	public static final String APP_ICON = "app_icons.png";

	public static final String PATH_GLOBAL = "./ressources/global/";

	public static File getGlobalDirectory() {
		return new File(PATH_GLOBAL);
	}

	public static String getPathIcons(String name) {
		return PATH_ICONS + name;
	}

	public static String getPathTexture(String name) {
		return PATH_TEXTURE + name;
	}

	public static BufferedImage getImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			ExceptionTool.showError(e, "Impossible de trouver l'image " + path);
		}
		return img;
	}

	public static BufferedImage getImage(String name, Integer width,
			Integer height) {
		BufferedImage img = getImage(name);
		if (img != null) {
			img = ImageUtil.resizeImage(img, width, height);
		}
		return img;
	}

	public static ImageIcon getIcon(String name) {
		return new ImageIcon(getImage(getPathIcons(name)));
	}

	public static ImageIcon getIcon(String name, Integer width, Integer height) {
		return new ImageIcon(getImage(getPathIcons(name), width, height));
	}

	public static String getPathProperties(String name) {
		return PATH_PROPERTIES + name;
	}

	public static GlobalProperties getGlobalProperties() {
		return globalProperties;
	}
}
