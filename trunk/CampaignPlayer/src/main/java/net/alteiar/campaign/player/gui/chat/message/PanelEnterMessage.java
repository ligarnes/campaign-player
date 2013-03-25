package net.alteiar.campaign.player.gui.chat.message;

import net.alteiar.chat.message.MessageRemote;

public class PanelEnterMessage extends PanelSystemMessage {
	private static final long serialVersionUID = 1L;

	public PanelEnterMessage(int maxWidth, MessageRemote msg) {
		super(maxWidth, "<html>" + msg.getSender()
				+ " viens de rejoindre la partie<html>");
	}

}
