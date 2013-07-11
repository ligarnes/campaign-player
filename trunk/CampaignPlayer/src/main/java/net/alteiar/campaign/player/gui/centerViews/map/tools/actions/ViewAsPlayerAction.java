package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.event.ActionEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public class ViewAsPlayerAction extends MapAction {
	private static final long serialVersionUID = 1L;

	private static String VIEW_AS_PLAYER = "Vue joueur";
	private static String VIEW_AS_DM = "Vue Mj";

	public ViewAsPlayerAction(MapEditableInfo info) {
		super(info);
		putValue(NAME, VIEW_AS_PLAYER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (getValue(NAME).equals(VIEW_AS_PLAYER)) {
			this.getMapInfo().viewAsPlayer();
			putValue(NAME, VIEW_AS_DM);
		} else {
			this.getMapInfo().viewAsMj();
			putValue(NAME, VIEW_AS_PLAYER);
		}
	}
}
