package shadowrun.actions;

import java.util.ArrayList;
import java.util.Iterator;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;
import net.alteiar.beans.map.elements.IAction;
import net.alteiar.campaign.CampaignClient;
import shadowrun.bean.unit.ShadowrunCharacter;
import shadowrun.gui.adapter.ShadowrunCharacterCombatTraker;

public class AddToCombatTracker extends IAction {

	private final ShadowrunCharacter character;

	public AddToCombatTracker(ShadowrunCharacter character) {
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
			ShadowrunCharacterCombatTraker unit = (ShadowrunCharacterCombatTraker) itt
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
		ShadowrunCharacterCombatTraker combat = new ShadowrunCharacterCombatTraker(
				character.getId());
		CampaignClient.getInstance().addBean(combat);

		CampaignClient.getInstance().getCombatTraker().addUnit(combat.getId());
	}
}
