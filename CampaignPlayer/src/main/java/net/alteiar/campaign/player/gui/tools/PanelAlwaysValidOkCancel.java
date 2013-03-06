package net.alteiar.campaign.player.gui.tools;

import javax.swing.JPanel;

import net.alteiar.dialog.PanelOkCancel;

public class PanelAlwaysValidOkCancel extends JPanel implements PanelOkCancel {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isDataValid() {
		return true;
	}

	@Override
	public String getInvalidMessage() {
		return "";
	}

}
