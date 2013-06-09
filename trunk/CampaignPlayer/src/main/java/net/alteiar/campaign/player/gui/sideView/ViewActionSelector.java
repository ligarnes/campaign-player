package net.alteiar.campaign.player.gui.sideView;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.alteiar.campaign.player.gui.UiManager;

public class ViewActionSelector extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public ViewActionSelector(String view) {
		putValue(NAME, view);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = (String) getValue(NAME);
		UiManager.getInstance().selectView(name);
	}
}