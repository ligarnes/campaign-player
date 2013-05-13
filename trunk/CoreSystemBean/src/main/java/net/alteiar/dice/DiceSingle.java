package net.alteiar.dice;

import net.alteiar.dice.visitor.DiceVisitor;
import net.alteiar.shared.Randomizer;

public class DiceSingle implements Dice {
	private static final long serialVersionUID = 1L;

	private final Integer faceCount;
	private Integer result;

	public DiceSingle(Integer numFace) {
		this.faceCount = numFace;
		result = null;
	}

	public Integer getFaceCount() {
		return faceCount;
	}

	@Override
	public void roll() {
		if (result == null) {
			result = Randomizer.dice(faceCount);
		}
	}

	@Override
	public Integer getTotal() {
		return result;
	}

	@Override
	public void visit(DiceVisitor visitor) {
		visitor.visit(this);
	}
}
