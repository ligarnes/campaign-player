package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.alteiar.shared.Randomizer;

public class Die implements ActionListener {

	private final int numFaces;
	private int upFace;
	private final int lowestValue;

	private boolean selected;

	public Die(int numFaces, int lowestValue) {
		// Pour l'instant, le dé est considéré comme
		// donnant des valeurs de lowestValue à lowestValue + numFaces.
		this.numFaces = numFaces;
		this.lowestValue = lowestValue;
		this.upFace = lowestValue;
		this.selected = false;
	}

	@Override
	public String toString() {
		return "Die [numFaces=" + numFaces + ", upFace=" + upFace
				+ ", lowestValue=" + lowestValue + ", selected=" + selected
				+ "]";
	}

	public int getNumFaces() {
		return numFaces;
	}

	public int getUpFace() {
		return upFace;
	}

	public int getLowestValue() {
		return lowestValue;
	}

	public boolean isSelected() {
		return selected;
	}

	public void roll() {
		setUpFace(Randomizer.random(lowestValue, numFaces));
	}

	private void setUpFace(int i) {
		this.upFace = i;
	}

	private void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setSelected(!isSelected());
	}

}
