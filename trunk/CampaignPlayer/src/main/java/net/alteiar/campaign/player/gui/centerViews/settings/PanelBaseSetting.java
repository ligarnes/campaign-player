package net.alteiar.campaign.player.gui.centerViews.settings;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class PanelBaseSetting extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelBaseSetting(String name) {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				name, TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}
}
