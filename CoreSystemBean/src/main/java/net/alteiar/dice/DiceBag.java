package net.alteiar.dice;

import java.util.ArrayList;

public class DiceBag implements Dice {
	private ArrayList<Dice> dices;

	public void addDice(Dice e) {
		dices.add(e);
	}

	@Override
	public void roll() {
		for (Dice die : dices) {
			die.roll();
		}
	}

	@Override
	public Integer getTotal() {
		Integer total = 0;
		for (Dice die : dices) {
			total += die.getTotal();
		}
		return total;
	}
}
