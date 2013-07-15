package net.alteiar.campaign.player.gui.sideView.chat.macro;

import net.alteiar.chat.Message;
import net.alteiar.chat.MessageFactory;

public class StatMacro extends Macro {

	@Override
	public String[] getCommand() {
		return new String[] { "/stat" };
	}

	@Override
	public String getHelp() {
		return "**/stat** memory usage information";
	}

	@Override
	protected Message applyValidMacro(String message) {
		return MessageFactory.selfMessage(showStat());
	}

	public static String showStat() {
		Runtime runtime = Runtime.getRuntime();
		Integer byteToMega = 1048576;

		String msg = "used : "
				+ ((runtime.totalMemory() - runtime.freeMemory()) / byteToMega)
				+ "mb " + "\\";

		msg += "  committed : " + (runtime.totalMemory() / byteToMega) + "mb "
				+ "\\";
		msg += "  max : " + (runtime.maxMemory() / byteToMega) + "mb " + " \\";

		return msg;
	}
}
