package net.alteiar.campaign.player.gui.centerViews.explorer.mine;

import javax.swing.tree.DefaultMutableTreeNode;

import net.alteiar.documents.BeanBasicDocument;

public class DocumentNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;

	public DocumentNode() {
		super();
	}

	public DocumentNode(BeanBasicDocument userObject) {
		super(userObject);
	}

	public DocumentNode(BeanBasicDocument userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	@Override
	public void setUserObject(Object userObject) {
		if (userObject instanceof String) {
			setName((String) userObject);
		} else if (userObject instanceof BeanBasicDocument) {
			super.setUserObject(userObject);
		}
	}

	private void setName(String name) {
		if (getUserObject() != null) {
			getUserObject().setDocumentName(name);
		}
	}

	@Override
	public BeanBasicDocument getUserObject() {
		return (BeanBasicDocument) super.getUserObject();
	}

}
