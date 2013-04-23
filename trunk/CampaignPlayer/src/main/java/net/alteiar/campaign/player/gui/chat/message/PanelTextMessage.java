package net.alteiar.campaign.player.gui.chat.message;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.chat.message.MessageRemote;
import net.alteiar.player.Player;
import net.miginfocom.swing.MigLayout;

public class PanelTextMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelTextMessage(int maxWidth, MessageRemote msg) {
		this.setLayout(new MigLayout("insets 0 0 0 0, wmax " + maxWidth,
				"[][grow]", "[]"));

		JLabel lblName = new JLabel(msg.getSender() + ":");
		lblName.setFont(lblName.getFont().deriveFont(Font.BOLD));

		Player player = CampaignClient.getInstance().getBean(msg.getPlayerId());
		lblName.setForeground(player.getColor());
		add(lblName, "aligny top");

		JLabel lblText = new JLabel("<html>" + msg.getMessage() + "<html>");
		lblText.setForeground(player.getColor());
		add(lblText, "spanx ,growx,aligny top");
	}
}
