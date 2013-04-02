package net.alteiar.campaign.player.gui.documents;

import javax.swing.JPanel;

public abstract class PanelDocumentBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelDocumentBuilder() {
		super();
	}

	public abstract String getDocumentName();

	public abstract String getDocumentDescription();

	public abstract void buildDocument();

	public Boolean isDataValid() {
		return true;
	}

	public String getInvalidMessage() {
		return "";
	}
}
