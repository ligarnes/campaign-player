package net.alteiar.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage background;

	public MyPanel(BufferedImage background) {
		super();
		this.setOpaque(false);
		initialize(background);
	}

	public MyPanel(BufferedImage background, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.setOpaque(false);
		initialize(background);
	}

	public MyPanel(BufferedImage background, LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.setOpaque(false);
		initialize(background);
	}

	public MyPanel(BufferedImage background, LayoutManager layout) {
		super(layout);
		this.setOpaque(false);
		initialize(background);
	}

	private void initialize(BufferedImage background) {
		this.background = background;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Create a texture paint from the buffered image
		Rectangle r = new Rectangle(0, 0, background.getWidth(),
				background.getHeight());
		TexturePaint tp = new TexturePaint(background, r);

		// Add the texture paint to the graphics context.
		g2.setPaint(tp);

		// Create and render a rectangle filled with the texture.
		g2.fillRect(0, 0, getWidth(), getHeight());

		super.paint(g);
	}
}
