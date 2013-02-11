package net.alteiar.campaign.player.gui.settings;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PanelSettings extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JPanel panelChoice;
	private final JPanel panelSetting;

	public PanelSettings() {
		super(new BorderLayout());

		panelChoice = new JPanel();
		panelSetting = new PanelBattleParameters();

		this.add(panelChoice, BorderLayout.WEST);
		this.add(panelSetting, BorderLayout.CENTER);
	}
}
