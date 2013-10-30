package generic.gui.mapElement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class BarElement {
	private final Color maxColor;
	private final Color minColor;

	public BarElement(Color minColor, Color maxColor) {
		this.minColor = minColor;
		this.maxColor = maxColor;
	}

	public void drawBar(Graphics2D g, int x, int y, int height, int width,
			Float ratio) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		if (ratio > 0.0) {
			g2.setColor(generateColor(ratio));
			g2.fillRect(x, y, (int) (width * ratio), height);
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, width, height);

		g2.dispose();
	}

	private Color generateColor(Float ratio) {
		int red = getRatioValue(maxColor.getRed(), minColor.getRed(), ratio);
		int green = getRatioValue(maxColor.getGreen(), minColor.getGreen(),
				ratio);
		int blue = getRatioValue(maxColor.getBlue(), minColor.getBlue(), ratio);

		return new Color(red, green, blue);
	}

	private int getRatioValue(int max, int min, Float ratio) {
		int val = (int) ((max - min) * ratio);

		if (val < 0) {
			val = 255 + val;
		}

		return val;
	}
}
