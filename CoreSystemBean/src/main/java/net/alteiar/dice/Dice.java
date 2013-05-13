package net.alteiar.dice;

import java.io.Serializable;

import net.alteiar.dice.visitor.DiceVisitor;

public interface Dice extends Serializable {
	void roll();

	Integer getTotal();

	void visit(DiceVisitor visitor);
}
