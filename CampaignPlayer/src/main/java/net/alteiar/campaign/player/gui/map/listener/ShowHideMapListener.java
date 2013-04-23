package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.mouse.MouseDrawable;
import net.alteiar.campaign.player.gui.map.drawable.mouse.RectangleToMouse;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class ShowHideMapListener extends ActionMapListener {

	private final Point begin;
	private final Boolean isShow;
	private final MouseDrawable draw;

	public ShowHideMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, Point begin, Boolean isShow) {
		super(mapInfo, mapListener);
		this.begin = begin;

		draw = new RectangleToMouse(mapInfo, begin);
		mapInfo.addDrawable(draw);

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
				getMapEditableInfo().showRectangle(new Point(x, y), width,
						height);
			} else {
				getMapEditableInfo().hideRectangle(new Point(x, y), width,
						height);
			}
			// this.mapInfo.stopDrawRectangle();
			getMapEditableInfo().removeDrawable(draw);
			mapListener.defaultListener();
		}
	}
}
