package net.alteiar.campaign.player.gui.chat.message;

import net.alteiar.server.document.chat.message.MessageRemote;

public class PanelLeaveMessage extends PanelSystemMessage {
	private static final long serialVersionUID = 1L;

	public PanelLeaveMessage(int maxWidth, MessageRemote msg) {
		super(maxWidth, "<html>" + msg.getExpediteur()
				+ " viens de quitter la partie<html>");
	}

}
