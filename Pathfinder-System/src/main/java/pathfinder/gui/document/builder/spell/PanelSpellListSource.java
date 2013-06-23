package pathfinder.gui.document.builder.spell;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pathfinder.bean.spell.Spell;
import pathfinder.gui.document.builder.spell.dragndrop.SpellListTransferHandler;

public class PanelSpellListSource extends JPanel {
	private static final long serialVersionUID = 1L;

	private final SpellListTransferHandler handler;

	private final SpellListModel modelRenderer;

	public PanelSpellListSource(String classe, List<Spell> spells) {
		JList<Spell> list = new JList<Spell>();

		handler = new SpellListTransferHandler();

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

	public Set<Spell> getSpells() {
		return modelRenderer.getSpells();
	}
}
