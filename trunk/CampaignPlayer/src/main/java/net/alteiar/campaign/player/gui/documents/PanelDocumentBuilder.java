package net.alteiar.campaign.player.gui.documents;

import java.awt.Point;

import javax.swing.JPanel;

import net.alteiar.map.elements.MapElement;

public abstract class PanelDocumentBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelDocumentBuilder() {
		super();
	}

	public abstract String getElementName();

	public abstract String getElementDescription();

	public abstract MapElement buildMapElement(Point position);
}
