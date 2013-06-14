package net.alteiar.campaign.player.gui.centerViews.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelSettings extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSettings() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panelNetwork = new PanelNetworkInformation();
		GridBagConstraints gbc_panelNetwork = new GridBagConstraints();
		gbc_panelNetwork.fill = GridBagConstraints.BOTH;
		gbc_panelNetwork.gridx = 0;
		gbc_panelNetwork.gridy = 0;
		add(panelNetwork, gbc_panelNetwork);

	}

}
