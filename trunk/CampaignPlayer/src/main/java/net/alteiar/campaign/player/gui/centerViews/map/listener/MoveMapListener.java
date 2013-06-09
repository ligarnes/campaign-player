package net.alteiar.campaign.player.gui.centerViews.map.listener;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.event.MapEvent;
import net.alteiar.zoom.MoveZoomListener;

public class MoveMapListener extends MapAdapter {

	private final MapEditableInfo mapInfo;
	private final MoveZoomListener listener;

	public MoveMapListener(MapEditableInfo mapInfo) {
		this.mapInfo = mapInfo;
		listener = new MoveZoomListener(mapInfo.getMoveZoomPanel());
	}

	protected MapEditableInfo getMapEditableInfo() {
		return mapInfo;
	}

	@Override
	public void mousePressed(MapEvent event) {
		listener.mousePressed(event.getMouseEvent());
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
		listener.mouseDragged(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e, Point mapPosition) {
		e = new MouseWheelEvent((Component) e.getSource(), e.getID(),
				e.getWhen(), e.getModifiers(), mapPosition.x, mapPosition.y,
				e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), false,
				e.getScrollType(), e.getScrollAmount(), e.getWheelRotation(),
				e.getPreciseWheelRotation());
		listener.mouseWheelMoved(e);
	}
}