package net.alteiar.campaign.player.gui.centerViews;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelCenter extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelCenter() {
		this.setLayout(new BorderLayout());
	}

	public void updateSelectedView(final ApplicationView appView) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JComponent viewPanel = appView.getView();
				if (viewPanel != null) {

					removeAll();
					add(viewPanel, BorderLayout.CENTER);
					revalidate();
					repaint();
				}
			}
		});
	}
}
