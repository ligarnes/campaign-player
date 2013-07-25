package net.alteiar.campaign.player.gui.sideView.combatTraker;

import java.io.Serializable;
import java.util.Comparator;

import net.alteiar.combatTraker.CombatTrackerUnit;

public class InitiativeComparator implements Comparator<CombatTrackerUnit>,
		Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(CombatTrackerUnit unit1, CombatTrackerUnit unit2) {
		return unit2.getInitiative() - unit1.getInitiative();
	}

}
