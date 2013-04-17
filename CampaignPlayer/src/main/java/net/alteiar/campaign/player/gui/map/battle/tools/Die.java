package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Die implements ActionListener{

	private int numFaces;
	private boolean selected;
	private int upperFace;
		
	public Die(int numFaces) {
		//Pour l'instant, le dé est considéré comme
		// donnant des valeurs de 1 à numFaces.
		this.numFaces = numFaces;
		this.upperFace = 1;
		this.selected = false;
	}

	public int getUpperFace() {
		return upperFace;
	}

	

	@Override
	public String toString() {
		return "Die [numFaces=" + numFaces + ", selected=" + selected
				+ ", upperFace=" + upperFace + "]";
	}

	public void roll(){
		setUpperFace((new Random()).nextInt(numFaces) + 1);
	}
	
	private void setUpperFace(int i) {
		this.upperFace = i;
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		return selected;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setSelected(!isSelected());
	}

}
