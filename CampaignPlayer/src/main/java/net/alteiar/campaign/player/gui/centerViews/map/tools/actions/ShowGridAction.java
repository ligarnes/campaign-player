package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.event.ActionEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.infos.HelpersImages;

public class ShowGridAction extends MapAction {
	private static final long serialVersionUID = 1L;

	private static String ICON_SHOW_GRID = "show-grid.jpg";

	public ShowGridAction(MapEditableInfo info) {
		super(info);

		putValue(NAME, "Afficher la grille");
		putValue(LARGE_ICON_KEY, HelpersImages.getIcon(ICON_SHOW_GRID));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getMapInfo().showGrid(!getMapInfo().getShowGrid());
	}
}
