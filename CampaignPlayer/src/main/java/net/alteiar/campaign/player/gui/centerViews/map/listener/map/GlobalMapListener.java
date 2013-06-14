package net.alteiar.campaign.player.gui.centerViews.map.listener.map;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapListener;
import net.alteiar.campaign.player.gui.centerViews.map.listener.map.state.DefaultMapListener;

public class GlobalMapListener implements MapListener {

	private final DefaultMapListener defaultListener;

	private ActionMapListener currentListener;

	public GlobalMapListener(MapEditableInfo mapInfo) {
		defaultListener = new DefaultMapListener(mapInfo);
		currentListener = defaultListener;
	}

	public void setCurrentListener(ActionMapListener listener) {
		currentListener.endTask();
		currentListener = listener;
		currentListener.startTask();
	}

	public MapListener getCurrentListener() {
		return currentListener;
	}

	public void defaultListener() {
		setCurrentListener(defaultListener);
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
