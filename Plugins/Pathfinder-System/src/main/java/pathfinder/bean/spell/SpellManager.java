package pathfinder.bean.spell;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.tools.ListFilter;

public class SpellManager {

	private static final SpellManager INSTANCE = new SpellManager();

	public static SpellManager getInstance() {
		return INSTANCE;
	}

	private final ArrayList<Spell> allSpells;
	private final HashSet<String> allClasses;

	private SpellManager() {
		allSpells = new ArrayList<Spell>();
		allClasses = new HashSet<String>();

		load();
	}

	/**
	 * loading can be long but don't need it instantly
	 */
	private void load() {
		System.out.println("plugin beans: "
				+ PluginSystem.getInstance().getPluginBeans());

		// System.out.println();
		allSpells.addAll(CampaignClient.loadDirectory(new File(PluginSystem
				.getInstance().getPluginBeans()), Spell.class));

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
