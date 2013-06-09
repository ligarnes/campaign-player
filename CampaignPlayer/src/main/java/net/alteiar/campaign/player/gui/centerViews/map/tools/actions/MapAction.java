package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import javax.swing.AbstractAction;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public abstract class MapAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final MapEditableInfo info;

	public MapAction(MapEditableInfo info) {
		this.info = info;
	}

	public MapEditableInfo getMapInfo() {
		return info;
	}
}
