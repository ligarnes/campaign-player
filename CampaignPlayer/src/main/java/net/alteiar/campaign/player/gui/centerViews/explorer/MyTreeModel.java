package net.alteiar.campaign.player.gui.centerViews.explorer;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class MyTreeModel implements TreeModel {

	private DefaultTreeModel fullModel;
	private DefaultTreeModel printModel;

	private void copyModel() {

	}

	@Override
	public Object getRoot() {
		return printModel.getRoot();
	}

	@Override
	public Object getChild(Object parent, int index) {
		return printModel.getChild(parent, index);
	}

	@Override
	public int getChildCount(Object parent) {
		return printModel.getChildCount(parent);
	}

	@Override
	public boolean isLeaf(Object node) {
		return printModel.isLeaf(node);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		printModel.valueForPathChanged(path, newValue);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return printModel.getIndexOfChild(parent, child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		printModel.addTreeModelListener(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		printModel.removeTreeModelListener(l);
	}

}
