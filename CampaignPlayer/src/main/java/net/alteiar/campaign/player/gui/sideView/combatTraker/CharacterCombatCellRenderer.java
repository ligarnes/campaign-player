package net.alteiar.campaign.player.gui.sideView.combatTraker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;

import net.alteiar.combatTraker.CombatTrackerUnit;
import net.alteiar.combatTraker.CombatTraker;

public class CharacterCombatCellRenderer implements
		ListCellRenderer<CombatTrackerUnit> {

	private final CombatTraker traker;
	private final int maxWidth;

	public CharacterCombatCellRenderer(int maxWidth, CombatTraker traker) {
		this.maxWidth = maxWidth;
		this.traker = traker;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends CombatTrackerUnit> list, CombatTrackerUnit unit,
			int index, boolean isSelected, boolean cellHasFocus) {

		boolean currentTurn = (index == traker.getCurrentUnit());
		PanelCharacterCombat pane = new PanelCharacterCombat(maxWidth - 4,
				unit, currentTurn);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		if (index == 0) {
			panel.add(new JSeparator(JSeparator.HORIZONTAL));
		}
		panel.add(pane);

		if (isSelected) {
			pane.setBackground(Color.GRAY);
		}
		panel.add(new JSeparator(JSeparator.HORIZONTAL));

		return panel;
	}
}
