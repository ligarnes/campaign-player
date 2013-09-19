package net.alteiar.beans.dice;

import java.io.Serializable;

import net.alteiar.beans.dice.visitor.DiceVisitor;

public interface Dice extends Serializable {
	void roll();

	Integer getTotal();

	void visit(DiceVisitor visitor);
}
