package net.alteiar.dice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class DiceListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (DiceRoller.METH_ROLL_METHOD.equals(evt.getPropertyName())) {
			diceRolled((Dice) evt.getNewValue());
		}
	}

	public abstract void diceRolled(Dice dice);

}
