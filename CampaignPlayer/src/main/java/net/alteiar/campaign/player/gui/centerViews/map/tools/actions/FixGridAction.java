package net.alteiar.campaign.player.gui.centerViews.map.tools.actions;

import java.awt.event.ActionEvent;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;

public class FixGridAction extends MapAction {
	private static final long serialVersionUID = 1L;

	public FixGridAction(MapEditableInfo info) {
		super(info);

		putValue(NAME, "Fixer a la grille");
		// putValue(SMALL_ICON, Helpers.getIcon(ICON_ADD_ELEMENT_REDUCE_NAME));
		// putValue(LARGE_ICON_KEY, Helpers.getIcon(ICON_ADD_ELEMENT_NAME));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getMapInfo().fixGrid(!getMapInfo().getFixGrid());
	}

}
