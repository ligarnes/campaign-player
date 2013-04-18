package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.PolygonToMouse;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class ShowHidePolygonMapListener extends ActionMapListener {

	private final Boolean isShow;

	private final PolygonToMouse draw;

	public ShowHidePolygonMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener, Point begin, Boolean isShow) {
		super(mapInfo, mapListener);

		draw = new PolygonToMouse(mapInfo, begin);
		mapInfo.addDrawable(draw);
		this.isShow = isShow;
	}

	@Override
	public void mouseClicked(MapEvent event) {

		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			draw.addPoint(event.getMapPosition());
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			finishShowHide();
		}
	}

	public void finishShowHide() {
		if (isShow) {
			getMapEditableInfo().showPolygon(draw.getPts());
		} else {
			getMapEditableInfo().hidePolygon(draw.getPts());
		}
		getMapEditableInfo().removeDrawable(draw);
		mapListener.defaultListener();
	}
}
