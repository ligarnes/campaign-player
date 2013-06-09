package net.alteiar.campaign.player.gui.centerViews.map.drawable;

import java.awt.Graphics2D;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public abstract class DrawFilter {
	private final MapEditableInfo mapInfo;

	public DrawFilter(MapEditableInfo mapInfo) {
		this.mapInfo = mapInfo;
	}

	protected MapEditableInfo getMapInfo() {
		return this.mapInfo;
	}

	public abstract void draw(Graphics2D g2);
}
