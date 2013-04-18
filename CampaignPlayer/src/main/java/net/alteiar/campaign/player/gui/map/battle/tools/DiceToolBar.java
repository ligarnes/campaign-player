package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;

public class DiceToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private static final String ICON_D6 = "d6.png";
	private static final String ICON_D8 = "d8.png";
	private static final String ICON_D10 = "d10.png";
	private static final String ICON_D12 = "d12.png";
	private static final String ICON_D20 = "d20.png";
	private static final String ICON_D24 = "d24.png";
	private static final String ICON_D30 = "d30.png";
	private static final String ICON_DX = "dx.png";
	
	private ArrayList<Die> dice;
	
	public DiceToolBar() {
		dice = new ArrayList<Die>();
	
		JButton rollButton = new JButton("Lancer les dés");
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rollDice()){ // rollDice retourne faux si aucun dé n'est sélectionné
					showDice();
				}
			}
		});
		this.add(rollButton);
		
		JButton addDieButton = new JButton("Ajouter un dé");
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
		JToggleButton dieButton = new JToggleButton(Helpers.getIcon(
				getDieIcon(die), 64, 48));
		dieButton.addActionListener(die);
		dieButton.setToolTipText("Nombre de face: " + die.getNumFaces());
		this.dice.add(die);
		this.add(dieButton);
	}
	
	private boolean rollDice(){
		boolean atLeastOneDieSelected = false;
		for (Die die : this.dice){
			if (die.isSelected()){
				atLeastOneDieSelected = true;
				die.roll();
			}
		}
		if (atLeastOneDieSelected){
			CampaignClient.getInstance().getChat().talk("Je lance les dés.");
		}
		else{
			// TODO : ne pas /crire ceci dans le chat, 
			// mais bien dans un pop up window qui sera montré
			// uniquement à l'utilisateur.
			CampaignClient.getInstance().getChat().talk("Aucun dé sélectionné.");
		}
		return atLeastOneDieSelected;
	}
	
	private void showDice(){
		String message = "";
		int total = 0;
		for (Die die : this.dice){
			if (die.isSelected()){
				// TODO: les '\n' ne fonctionnent pas.
				message += String.format("Dé à %d faces : %d.\n", die.getNumFaces(), die.getUpFace());
				total += die.getUpFace();
			}
		}
		message += String.format("Total : %d.", total);
		CampaignClient.getInstance().getChat().talk(message);
	}
	
	public final Die ask(){
//		JPanel panel = new JPanel();
//		
//		JTextField numFacesTextField = new JTextField(5);
//		JTextField lowestValueTextField = new JTextField(5);
//		panel.add(new JLabel("Nombre de face: "));
//		panel.add(numFacesTextField);
//		panel.add(Box.createHorizontalStrut(15));
//		panel.add(new JLabel("Valeur la plus basse: "));
//		panel.add(lowestValueTextField);
//		
//		int result = JOptionPane.showConfirmDialog(null, panel, 
//	               "Création d'un nouveau dé", JOptionPane.OK_CANCEL_OPTION);
//		
//		if (result == JOptionPane.OK_OPTION) {
//			int numFaces = Integer.valueOf(numFacesTextField.getText());
//			int lowestValue = Integer.valueOf(lowestValueTextField.getText());
//	        return new Die(numFaces, lowestValue);
//	    }
//		else{
//			return null;
//		}
		
		PanelDiceSelection panelDiceSelection = new PanelDiceSelection();
		
		int result = JOptionPane.showConfirmDialog(null, panelDiceSelection, 
	               "Création d'un nouveau dé", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			
	        return new Die(panelDiceSelection.getNumFaces(), 1);
	    }
		else{
			return null;
		}
	}
	
	private String getDieIcon(Die die){
		switch (die.getNumFaces()) {
			case 6: return ICON_D6;
			case 8: return ICON_D8;
			case 10: return ICON_D10;
			case 12: return ICON_D12;
			case 20: return ICON_D20;
			case 24: return ICON_D24;
			case 30: return ICON_D30;
			default: return ICON_DX;
		}
	}
	
	public static class PanelDiceSelection extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		static String d6 = "6 faces";
		static String d8 = "8 faces";
		static String d10 = "10 faces";
		static String d12 = "12 faces";
		static String d20 = "20 faces";
		static String d24 = "24 faces";
		static String d30 = "30 faces";
		
		int numFaces;

		JLabel picture;
		
		public PanelDiceSelection() {
			super(new BorderLayout());
			
			//Create the radio buttons.
			JRadioButton d6Button = new JRadioButton(d6);
			d6Button.setActionCommand(ICON_D6);
			numFaces = 6;
			d6Button.setSelected(true);
			
			JRadioButton d8Button = new JRadioButton(d8);
			d8Button.setActionCommand(ICON_D8);
			
			JRadioButton d10Button = new JRadioButton(d10);
			d10Button.setActionCommand(ICON_D10);
			
			JRadioButton d12Button = new JRadioButton(d12);
			d12Button.setActionCommand(ICON_D12);
			
			JRadioButton d20Button = new JRadioButton(d20);
			d20Button.setActionCommand(ICON_D20);
			
			JRadioButton d24Button = new JRadioButton(d24);
			d24Button.setActionCommand(ICON_D24);
			
			JRadioButton d30Button = new JRadioButton(d30);
			d30Button.setActionCommand(ICON_D30);
			
			//Group the radio buttons.
			ButtonGroup group = new ButtonGroup();
			group.add(d6Button);
			group.add(d8Button);
			group.add(d10Button);
			group.add(d12Button);
			group.add(d20Button);
			group.add(d24Button);
			group.add(d30Button);
			
			//Register a listener for the radio buttons.
			d6Button.addActionListener(this);
			d8Button.addActionListener(this);
			d10Button.addActionListener(this);
			d12Button.addActionListener(this);
			d20Button.addActionListener(this);
			d24Button.addActionListener(this);
			d30Button.addActionListener(this);
			
			//Set up the picture label.
			picture = new JLabel(createImageIcon(ICON_D6));
			
			//The preferred size is hard-coded to be the width of the
			//widest image and the height of the tallest image.
			//A real program would compute this.
			picture.setPreferredSize(new Dimension(320, 240));
			
			
			//Put the radio buttons in a column in a panel.
			JPanel radioPanel = new JPanel(new GridLayout(0, 1));
			radioPanel.add(d6Button);
			radioPanel.add(d8Button);
			radioPanel.add(d10Button);
			radioPanel.add(d12Button);
			radioPanel.add(d20Button);
			radioPanel.add(d24Button);
			radioPanel.add(d30Button);
			
			add(radioPanel, BorderLayout.LINE_START);
			add(picture, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		}
		
		/** Listens to the radio buttons. */
		public void actionPerformed(ActionEvent e) {
			picture.setIcon(createImageIcon(e.getActionCommand()));
			switch (e.getActionCommand()) {
				case ICON_D6: numFaces = 6; break;
				case ICON_D8: numFaces = 8; break;
				case ICON_D10: numFaces = 10; break;
				case ICON_D12: numFaces = 12; break;
				case ICON_D20: numFaces = 20; break;
				case ICON_D24: numFaces = 24; break;
				case ICON_D30: numFaces = 30; break;
				default: numFaces = 0;
			}
		}
		
		public int getNumFaces() {
			return numFaces;
		}
		
		/** Returns an ImageIcon */
		protected static ImageIcon createImageIcon(String path) {
			return Helpers.getIcon(path, 320, 240);
		}
	}

}
