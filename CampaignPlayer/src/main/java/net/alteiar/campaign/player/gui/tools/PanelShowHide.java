package net.alteiar.campaign.player.gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.Helpers;

public class PanelShowHide extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color TITLE_BACKGROUND_COLOR = new Color(173, 173, 173);

	private static BufferedImage arrowHide;
	private static BufferedImage arrowShow;

	protected Boolean show;
	private final JPanel panelContent;
	private final JPanel panelTitle;

	public PanelShowHide(String title, JPanel panel) {

		if (arrowHide == null && arrowShow == null) {
			try {
				arrowHide = ImageIO.read(new File(Helpers
						.getPathIcons("Arrow_down.png")));
				arrowShow = ImageIO.read(new File(Helpers
						.getPathIcons("Arrow_left.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.setLayout(new BorderLayout());
		panelTitle = new PanelTitle(title);

		panelContent = panel;
		show = true;
		this.add(panelTitle, BorderLayout.NORTH);
		this.add(panelContent, BorderLayout.CENTER);
	}

	private void reduce() {
		show = !show;
		panelContent.setVisible(show);
		Dimension dim = this.getPreferredSize();
		dim.width = panelContent.getWidth();
		if (show) {
			dim.height = panelTitle.getHeight() + panelContent.getHeight();
		} else {
			dim.height = panelTitle.getHeight();
		}
		this.setPreferredSize(dim);
		this.revalidate();
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

	private class PanelTitle extends JPanel {
		private static final long serialVersionUID = 1L;

		/**
		 * Create the panel.
		 */
		public PanelTitle(String title) {
			this.setPreferredSize(new Dimension(this.getWidth(), 25));
			setBackground(TITLE_BACKGROUND_COLOR);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
			gridBagLayout.rowHeights = new int[] { 0, 0 };
			gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
					Double.MIN_VALUE };
			gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			setLayout(gridBagLayout);

			JLabel lblTitle = new JLabel(" " + title);
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.anchor = GridBagConstraints.WEST;
			gbc_lblTitle.insets = new Insets(0, 0, 0, 5);
			gbc_lblTitle.gridx = 1;
			gbc_lblTitle.gridy = 0;
			add(lblTitle, gbc_lblTitle);

			JPanel panel = new PanelIcon();
			panel.setOpaque(false);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			add(panel, gbc_panel);

			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					reduce();
				}
			});
		}
	}
}
