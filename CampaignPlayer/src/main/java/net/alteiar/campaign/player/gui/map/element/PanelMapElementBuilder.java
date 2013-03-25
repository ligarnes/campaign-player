package net.alteiar.campaign.player.gui.map.element;

import java.awt.Point;

import javax.swing.JPanel;

import net.alteiar.map.elements.MapElement;

public abstract class PanelMapElementBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelMapElementBuilder() {
		super();
	}

	public abstract Boolean isAvailable();

	public abstract String getElementName();

	public abstract String getElementDescription();

	public abstract MapElement buildMapElement(Point position);
}
