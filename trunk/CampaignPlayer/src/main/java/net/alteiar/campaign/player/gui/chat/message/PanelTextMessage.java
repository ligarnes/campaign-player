package net.alteiar.campaign.player.gui.chat.message;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.server.document.chat.message.MessageRemote;
import net.miginfocom.swing.MigLayout;

public class PanelTextMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelTextMessage(int maxWidth, MessageRemote msg) {
		this.setLayout(new MigLayout("insets 0 0 0 0, wmax " + maxWidth,
				"[][grow]", "[]"));

		JLabel lblName = new JLabel(msg.getExpediteur() + ":");
		lblName.setFont(lblName.getFont().deriveFont(Font.BOLD));
		add(lblName, "aligny top");

		JLabel lblText = new JLabel("<html>" + msg.getMessage() + "<html>");
		add(lblText, "spanx ,growx,aligny top");
	}

}
