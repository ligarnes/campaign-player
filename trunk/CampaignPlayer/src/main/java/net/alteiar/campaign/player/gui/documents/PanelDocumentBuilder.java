package net.alteiar.campaign.player.gui.documents;

import javax.swing.JPanel;

import net.alteiar.newversion.shared.bean.BasicBean;

public abstract class PanelDocumentBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelDocumentBuilder() {
		super();
	}

	public abstract String getDocumentBuilderName();

	public abstract String getDocumentBuilderDescription();

	public abstract void reset();

	public abstract String getDocumentName();

	public abstract BasicBean buildDocument();

	public abstract String getDocumentType();

	public Boolean isDataValid() {
		return true;
	}

	public String getInvalidMessage() {
		return "";
	}
}
