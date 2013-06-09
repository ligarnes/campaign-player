package net.alteiar.campaign.player.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.alteiar.campaign.player.gui.centerViews.PanelCenter;
import net.alteiar.campaign.player.gui.sideView.PanelWest;

public class PanelGlobal extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelGlobal() {
		PanelCenter panelCenter = UiManager.getInstance().getCenterPanel();
		PanelWest west = UiManager.getInstance().getWestPanel();

		JScrollPane scroll = new JScrollPane(west);

		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.WEST);
		this.add(panelCenter, BorderLayout.CENTER);
	}
}
