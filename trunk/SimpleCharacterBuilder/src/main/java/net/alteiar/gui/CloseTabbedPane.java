package net.alteiar.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CloseTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	@Override
	public void addTab(String title, Component component) {
		super.addTab(title, component);

		setTabComponentAt(getTabCount() - 1, new UiTitle(title));
	}

	@Override
	public void setTitleAt(int index, String title) {
		super.setTitleAt(index, title);
		((UiTitle) getTabComponentAt(index)).setTitle(title);
	}

	private class UiClose extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		private Color background;

		public UiClose() {
			setPreferredSize(new Dimension(14, 14));
			this.background = Color.WHITE;
			addMouseListener(this);
		}

		@Override
		public void paint(Graphics g) {
			if ((g instanceof Graphics2D)) {
				Graphics2D g2 = (Graphics2D) g;

				g2.setStroke(new BasicStroke(5.0F, 1, 1));
				g2.setColor(Color.BLACK);
				g2.drawLine(4, 12, 12, 4);
				g2.drawLine(12, 12, 4, 4);

				g2.setColor(this.background);
				g2.setStroke(new BasicStroke(3.0F, 1, 1));
				g2.drawLine(4, 12, 12, 4);
				g2.drawLine(12, 12, 4, 4);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Integer selected = Integer.valueOf(CloseTabbedPane.this
					.getSelectedIndex());
			if (selected.intValue() >= 0)
				CloseTabbedPane.this.removeTabAt(selected.intValue());
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			this.background = Color.RED;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			this.background = Color.WHITE;
		}
	}

	private class UiTitle extends JPanel {
		private static final long serialVersionUID = 1L;
		private final JLabel lblTitle;

		public UiTitle(String title) {
			this.lblTitle = new JLabel(title);
			CloseTabbedPane.UiClose close = new CloseTabbedPane.UiClose();

			setOpaque(false);
			setLayout(new GridBagLayout());
			add(this.lblTitle);
			add(close);
		}

		public void setTitle(String title) {
			this.lblTitle.setText(title);
		}
	}
}
