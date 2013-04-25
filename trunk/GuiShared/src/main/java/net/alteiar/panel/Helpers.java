package net.alteiar.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Helpers {

	public static ImageIcon getIconColor(Color color) {
		BufferedImage bi = new BufferedImage(50, 50,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, 50, 50);
		g2d.dispose();
		return new ImageIcon(bi);
	}

	public static ImageIcon getIconColor(Color color, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, width, height);
		g2d.dispose();
		return new ImageIcon(bi);
	}

}
