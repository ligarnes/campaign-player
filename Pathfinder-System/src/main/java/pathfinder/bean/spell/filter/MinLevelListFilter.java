package pathfinder.bean.spell.filter;

import pathfinder.bean.spell.Spell;

public class MinLevelListFilter extends ClassesListFilter {

	private final Integer minLevel;

	public MinLevelListFilter(String className, Integer minLevel) {
		super(className);
		this.minLevel = minLevel;
	}

	@Override
	public Boolean accept(Spell spell) {
		Integer level = spell.getLevel(getClassName());
		return level != null ? level >= minLevel : false;
	}
}
