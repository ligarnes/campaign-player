package net.alteiar.campaign.player.gui.documents;

import javax.swing.JPanel;

import net.alteiar.documents.BeanDocument;

public abstract class PanelViewDocument extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelViewDocument() {
	}

	public abstract void setDocument(BeanDocument document);
}
