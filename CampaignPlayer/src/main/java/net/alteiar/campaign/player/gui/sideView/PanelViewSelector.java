package net.alteiar.campaign.player.gui.sideView;

import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.ApplicationView;

public class PanelViewSelector extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelViewSelector(Collection<ApplicationView> views, int width) {
		int rows = (views.size() + 1) / 2;
		this.setLayout(new GridLayout(rows, 2));

		for (ApplicationView applicationView : views) {
			JButton btn = new JButton();
			btn.setAction(new ViewActionSelector(applicationView));
			this.add(btn);
		}
	}
}
