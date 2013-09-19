package net.alteiar.beans.dice.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.alteiar.beans.dice.Dice;
import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceSingle;

public class DiceCountVisitor implements DiceVisitor {

	private final HashMap<Integer, List<Integer>> diceResult;
	private Integer modifier;

	public DiceCountVisitor() {
		diceResult = new HashMap<Integer, List<Integer>>();
		modifier = 0;
	}

	@Override
	public void visit(Dice visit) {

	}

	@Override
	public void visit(DiceSingle visit) {
		Integer numFace = visit.getFaceCount();

		List<Integer> vals = diceResult.get(numFace);
		if (vals == null) {
			vals = new ArrayList<Integer>();
			diceResult.put(numFace, vals);
		}
		vals.add(visit.getTotal());
	}

	@Override
	public void visit(DiceBag visit) {
		this.modifier = visit.getModifier();
	}

	public Integer getDiceCount(Integer dice) {
		return diceResult.get(dice).size();
	}

	public List<Integer> getResults(Integer dice) {
		return diceResult.get(dice);
	}

	public Set<Integer> getDices() {
		return diceResult.keySet();
	}

	public Integer getModifier() {
		return modifier;
	}

}
