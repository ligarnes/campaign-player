package net.alteiar.campaign.player.gui.battle.plan.listener;

import java.awt.Point;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.plan.details.MapEvent;

public class RescaleMapListener extends ActionMapListener {

	private final Point begin;
	private final MapEditableInfo mapInfo;

	public RescaleMapListener(GlobalMapListener mapListener,
			MapEditableInfo mapInfo, Point begin) {
		super(mapListener);
		this.mapInfo = mapInfo;
		this.begin = begin;
		this.mapInfo.drawLineToMouse(begin);
	}

	@Override
	public void mouseClicked(MapEvent event) {
		if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			mapInfo.rescaleMap(begin, event.getMapPosition());
			this.mapInfo.stopDrawLineToMouse();
			mapListener.defaultListener();
		}
	}
}
