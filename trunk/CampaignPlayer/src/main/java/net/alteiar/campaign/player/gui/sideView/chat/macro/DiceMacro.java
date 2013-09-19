package net.alteiar.campaign.player.gui.sideView.chat.macro;

import net.alteiar.beans.chat.Message;
import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceSingle;
import net.alteiar.campaign.CampaignClient;

public class DiceMacro extends Macro {

	@Override
	public String[] getCommand() {
		return new String[] { "/roll", "/r" };
	}

	@Override
	protected Message applyValidMacro(String message) {
		message = message.replaceAll(" ", "");

		// Parse the chat standard
		String diceCntStr = message.substring(0, message.indexOf("d"));

		int modif = message.indexOf("+");
		int diceValEnd = modif == -1 ? message.length() : modif;
		String diceValStr = message.substring(message.indexOf("d") + 1,
				diceValEnd);

		String modStr = "0";
		if (modif != -1) {
			modStr = message.substring(modif + 1);
		}

		int diceCount = Integer.valueOf(diceCntStr);
		int diceValue = Integer.valueOf(diceValStr);
		int mod = Integer.valueOf(modStr);

		DiceBag bag = new DiceBag(mod);
		for (int i = 0; i < diceCount; ++i) {
			bag.addDice(new DiceSingle(diceValue));
		}

		CampaignClient.getInstance().getDiceRoller().roll(bag);

		return null;
	}

	@Override
	public String getHelp() {
		return "**/roll, /r** ##XdY[+Z]## lancer des dés";
	}
}
