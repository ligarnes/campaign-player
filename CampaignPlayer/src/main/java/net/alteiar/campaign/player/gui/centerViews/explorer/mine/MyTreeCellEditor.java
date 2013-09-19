package net.alteiar.campaign.player.gui.centerViews.explorer.mine;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;

import sun.swing.DefaultLookup;

public class MyTreeCellEditor extends AbstractCellEditor implements
		TreeCellEditor {
	private static final long serialVersionUID = 1L;

	private final PanelDocumentEditable editor;

	private final Icon closedIcon;
	private final Icon openIcon;
	private final Icon leafIcon;

	public MyTreeCellEditor(JTree tree) {
		editor = new PanelDocumentEditable(this);

		closedIcon = DefaultLookup.getIcon(tree, tree.getUI(),
				"Tree.closedIcon");
		openIcon = DefaultLookup.getIcon(tree, tree.getUI(), "Tree.openIcon");
		leafIcon = DefaultLookup.getIcon(tree, tree.getUI(), "Tree.leafIcon");
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= 2;
		}
		return true;
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		DocumentNode node = (DocumentNode) value;

		Icon icon = closedIcon;
		if (expanded) {
			icon = openIcon;
		}
		if (leaf) {
			icon = leafIcon;
		}

		editor.setIcon(icon);
		editor.setBean(node.getUserObject());
		return editor;
	}

	@Override
	public boolean stopCellEditing() {
		boolean editionAccepted = !editor.getDocumentName().isEmpty();
		if (editionAccepted) {
			fireEditingStopped();
		}
		return editionAccepted;
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getDocumentName();
	}

}
