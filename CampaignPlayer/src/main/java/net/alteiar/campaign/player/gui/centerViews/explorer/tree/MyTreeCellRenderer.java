package net.alteiar.campaign.player.gui.centerViews.explorer.tree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.alteiar.campaign.player.gui.centerViews.explorer.ExplorerIconUtils;
import net.alteiar.campaign.player.gui.centerViews.explorer.PanelDocument;
import net.alteiar.documents.BeanDocument;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;

	private final PanelDocument panel;

	public MyTreeCellRenderer() {
		panel = new PanelDocument();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		DocumentNode node = (DocumentNode) value;

		Icon icon = ExplorerIconUtils.getDirClosedIcon(32, 32);
		if (expanded) {
			icon = ExplorerIconUtils.getDirOpenIcon(32, 32);
		}
		if (leaf && !node.getAllowsChildren()) {
			icon = ExplorerIconUtils.getFileIcon(
					(BeanDocument) node.getUserObject(), 32, 32);
		}
		panel.setIcon(icon);
		panel.setBean(node.getUserObject());

		if (sel) {
			panel.setOpaque(true);
		} else {
			panel.setOpaque(false);
		}

		return panel;
	}
}
