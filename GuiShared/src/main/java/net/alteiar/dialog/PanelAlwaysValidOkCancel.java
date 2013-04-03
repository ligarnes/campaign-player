package net.alteiar.dialog;

import javax.swing.JPanel;


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
