package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;
import net.alteiar.campaign.player.gui.map.event.MapListener;
import net.alteiar.documents.map.MapBean;

public class GlobalMapListener implements MapListener {

	private final DefaultMapListener defaultListener;

	private MapListener currentListener;

	public GlobalMapListener(MapBean battle, MapEditableInfo mapInfo) {
		defaultListener = new DefaultMapListener(mapInfo, this, battle);
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
		currentListener.mouseDragged(e, mapPosition);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		currentListener.mouseWheelMoved(event, mapPosition);
	}

	@Override
	public void mouseMove(MouseEvent e, Point mapPosition) {
		currentListener.mouseMove(e, mapPosition);
	}

}
