package pathfinder.bean.spell;

import java.util.ArrayList;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public class SpellBook extends BasicBean {
	private static final long serialVersionUID = 1L;

	private ArrayList<UniqueID> spells;

	private String className;

	protected SpellBook() {

	}

	public SpellBook(String className) {
		this.className = className;
		spells = new ArrayList<UniqueID>();
	}

	public void addSpell(Spell spell) {
		spells.add(spell.getId());
	}

	protected void setSpells(ArrayList<UniqueID> spells) {
		this.spells = spells;
	}
}
