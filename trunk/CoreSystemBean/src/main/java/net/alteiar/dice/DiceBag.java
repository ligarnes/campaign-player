package net.alteiar.dice;

import java.util.ArrayList;

import net.alteiar.dice.visitor.DiceVisitor;

public class DiceBag implements Dice {
	private static final long serialVersionUID = 1L;

	private final ArrayList<Dice> dices;
	private final Integer modifier;

	public DiceBag() {
		this(0);
	}

	public DiceBag(Integer modifier) {
		dices = new ArrayList<Dice>();
		this.modifier = modifier;
	}

	public void addDice(Dice e) {
		dices.add(e);
	}

	@Override
	public void roll() {
		for (Dice die : dices) {
			die.roll();
		}
	}

	public Integer getModifier() {
		return modifier;
	}

	@Override
	public Integer getTotal() {
		Integer total = modifier;
		for (Dice die : dices) {
			total += die.getTotal();
		}
		return total;
	}

	@Override
	public void visit(DiceVisitor visitor) {
		visitor.visit(this);
		for (Dice die : dices) {
			die.visit(visitor);
		}
	}
}
