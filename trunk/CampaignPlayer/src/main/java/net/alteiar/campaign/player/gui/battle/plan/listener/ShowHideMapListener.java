package net.alteiar.campaign.player.gui.battle.plan.listener;

import java.awt.Point;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.battle.plan.MapEditableInfo;
import net.alteiar.campaign.player.gui.battle.plan.details.MapEvent;

public class ShowHideMapListener extends ActionMapListener {

	private final Point begin;
	private final MapEditableInfo mapInfo;
	private final Boolean isShow;

	public ShowHideMapListener(GlobalMapListener mapListener,
			MapEditableInfo mapInfo, Point begin, Boolean isShow) {
		super(mapListener);
		this.mapInfo = mapInfo;
		this.begin = begin;
		this.mapInfo.drawRectangleToMouse(begin);
		this.isShow = isShow;
	}

	@Override
	public void mouseClicked(MapEvent event) {

		if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			Point mapPosition = event.getMapPosition();
			int x = Math.min(begin.x, mapPosition.x);
			int y = Math.min(begin.y, mapPosition.y);
			int width = begin.x - mapPosition.x;
			int height = begin.y - mapPosition.y;
			if (width < 0)
				width = -width;
			if (height < 0)
				height = -height;

			if (isShow) {
				mapInfo.showRectangle(new Point(x, y), width, height);
			} else {
				mapInfo.hideRectangle(new Point(x, y), width, height);
			}
			this.mapInfo.stopDrawRectangle();
			mapListener.defaultListener();
		}
	}
}
