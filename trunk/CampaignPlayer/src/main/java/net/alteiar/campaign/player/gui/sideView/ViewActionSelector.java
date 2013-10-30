package net.alteiar.campaign.player.gui.sideView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.player.gui.UiManager;
import net.alteiar.campaign.player.gui.centerViews.ApplicationView;

public class ViewActionSelector extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public ViewActionSelector(ApplicationView view) {
		putValue(NAME, view.getName());

		putValue(LARGE_ICON_KEY, view.getIcon());
		putValue(SMALL_ICON, view.getIcon());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = (String) getValue(NAME);
		UiManager.getInstance().selectView(name);
	}
}