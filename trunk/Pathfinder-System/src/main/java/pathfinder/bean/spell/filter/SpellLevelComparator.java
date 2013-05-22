package pathfinder.bean.spell.filter;

import java.util.Comparator;

import pathfinder.bean.spell.Spell;

public class SpellLevelComparator implements Comparator<Spell> {

	private final String classe;

	public SpellLevelComparator(String classe) {
		this.classe = classe;
	}

	protected int diffLevel(Spell o1, Spell o2) {
		return o1.getLevel(classe) - o2.getLevel(classe);
	}

	protected int diffName(Spell o1, Spell o2) {
		return o1.getName().compareTo(o2.getName());
	}

	@Override
	public int compare(Spell o1, Spell o2) {
		int diff = diffLevel(o1, o2);
		if (diff == 0) {
			diff = diffName(o1, o2);
		}
		return diff;
	}
}
