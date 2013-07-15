package net.alteiar.campaign.player.gui.sideView.chat.macro;

import net.alteiar.chat.Message;
import net.alteiar.chat.MessageFactory;

public class DmMacro extends Macro {

	@Override
	public String[] getCommand() {
		return new String[] { "/dm", "/mj" };
	}

	@Override
	protected Message applyValidMacro(String message) {
		return MessageFactory.dmMessage(message);
	}

	@Override
	public String getHelp() {
		return "**/dm, /mj** envoyer un message au mj seulement";
	}
}
