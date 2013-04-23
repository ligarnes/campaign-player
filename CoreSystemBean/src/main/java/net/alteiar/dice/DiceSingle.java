package net.alteiar.dice;

import net.alteiar.shared.Randomizer;

public class DiceSingle implements Dice {
	private final Integer faceCount;
	private Integer result;

	public DiceSingle(Integer numFace) {
		this.faceCount = numFace;
		result = 0;
	}

	public Integer getFaceCount() {
		return faceCount;
	}

	@Override
	public void roll() {
		result = Randomizer.dice(faceCount);
	}

	@Override
	public Integer getTotal() {
		return result;
	}
}
