package net.alteiar.dice;

import net.alteiar.client.bean.BasicBean;

public class DiceRoller extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static String METH_ROLL_METHOD = "roll";

	public void roll(Dice dice) {
		dice.roll();
		if (notifyRemote(METH_ROLL_METHOD, null, dice)) {
			notifyLocal(METH_ROLL_METHOD, null, dice);
		}
	}
}
