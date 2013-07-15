package net.alteiar.campaign.player.gui.sideView.chat.macro;

import java.util.HashMap;
import java.util.HashSet;

import net.alteiar.chat.Message;
import net.alteiar.chat.MessageFactory;

public class MacroManager {

	private final HashMap<String, Macro> macros;

	public MacroManager() {
		macros = new HashMap<String, Macro>();

		addMacro(new DiceMacro());
		addMacro(new PrivateMessageMacro());
		addMacro(new DmMacro());

		addMacro(new HelpMacro());

		addMacro(new StatMacro());
	}

	private void addMacro(Macro m) {
		for (String command : m.getCommand()) {
			macros.put(command, m);
		}
	}

	private String extractCommand(String text) {
		int idx = text.indexOf(" ");
		if (idx >= 0)
			return text.substring(0, idx);
		return text;
	}

	private String extractText(String text) {
		int idx = text.indexOf(" ");
		if (idx >= 0)
			return text.substring(idx + 1);
		return "";
	}

	public Message applyMacro(String text) {
		String command = extractCommand(text);
		String args = extractText(text);
		Macro macro = macros.get(command);

		Message msg = null;
		if (macro != null) {
			msg = macro.applyMacro(args);
		} else {
			msg = new Message(
					"Commande invalide taper /h ou /help pour plus d'information sur les commandes disponibles");
		}
		return msg;
	}

	private class HelpMacro extends Macro {
		@Override
		public String[] getCommand() {
			return new String[] { "/h", "/help" };
		}

		@Override
		protected Message applyValidMacro(String message) {
			String text = "La liste des commandes: \\";

			HashSet<Macro> macs = new HashSet<Macro>(macros.values());

			for (Macro macro : macs) {
				text += " -" + macro.getHelp() + "\\";
			}

			return MessageFactory.selfMessage(text);
		}

		@Override
		public String getHelp() {
			return "**/help, /h** affiche les commandes disponible";
		}

	}
}
