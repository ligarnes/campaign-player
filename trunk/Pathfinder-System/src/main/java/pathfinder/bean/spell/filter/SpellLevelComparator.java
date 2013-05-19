package pathfinder.bean.spell.filter;

import java.util.Comparator;

import pathfinder.bean.spell.Spell;

public class SpellLevelComparator implements Comparator<Spell> {

	private final String classe;

	public SpellLevelComparator(String classe) {
		this.classe = classe;
	}

	@Override
	public int compare(Spell o1, Spell o2) {
		return o1.getLevel(classe) - o2.getLevel(classe);
	}
}
