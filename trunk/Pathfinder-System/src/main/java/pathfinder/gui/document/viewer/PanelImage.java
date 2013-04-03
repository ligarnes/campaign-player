package pathfinder.gui.document.viewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.alteiar.zoom.Zoomable;

public class PanelImage extends JPanel implements Zoomable {
	private static final long serialVersionUID = 1L;

	private final BufferedImage img;
	private double zoomFactor;

	public PanelImage(BufferedImage img) {
		this.img = img;

		zoomFactor = 1.0;

		this.setPreferredSize(computeDimension());
		this.setMaximumSize(computeDimension());
		this.setMinimumSize(computeDimension());
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img, 0, 0, (int) (img.getWidth() * zoomFactor),
				(int) (img.getHeight() * zoomFactor), null);
	}

	private Dimension computeDimension() {
		return new Dimension((int) (img.getWidth() * zoomFactor),
				(int) (img.getHeight() * zoomFactor));
	}

	@Override
	public void zoom(double value) {
		zoomFactor = value;
		this.setPreferredSize(computeDimension());
		this.setMaximumSize(computeDimension());
		this.setMinimumSize(computeDimension());

		this.revalidate();
		this.repaint();
	}

	@Override
	public Double getZoomFactor() {
		return zoomFactor;
	}
}
