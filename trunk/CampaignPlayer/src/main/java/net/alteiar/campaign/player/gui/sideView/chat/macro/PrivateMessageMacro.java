package net.alteiar.campaign.player.gui.sideView.chat.macro;

import net.alteiar.chat.Message;
import net.alteiar.chat.MessageFactory;

public class PrivateMessageMacro extends Macro {

	@Override
	public String[] getCommand() {
		return new String[] { "/to" };
	}

	@Override
	protected Message applyValidMacro(String text) {
		String[] args = text.split(" ", 2);

		String receiverName = args[0];
		String msg = args[1];

		return MessageFactory.privateMessage(receiverName, msg);
	}

	@Override
	public String getHelp() {
		return "**/to** ##nomDuJoueur message## envoyer un message au joueur seulement";
	}
}
