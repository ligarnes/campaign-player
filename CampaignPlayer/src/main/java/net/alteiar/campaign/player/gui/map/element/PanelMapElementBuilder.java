package net.alteiar.campaign.player.gui.map.element;

import java.awt.Point;

import javax.swing.JPanel;

import net.alteiar.map.elements.MapElement;

public abstract class PanelMapElementBuilder extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelMapElementBuilder() {
		super();
	}

	/**
	 * 
	 * @return true if we can actually build an element of this kind
	 */
	public abstract Boolean isAvailable();

	/**
	 * 
	 * @return a name that describe what the panel build
	 */
	public abstract String getElementName();

	/**
	 * 
	 * @return a long description of what the panel build
	 */
	public abstract String getElementDescription();

	/**
	 * build the MapElement at the given position
	 * 
	 * @param position
	 * @return the MapElement created
	 */
	public abstract MapElement buildMapElement(Point position);
}
