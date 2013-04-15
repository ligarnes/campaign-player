package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.PolygonToMouse;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class ShowHidePolygonMapListener extends ActionMapListener {

	private final MapEditableInfo mapInfo;
	private final Boolean isShow;

	private final PolygonToMouse draw;

	public ShowHidePolygonMapListener(GlobalMapListener mapListener,
			MapEditableInfo mapInfo, Point begin, Boolean isShow) {
		super(mapListener);
		this.mapInfo = mapInfo;

		draw = new PolygonToMouse(mapInfo, begin);
		this.mapInfo.addDrawable(draw);
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
			mapInfo.showPolygon(draw.getPts());
		} else {
			mapInfo.hidePolygon(draw.getPts());
		}
		mapInfo.removeDrawable(draw);
		mapListener.defaultListener();
	}
}
