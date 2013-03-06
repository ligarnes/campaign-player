package net.alteiar.campaign.player.gui.chat.message;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.server.document.chat.message.MessageRemote;
import net.alteiar.server.document.chat.message.PrivateSender;
import net.miginfocom.swing.MigLayout;

public class PanelPrivateTextMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelPrivateTextMessage(int maxWidth, MessageRemote msg,
			PrivateSender realMsg) {
		this.setLayout(new MigLayout("insets 0 0 0 0, wmax " + maxWidth,
				"[][][grow]", "[]"));

		JLabel lblSub = new JLabel("(privé)");
		lblSub.setFont(lblSub.getFont().deriveFont(8f));
		add(lblSub, "aligny top");

		JLabel lblName = new JLabel(msg.getExpediteur() + ":");
		lblName.setFont(lblName.getFont().deriveFont(Font.BOLD));
		add(lblName, "aligny top");

		JLabel lblText = new JLabel("<html>" + realMsg.getMessage() + "<html>");
		add(lblText, "spanx ,growx,aligny top");
	}
}
