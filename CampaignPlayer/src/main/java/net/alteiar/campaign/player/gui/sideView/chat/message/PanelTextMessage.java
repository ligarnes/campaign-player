package net.alteiar.campaign.player.gui.sideView.chat.message;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.beans.chat.Message;
import net.miginfocom.swing.MigLayout;

public class PanelTextMessage extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelTextMessage(int maxWidth, Message msg) {
		this.setLayout(new MigLayout("insets 0 0 0 0, wmax " + maxWidth,
				"[][grow]", "[]"));

		/*
		 * JLabel lblName = new JLabel(msg.getSender() + ":");
		 * lblName.setFont(lblName.getFont().deriveFont(Font.BOLD));
		 * 
		 * Player player =
		 * CampaignClient.getInstance().getBean(msg.getPlayerId());
		 * lblName.setForeground(player.getColor()); add(lblName, "aligny top");
		 */
		JLabel lblText = new JLabel(msg.getHtmlFormat());
		// lblText.setForeground(player.getColor());
		add(lblText, "spanx ,growx,aligny top");
	}
}
