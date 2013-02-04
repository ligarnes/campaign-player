package net.alteiar.campaign.player.gui.tools.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.alteiar.campaign.player.Helpers;

public class PanelShowHideLeft extends JPanel {
	private static final long serialVersionUID = 1L;
	private static BufferedImage arrowHide;
	private static BufferedImage arrowShow;

	protected Boolean show;
	private final JPanel panelContent;
	private final JPanel panelTitle;

	public PanelShowHideLeft(JPanel panel) {

		if (arrowHide == null && arrowShow == null) {
			try {
				arrowHide = ImageIO.read(new File(Helpers
						.getPathIcons("Arrow_left.png")));
				arrowShow = ImageIO.read(new File(Helpers
						.getPathIcons("Arrow_down.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.setLayout(new BorderLayout());
		panelTitle = new PanelIcon();
		panelTitle.setOpaque(false);
		panelTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				reduce();
			}
		});

		panelContent = panel;
		show = true;
		this.add(panelTitle, BorderLayout.EAST);
		this.add(panelContent, BorderLayout.CENTER);

		// reduce();
		// reduce();
	}

	private void reduce() {
		show = !show;
		panelContent.setVisible(show);
		Dimension dim = this.getPreferredSize();
		dim.height = panelTitle.getHeight();
		if (show) {
			dim.width = panelTitle.getWidth() + panelContent.getWidth();
		} else {
			dim.width = panelTitle.getWidth();
		}

		this.setPreferredSize(dim);
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.revalidate();
		this.repaint();
	}

	private class PanelIcon extends JPanel {
		private static final long serialVersionUID = 1L;

		public PanelIcon() {
			this.setPreferredSize(new Dimension(25, 25));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			BufferedImage img = null;
			if (show) {
				img = arrowHide;
			} else {
				img = arrowShow;
			}
			g.drawImage(img, -5, -5, 35, 35, null);
			g.dispose();
		}
	}
}
