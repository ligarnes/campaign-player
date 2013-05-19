package pathfinder.gui.document.builder.spell;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pathfinder.bean.spell.Spell;
import pathfinder.bean.spell.filter.SpellLevelComparator;

public class PanelSpellListSource extends JPanel {
	private static final long serialVersionUID = 1L;

	private static SpellListTransferHandler handler = new SpellListTransferHandler();

	private final SpellListModel modelRenderer;

	public PanelSpellListSource(String classe, List<Spell> spells) {
		Collections.sort(spells, new SpellLevelComparator(classe));

		JList<Spell> list = new JList<Spell>();

		modelRenderer = new SpellListModel(classe, spells);
		list.setModel(modelRenderer);
		list.setCellRenderer(modelRenderer);

		list.setTransferHandler(handler);
		list.setDragEnabled(true);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
	}

	public PanelSpellListSource() {
		this("", new ArrayList<Spell>());
	}

	public void setSpells(String classe, List<Spell> spells) {
		modelRenderer.setSpells(classe, spells);
		this.revalidate();
		this.repaint();
	}
}
