package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.server.document.map.battle.BattleClient;

public class GlobalMapListener implements MapListener {

	private class MoveMapListener extends MapAdapter {
		private final MapEditableInfo mapInfo;
		private Point first;

		public MoveMapListener(MapEditableInfo mapInfo) {
			this.mapInfo = mapInfo;
			first = null;
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

	private final MoveMapListener moveMapListener;
	private final DefaultMapListener defaultListener;

	private MapListener currentListener;

	public GlobalMapListener(BattleClient battle, MapEditableInfo mapInfo) {
		moveMapListener = new MoveMapListener(mapInfo);
		defaultListener = new DefaultMapListener(this, battle, mapInfo);
		currentListener = defaultListener;
	}

	public void setCurrentListener(MapListener listener) {
		currentListener = listener;
	}

	public MapListener getCurrentListener() {
		return currentListener;
	}

	public void defaultListener() {
		currentListener = defaultListener;
	}

	@Override
	public void mouseClicked(final MapEvent event) {
		currentListener.mouseClicked(event);
	}

	@Override
	public void mousePressed(MapEvent event) {
		moveMapListener.mousePressed(event);
		currentListener.mousePressed(event);
	}

	@Override
	public void mouseReleased(MapEvent event) {
	}

	@Override
	public void mouseElementEntered(MapEvent event) {
		currentListener.mouseElementEntered(event);
	}

	@Override
	public void mouseElementExited(MapEvent event) {
		currentListener.mouseElementExited(event);
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
		moveMapListener.mouseDragged(e, mapPosition);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		// should do in another way, do not want to zoom and do other stuff
		currentListener.mouseWheelMoved(event, mapPosition);
		moveMapListener.mouseWheelMoved(event, mapPosition);
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
		currentListener.mouseMove(e, mapPosition);
	}

}
