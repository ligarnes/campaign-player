package net.alteiar.campaign.player.gui.centerViews.map.element;

import javax.swing.JPanel;

import net.alteiar.dialog.PanelOkCancel;
import net.alteiar.map.elements.MapElement;

public abstract class PanelMapElementEditor<E extends MapElement> extends
		JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	private final E mapElement;

	public PanelMapElementEditor(E mapElement) {
		this.mapElement = mapElement;
	}

	protected E getMapElement() {
		return mapElement;
	}

	public abstract void applyModification();
}
