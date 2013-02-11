package net.alteiar.campaign.player.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelBattleParameters extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelBattleParameters() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblAfficherBarreDoutil = new JLabel("Afficher barre d'outil:");
		GridBagConstraints gbc_lblAfficherBarreDoutil = new GridBagConstraints();
		gbc_lblAfficherBarreDoutil.insets = new Insets(0, 0, 0, 5);
		gbc_lblAfficherBarreDoutil.gridx = 0;
		gbc_lblAfficherBarreDoutil.gridy = 0;
		add(lblAfficherBarreDoutil, gbc_lblAfficherBarreDoutil);

		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 0;
		add(checkBox, gbc_checkBox);
	}

}
