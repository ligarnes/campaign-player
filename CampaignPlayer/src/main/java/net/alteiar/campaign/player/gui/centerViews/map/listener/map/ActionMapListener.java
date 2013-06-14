package net.alteiar.campaign.player.gui.centerViews.map.listener.map;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.listener.MapEvent;

public abstract class ActionMapListener extends MoveMapListener {
	public ActionMapListener(MapEditableInfo mapInfo) {
		super(mapInfo);
	}

	public abstract void startTask();

	@Override
	public void mousePressed(MapEvent event) {
		super.mousePressed(event);
	}

	@Override
	public void mouseDragged(MouseEvent e, Point mapPosition) {
		super.mouseDragged(e, mapPosition);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event, Point mapPosition) {
		super.mouseWheelMoved(event, mapPosition);
	}

	public abstract void endTask();

}
