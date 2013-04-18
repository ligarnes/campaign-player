package net.alteiar.campaign.player.gui.map.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.event.MapEvent;

public class ActionMapListener extends MoveMapListener {
	protected final GlobalMapListener mapListener;

	public ActionMapListener(MapEditableInfo mapInfo,
			GlobalMapListener mapListener) {
		super(mapInfo);

		this.mapListener = mapListener;
	}

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
}
