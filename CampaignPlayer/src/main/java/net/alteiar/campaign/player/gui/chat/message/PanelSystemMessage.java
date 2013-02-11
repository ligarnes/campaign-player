package net.alteiar.campaign.player.gui.chat.message;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class PanelSystemMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSystemMessage(int maxWidth, String message) {
		this.setLayout(new MigLayout("insets 0 0 0 0, wmax " + maxWidth,
				"[][grow]", "[]"));

		JLabel lblText = new JLabel(message);
		lblText.setFont(lblText.getFont().deriveFont(Font.BOLD));
		add(lblText, "spanx ,growx,aligny top");
	}

}
