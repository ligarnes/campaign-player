package pathfinder.gui.document.builder.spell;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import pathfinder.bean.spell.Spell;
import pathfinder.bean.spell.filter.SpellLevelComparator;

public class SpellListModel extends AbstractListModel implements ListModel,
		ListCellRenderer {
	private static final long serialVersionUID = 1L;

	private String classe;
	private TreeSet<Spell> spells;

	private ArrayList<Spell> values;

	public SpellListModel(String classe, List<Spell> spells) {
		this.classe = classe;
		this.spells = new TreeSet<Spell>(new SpellLevelComparator(classe));
		this.spells.addAll(spells);
		values = new ArrayList<Spell>();
		refresh();
	}

	public Set<Spell> getSpells() {
		return spells;
	}

	public void setSpells(String classe, List<Spell> spells) {
		this.classe = classe;
		this.spells = new TreeSet<Spell>(new SpellLevelComparator(classe));
		this.spells.addAll(spells);
		values = new ArrayList<Spell>();
		refresh();
		this.fireIntervalRemoved(this, 0, getSize());
		this.fireContentsChanged(this, 0, getSize());
	}

	public void addSpell(Spell spell) {
		this.spells.add(spell);
		refresh();
		this.fireIntervalAdded(this, 0, getSize());
		this.fireContentsChanged(this, 0, getSize());
	}

	public void removeSpell(Spell spell) {
		this.spells.remove(spell);
		refresh();
		this.fireIntervalRemoved(this, 0, getSize());
		this.fireContentsChanged(this, 0, getSize());
	}

	private void refresh() {
		values.clear();

		// TODO FIXME
		if (spells.isEmpty()) {
			values.add(null);
			return;
		}

		Integer previousLevel = -1;
		for (Spell spell : spells) {
			Integer currentLevel = spell.getLevel(classe);

			if (!currentLevel.equals(previousLevel)) {
				values.add(null);
				previousLevel = currentLevel;
			}
			values.add(spell);
		}
	}

	@Override
	public int getSize() {
		return values.size();
	}

	@Override
	public Spell getElementAt(int index) {
		return values.get(index);
	}

	protected JLabel buildSpell(Spell spell, JList list, boolean isSelected) {
		JLabel label = new JLabel(spell.getName());
		label.setOpaque(true);

		label.setBackground(isSelected ? list.getSelectionBackground() : list
				.getBackground());

		label.setPreferredSize(new Dimension(200, 20));
		return label;
	}

	protected JLabel buildTitle(String title) {
		JLabel label = new JLabel(title);
		label.setOpaque(true);

		label.setPreferredSize(new Dimension(200, 30));
		label.setBackground(Color.DARK_GRAY);
		return label;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		JLabel label = null;
		if (value != null) {
			label = buildSpell((Spell) value, list, isSelected);
		} else if ((index + 1) < getSize()) {
			Spell spell = getElementAt(index + 1);
			label = buildTitle("Niveau " + spell.getLevel(classe));
		} else {
			label = buildTitle("Aucun sort");
		}

		return label;
	}
}
