package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class RescaleMapListener extends ActionMapListener {

	public RescaleMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener) {
		super(mapInfo, mapListener);
	}

	@Override
	public void mouseClicked(MapEvent event) {
		// find another way to exit
		mapListener.defaultListener();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		int amount = event.getWheelRotation();
		getMapEditableInfo().rescaleMap(
				getMapEditableInfo().getPixelSquare() + amount);
	}
}
