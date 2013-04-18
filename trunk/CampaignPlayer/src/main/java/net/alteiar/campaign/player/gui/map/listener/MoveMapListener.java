package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

class MoveMapListener extends MapAdapter {
	private final MapEditableInfo mapInfo;
	private Point first;

	public MoveMapListener(MapEditableInfo mapInfo) {
		this.mapInfo = mapInfo;
		first = null;
	}

	protected MapEditableInfo getMapEditableInfo() {
		return mapInfo;
	}

	@Override
	public void mousePressed(MapEvent event) {

		if (SwingUtilities.isLeftMouseButton(event.getMouseEvent())) {
			first = event.getMapPosition();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (first != null) {
				int moveX = first.x - mapPosition.x;
				int moveY = first.y - mapPosition.y;
				mapInfo.move(moveX, moveY);
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		mapInfo.zoom(mapPosition, event.getWheelRotation());
	}
}