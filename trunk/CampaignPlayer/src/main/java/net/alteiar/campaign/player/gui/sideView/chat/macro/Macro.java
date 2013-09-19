package net.alteiar.campaign.player.gui.sideView.chat.macro;

import net.alteiar.beans.chat.Message;
import net.alteiar.beans.chat.MessageFactory;

import org.apache.log4j.Logger;

public abstract class Macro {

	public abstract String[] getCommand();

	public abstract String getHelp();

	protected abstract Message applyValidMacro(String message);

	/**
	 * 
	 * @param message
	 * @return the new message to be send or null if the macro do action without
	 *         sending message
	 */
	public Message applyMacro(String message) {
		Message msg = null;
		try {
			msg = applyValidMacro(message);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(getClass()).error("Error while executing macro ",
					ex);
			msg = MessageFactory.selfMessage("**commande invalide: **"
					+ message + " \\" + getHelp());
		}
		return msg;
	}
}
