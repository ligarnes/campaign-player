package net.alteiar.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelShowHide extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color TITLE_BACKGROUND_COLOR = new Color(173, 173, 173);

	private static ImageIcon arrowHide;
	private static ImageIcon arrowShow;

	protected Boolean show;
	private final JPanel panelContent;
	private final JPanel panelTitle;

	public PanelShowHide(String title, JPanel panel) {
		if (arrowHide == null && arrowShow == null) {
			arrowHide = new ImageIcon(
					PanelShowHide.class.getResource("/icons/Arrow_down.png"));

			arrowShow = new ImageIcon(
					PanelShowHide.class.getResource("/icons/Arrow_left.png"));
		}

		this.setLayout(new BorderLayout());
		panelTitle = new PanelTitle(title);
		panelTitle.setMinimumSize(new Dimension(50, 25));
		panelTitle.setPreferredSize(new Dimension(50, 25));

		panelContent = panel;

		show = true;
		this.add(panelTitle, BorderLayout.NORTH);
		this.add(panelContent, BorderLayout.CENTER);
	}

	public PanelShowHide(String title, JPanel panel, int width) {
		if (arrowHide == null && arrowShow == null) {
			arrowHide = new ImageIcon(
					PanelShowHide.class.getResource("/icons/Arrow_down.png"));

			arrowShow = new ImageIcon(
					PanelShowHide.class.getResource("/icons/Arrow_left.png"));
		}

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		panelTitle = new PanelTitle(title);
		panelTitle.setMinimumSize(new Dimension(width, 25));
		panelTitle.setPreferredSize(new Dimension(width, 25));

		panelContent = panel;

		show = true;
		this.add(panelTitle);
		this.add(panelContent);
	}

	private void reduce() {
		show = !show;
		panelContent.setVisible(show);

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

			ImageIcon img = null;
			if (show) {
				img = arrowHide;
			} else {
				img = arrowShow;
			}
			g.drawImage(img.getImage(), -5, -5, 35, 35, null);
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
