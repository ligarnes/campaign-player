package net.alteiar.beans.dice.visitor;

import net.alteiar.beans.dice.Dice;
import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceSingle;

public interface DiceVisitor {

	public void visit(Dice visit);

	public void visit(DiceSingle visit);

	public void visit(DiceBag visit);
}
