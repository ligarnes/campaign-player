package net.alteiar.shared;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageUtil {

	public static Object HIGH_RESOLUTION = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
	public static Object NORMAL_RESOLUTION = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
	public static Object LOW_RESOLUTION = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;

	public static BufferedImage resizeImage(BufferedImage image, int width,
			int height) {
		return resizeImage(image, width, height, HIGH_RESOLUTION);
	}

	/**
	 * Don't use image.getScaledInstance() because it's to slow
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @param resolution
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage image, int width,
			int height, Object resolution) {
		int type = image.getType() == 0 ? 2 : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		// Don't care about time do the best you can
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, resolution);
		g.setComposite(AlphaComposite.Src);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
}
