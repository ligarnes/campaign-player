package pathfinder.bean.spell.filter;

import net.alteiar.tools.ListFilter;
import pathfinder.bean.spell.Spell;

public class ClassesListFilter extends ListFilter<Spell> {

	private final String className;

	public ClassesListFilter(String className) {
		this.className = className;
	}

	protected String getClassName() {
		return this.className;
	}

	@Override
	public Boolean accept(Spell spell) {
		return spell.belongTo(className);
	}

}
