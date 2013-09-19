package net.alteiar.campaign.player.gui.centerViews.map;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.alteiar.beans.map.MapBean;

public class PanelAllMaps extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelAllMaps() {
		this.setLayout(new BorderLayout());
	}

	public void setMap(MapBean map) {
		this.removeAll();
		this.add(new PanelGlobalMap(map), BorderLayout.CENTER);
	}
}
