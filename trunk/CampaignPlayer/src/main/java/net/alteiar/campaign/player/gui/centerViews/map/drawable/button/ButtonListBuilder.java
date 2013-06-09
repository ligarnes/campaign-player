package net.alteiar.campaign.player.gui.centerViews.map.drawable.button;

import net.alteiar.campaign.player.gui.centerViews.map.PanelMapBasic;

public class ButtonListBuilder {

	private final PanelMapBasic map;

	private int xCurrent;
	private final int yCurrent;

	private int offset;

	public ButtonListBuilder(PanelMapBasic map, int xBegin, int yBegin,
			int offset) {
		this.map = map;

		this.xCurrent = xBegin;
		this.yCurrent = yBegin;
	}

	public void addButton(ButtonDrawable btn) {
		btn.setPosition(xCurrent, yCurrent);

		map.addButtonDrawable(btn);

		xCurrent += btn.getWidth() + offset;
	}

}
