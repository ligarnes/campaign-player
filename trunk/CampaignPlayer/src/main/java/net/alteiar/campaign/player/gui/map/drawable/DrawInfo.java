package net.alteiar.campaign.player.gui.map.drawable;

import java.awt.Graphics2D;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;

public abstract class DrawInfo {
	private final MapEditableInfo mapInfo;

	public DrawInfo(MapEditableInfo mapInfo) {
		this.mapInfo = mapInfo;
	}

	protected MapEditableInfo getMapInfo() {
		return this.mapInfo;
	}

	public abstract void draw(Graphics2D g2);
}
