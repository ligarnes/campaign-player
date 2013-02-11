package net.alteiar.campaign.player.gui.chat.message;

import net.alteiar.server.shared.campaign.chat.MessageRemote;

public class PanelEnterMessage extends PanelSystemMessage {
	private static final long serialVersionUID = 1L;

	public PanelEnterMessage(int maxWidth, MessageRemote msg) {
		super(maxWidth, "<html>" + msg.getExpediteur()
				+ " viens de rejoindre la partie<html>");
	}

}
