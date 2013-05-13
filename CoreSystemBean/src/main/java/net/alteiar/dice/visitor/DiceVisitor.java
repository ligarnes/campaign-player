package net.alteiar.dice.visitor;

import net.alteiar.dice.Dice;
import net.alteiar.dice.DiceBag;
import net.alteiar.dice.DiceSingle;

public interface DiceVisitor {

	public void visit(Dice visit);

	public void visit(DiceSingle visit);

	public void visit(DiceBag visit);
}
