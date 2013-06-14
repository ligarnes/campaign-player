package net.alteiar.campaign.player.gui.centerViews.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class PanelSettings extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSettings() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panelNetwork = new PanelNetworkInformation();
		GridBagConstraints gbc_panelNetwork = new GridBagConstraints();
		gbc_panelNetwork.insets = new Insets(0, 0, 0, 5);
		gbc_panelNetwork.fill = GridBagConstraints.BOTH;
		gbc_panelNetwork.gridx = 0;
		gbc_panelNetwork.gridy = 0;
		add(panelNetwork, gbc_panelNetwork);

		JPanel panelPlayer = new PanelPlayerSettings();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(panelPlayer, gbc_panel);

	}

}
