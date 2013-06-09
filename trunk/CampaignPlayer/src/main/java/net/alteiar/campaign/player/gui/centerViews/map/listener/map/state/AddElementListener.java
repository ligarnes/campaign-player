package net.alteiar.campaign.player.gui.centerViews.map.listener.map.state;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelCreateMapElement;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.ActionMapListener;

public class AddElementListener extends ActionMapListener {
	public AddElementListener(MapEditableInfo mapInfo) {
		super(mapInfo);
	}

	@Override
	public void mouseClicked(MapEvent event) {
		PanelCreateMapElement.createMapElement(getMapInfo().getMap(), event);

		getMapInfo().getMapListener().defaultListener();
	}

	@Override
	public void cancelTask() {
		// nothing to do
	}
}