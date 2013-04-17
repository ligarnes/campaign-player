package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.campaign.player.Helpers;

public class DiceToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private static String ICON_20_FACES_DIE = "die-20-faces.png";
	
	private ArrayList<Die> dice;
	
	public DiceToolBar() {
		dice = new ArrayList<Die>();
	
		JButton rollButton = new JButton("Roll");
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice();
				showDice();
			}
		});
		this.add(rollButton);
		
		JButton addDieButton = new JButton("Add Die");
		addDieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Die newDie = ask();
				if (newDie != null){
					addDie(newDie);
				}
			}
		});
		this.add(addDieButton);
		
		this.addSeparator();
	}
	
	private void addDie(Die die){
		JToggleButton dieTwentyFaces = new JToggleButton(Helpers.getIcon(
				ICON_20_FACES_DIE, 30, 30));
		dieTwentyFaces.addActionListener(die);
		this.dice.add(die);
		this.add(dieTwentyFaces);
	}
	
	private void rollDice(){
		for (Die die : this.dice){
			if (die.isSelected()){
				die.roll();
			}
		}
	}
	
	private void showDice(){
		for (Die die : this.dice){
			if (die.isSelected()){
				// TODO : send the result to the chat. Good idea?
				System.out.println(die);
			}
		}
	}
	
	public final Die ask(){
		JPanel panel = new JPanel();
		
		JTextField numFaces = new JTextField(5);
		JTextField step = new JTextField(5);
		panel.add(new JLabel("Number of Faces: "));
		panel.add(numFaces);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(new JLabel("Step: "));
		panel.add(step);
		
		int result = JOptionPane.showConfirmDialog(null, panel, 
	               "Bla bla bla", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
	         return new Die(Integer.valueOf(numFaces.getText()));
	    }
		else{
			return null;
		}
	}

}
