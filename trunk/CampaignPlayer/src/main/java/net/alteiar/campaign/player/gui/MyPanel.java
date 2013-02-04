package net.alteiar.campaign.player.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.alteiar.campaign.player.Helpers;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage background;

	public MyPanel() {
		super();
		this.setOpaque(false);
		initialize();
	}

	public MyPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.setOpaque(false);
		initialize();
	}

	public MyPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.setOpaque(false);
		initialize();
	}

	public MyPanel(LayoutManager layout) {
		super(layout);
		this.setOpaque(false);
		initialize();
	}

	private void initialize() {
		background = Helpers.getImage(Helpers.getPathTexture("parchemin.jpg"),
				500, 500);
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
