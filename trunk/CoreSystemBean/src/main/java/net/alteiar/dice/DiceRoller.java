package net.alteiar.dice;

import java.beans.PropertyVetoException;

import net.alteiar.client.bean.BasicBeans;

public class DiceRoller extends BasicBeans {
	private static final long serialVersionUID = 1L;

	private static String METH_ROLL_METHOD = "roll";

	public void roll(Dice dice) {
		try {
			dice.roll();
			this.vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ROLL_METHOD, null, dice);
			this.propertyChangeSupport.firePropertyChange(METH_ROLL_METHOD,
					null, dice);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}
