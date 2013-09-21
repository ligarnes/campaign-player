package pathfinder.bean.spell.filter;

import pathfinder.bean.spell.Spell;

public class MaxLevelListFilter extends ClassesListFilter {

	private final Integer maxLevel;

	public MaxLevelListFilter(String className, Integer maxLevel) {
		super(className);
		this.maxLevel = maxLevel;
	}

	@Override
	public Boolean accept(Spell spell) {
		Integer level = spell.getLevel(getClassName());
		return level != null ? level <= maxLevel : false;
	}
}
