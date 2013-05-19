package pathfinder.bean.spell;

import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.tools.ListFilter;

public class SpellManager {

	private static SpellManager INSTANCE = new SpellManager();

	public static SpellManager getInstance() {
		return INSTANCE;
	}

	private final ArrayList<Spell> allSpells;
	private final HashSet<String> allClasses;

	private SpellManager() {
		allSpells = CampaignClient.getInstance().loadLocalBean(Spell.class);

		allClasses = new HashSet<String>();
		for (Spell spell : allSpells) {
			allClasses.addAll(spell.getClassesLevel().keySet());
		}
	}

	public ArrayList<Spell> getSpells() {
		return allSpells;
	}

	public HashSet<String> getClasses() {
		return allClasses;
	}

	public ArrayList<Spell> getSpells(ListFilter<Spell> constraint) {
		ArrayList<Spell> result = new ArrayList<Spell>();
		ListFilter.filterList(allSpells, result, constraint);
		return result;
	}
}
