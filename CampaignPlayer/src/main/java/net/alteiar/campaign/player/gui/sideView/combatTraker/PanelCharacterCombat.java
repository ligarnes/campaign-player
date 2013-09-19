package net.alteiar.campaign.player.gui.sideView.combatTraker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;

public class PanelCharacterCombat extends JPanel {
	private static final long serialVersionUID = 1L;

	private final CombatTrackerUnit combatUnit;

	public PanelCharacterCombat(int maxWidth, CombatTrackerUnit character,
			Boolean isSelected) {
		combatUnit = character;

		setBorder(new EmptyBorder(4, 4, 4, 4));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 56, 64, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		JLabel lblName = new JLabel(getCharacter().getName(true));

		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		this.add(lblName, gbc_lblName);

		JLabel lblInit = new JLabel(getCharacter().getInitiative() + "");
		GridBagConstraints gbc_lblInit = new GridBagConstraints();
		gbc_lblInit.anchor = GridBagConstraints.EAST;
		gbc_lblInit.gridx = 1;
		gbc_lblInit.gridy = 0;
		add(lblInit, gbc_lblInit);

		if (isSelected) {
			this.setBackground(Color.GREEN);
		} else {
			this.setBackground(UIManager.getColor("Panel.background"));
		}

		this.setPreferredSize(new Dimension(maxWidth, 30));
	}

	public CombatTrackerUnit getCharacter() {
		return combatUnit;
	}
}