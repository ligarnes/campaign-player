package net.alteiar.campaign.player.infos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.shared.ImageUtil;

public class HelpersImages {

	public static BufferedImage getImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			ExceptionTool.showWarning(e, "Impossible de trouver l'image "
					+ path);
			Helpers.log.error("impossible de trouver l'image " + path, e);
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
		return new ImageIcon(getImage(HelpersPath.getPathIcons(name)));
	}

	public static ImageIcon getIcon(String name, Integer width, Integer height) {
		return new ImageIcon(getImage(HelpersPath.getPathIcons(name), width,
				height));
	}

}
