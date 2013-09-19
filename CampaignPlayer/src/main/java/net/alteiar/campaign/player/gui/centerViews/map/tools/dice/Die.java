package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.alteiar.beans.dice.DiceSingle;

public class Die implements ActionListener {

	private final DiceSingle dice;

	private boolean selected;

	public Die(DiceSingle numFaces) {
		this.dice = numFaces;
		this.selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	private void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setSelected(!isSelected());
	}

	public DiceSingle getDice() {
		// need to create one each time because it have some memory
		return new DiceSingle(dice.getFaceCount());
	}
}
