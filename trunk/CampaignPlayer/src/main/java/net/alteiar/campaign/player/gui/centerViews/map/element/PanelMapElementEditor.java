package net.alteiar.campaign.player.gui.centerViews.map.element;

import javax.swing.JPanel;

import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.dialog.PanelOkCancel;

public abstract class PanelMapElementEditor extends JPanel implements
		PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private MapElement mapElement;

	public PanelMapElementEditor() {
	}

	public void setMapElement(MapElement mapElement) {
		this.mapElement = mapElement;
		mapElementChanged();
	}

	protected abstract void mapElementChanged();

	protected <E extends MapElement> E getMapElement() {
		return (E) mapElement;
	}

	public abstract void applyModification();
}
