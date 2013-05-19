package pathfinder.bean.spell;

import java.util.HashSet;
import java.util.Set;

import net.alteiar.documents.AuthorizationBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class DocumentSpellBook extends AuthorizationBean {
	private static final long serialVersionUID = 1L;

	@Element
	private String className;
	@ElementList
	private HashSet<UniqueID> spells;

	protected DocumentSpellBook() {

	}

	public DocumentSpellBook(String spellBookname, String className,
			Set<Spell> spells) {
		super(spellBookname);
		this.className = className;
		this.spells = new HashSet<UniqueID>();

		for (Spell spell : spells) {
			this.spells.add(spell.getId());
		}
	}

	public String getClasseName() {
		return className;
	}

	public void addSpell(Spell spell) {
		spells.add(spell.getId());
	}

	protected void setSpells(HashSet<UniqueID> spells) {
		this.spells = spells;
	}

	public HashSet<UniqueID> getSpells() {
		return this.spells;
	}
}
