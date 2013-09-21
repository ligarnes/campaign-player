package pathfinder.actions;

import generic.bean.unit.Unit;

import java.util.ArrayList;
import java.util.Iterator;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;
import net.alteiar.beans.map.elements.IAction;
import net.alteiar.campaign.CampaignClient;
import pathfinder.gui.adapter.PathfinderCharacterCombatTraker;

public class AddToCombatTracker extends IAction {

	private final Unit character;

	public AddToCombatTracker(Unit character) {
		this.character = character;
	}

	@Override
	public String getName() {
		return "Ajouter au combat";
	}

	private boolean containCharacter() {
		ArrayList<CombatTrackerUnit> units = CampaignClient.getInstance()
				.getCombatTraker().getUnits();

		boolean contain = false;

		Iterator<CombatTrackerUnit> itt = units.iterator();

		while (itt.hasNext() && !contain) {
			PathfinderCharacterCombatTraker unit = (PathfinderCharacterCombatTraker) itt
					.next();
			if (unit.getCharacterId().equals(character.getId())) {
				contain = true;
			}
		}

		return contain;
	}

	@Override
	public Boolean canDoAction() {
		return !containCharacter();
	}

	@Override
	public void doAction(int xOnScreen, int yOnScreen) throws Exception {
		PathfinderCharacterCombatTraker combat = new PathfinderCharacterCombatTraker(
				character.getId());
		CampaignClient.getInstance().addBean(combat);

		CampaignClient.getInstance().getCombatTraker().addUnit(combat.getId());
	}
}
