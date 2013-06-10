package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import javax.swing.Action;
import javax.swing.JButton;

public class ButtonCustom extends JButton {
	private static final long serialVersionUID = 1L;

	public ButtonCustom(Action act) {
		super(act);
		setContentAreaFilled(false);
		setBorder(null);
	}
}
