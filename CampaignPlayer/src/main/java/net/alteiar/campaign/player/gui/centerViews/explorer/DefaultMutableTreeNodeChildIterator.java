package net.alteiar.campaign.player.gui.centerViews.explorer;

import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

class DefaultMutableTreeNodeChildIterator implements
		Iterator<DefaultMutableTreeNode> {

	private final DefaultMutableTreeNode parent;
	private int currentIdx;

	public DefaultMutableTreeNodeChildIterator(DefaultMutableTreeNode parent) {
		this.parent = parent;
		currentIdx = 0;
	}

	@Override
	public boolean hasNext() {
		return currentIdx < parent.getChildCount();
	}

	@Override
	public DefaultMutableTreeNode next() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent
				.getChildAt(currentIdx);
		currentIdx++;
		return node;
	}

	@Override
	public void remove() {
		currentIdx--;
		parent.remove(currentIdx);
	}
}