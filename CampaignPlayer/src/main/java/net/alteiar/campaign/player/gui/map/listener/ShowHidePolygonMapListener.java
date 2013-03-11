package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class ShowHidePolygonMapListener extends ActionMapListener {

	private final MapEditableInfo mapInfo;
	private final Boolean isShow;

	private final List<Point> pts;

	public ShowHidePolygonMapListener(GlobalMapListener mapListener,
			MapEditableInfo mapInfo, Point begin, Boolean isShow) {
		super(mapListener);
		this.mapInfo = mapInfo;
		this.mapInfo.drawPolygonToMouse(begin);
		this.isShow = isShow;

		pts = new ArrayList<Point>();
		pts.add(begin);
	}

	@Override
	public void mouseClicked(MapEvent event) {

		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			this.mapInfo.addPointToLine(event.getMapPosition());
			pts.add(event.getMapPosition());
		} else if (SwingUtilities.isRightMouseButton(event.getMouseEvent())) {
			finishShowHide();
		}
	}

	public void finishShowHide() {
		if (isShow) {
			mapInfo.showPolygon(pts);
		} else {
			mapInfo.hidePolygon(pts);
		}
		this.mapInfo.stopDrawPolygon();
		mapListener.defaultListener();
	}
}
