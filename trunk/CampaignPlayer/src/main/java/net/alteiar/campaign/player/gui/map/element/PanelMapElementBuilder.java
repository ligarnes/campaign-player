package net.alteiar.campaign.player.gui.map.element;

import java.awt.Point;

import javax.swing.JPanel;

import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;

public abstract class PanelMapElementBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelMapElementBuilder() {
		super();
	}

	public abstract Boolean isAvailable();

	public abstract String getElementName();

	public abstract String getElementDescription();

	public abstract DocumentMapElementBuilder buildMapElement(MapClient<?> map,
			Point position);
}
