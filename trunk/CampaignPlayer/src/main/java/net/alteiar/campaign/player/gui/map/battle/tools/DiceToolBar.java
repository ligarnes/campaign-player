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
import net.alteiar.chat.message.DiceSender;

public class DiceToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private static final String ICON_D4 = "d4.png";
	private static final String ICON_D6 = "d6.png";
	private static final String ICON_D8 = "d8.png";
	private static final String ICON_D10 = "d10.png";
	private static final String ICON_D12 = "d12.png";
	private static final String ICON_D20 = "d20.png";
	private static final String ICON_D24 = "d24.png";
	private static final String ICON_D30 = "d30.png";
	private static final String ICON_D100 = "d100.png";
	private static final String ICON_DX = "dx.png";

	private final ArrayList<Die> dice;

	public DiceToolBar() {
		dice = new ArrayList<Die>();

		JButton rollButton = new JButton("Lancer les dés");
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice();
			}
		});
		this.add(rollButton);

		addDie(new Die(4, 1));
		addDie(new Die(6, 1));
		addDie(new Die(8, 1));
		addDie(new Die(10, 1));
		addDie(new Die(12, 1));
		addDie(new Die(20, 1));
		addDie(new Die(100, 1));

		JButton addDieButton = new JButton("Ajouter un dé");
		addDieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Die newDie = ask();
				if (newDie != null) {
					addDie(newDie);
				}
			}
		});
		this.add(addDieButton);

		this.addSeparator();
	}

	private void addDie(Die die) {
		JToggleButton dieButton = new JToggleButton(Helpers.getIcon(
				getDieIcon(die), 64, 48));
		dieButton.addActionListener(die);
		dieButton.setToolTipText("Nombre de face: " + die.getNumFaces());
		this.dice.add(die);
		this.add(dieButton);
	}

	private boolean rollDice() {
		boolean atLeastOneDieSelected = false;
		int totalDice = 0;
		for (Die die : this.dice) {
			if (die.isSelected()) {
				// numDice ++;
				DiceSender dice = new DiceSender(1, die.getNumFaces(), 0);
				CampaignClient.getInstance().getChat()
						.talk(dice, DiceSender.DICE_SENDER_COMMAND);
				atLeastOneDieSelected = true;
				totalDice += Integer.valueOf(dice.getTotal());
				// die.roll();
			}
		}
		if (atLeastOneDieSelected) {
			CampaignClient.getInstance().getChat()
					.talk("le total est: " + totalDice);
		} else {
			// TODO : ne pas /crire ceci dans le chat,
			// mais bien dans un pop up window qui sera montré
			// uniquement à l'utilisateur.
			CampaignClient.getInstance().getChat()
					.talk("Aucun dé sélectionné.");
		}
		return atLeastOneDieSelected;
	}

	public final Die ask() {
		PanelDiceSelection panelDiceSelection = new PanelDiceSelection();

		int result = JOptionPane.showConfirmDialog(null, panelDiceSelection,
				"Création d'un nouveau dé", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			return new Die(panelDiceSelection.getNumFaces(), 1);
		} else {
			return null;
		}
	}

	private String getDieIcon(Die die) {
		switch (die.getNumFaces()) {
		case 4:
			return ICON_D4;
		case 6:
			return ICON_D6;
		case 8:
			return ICON_D8;
		case 10:
			return ICON_D10;
		case 12:
			return ICON_D12;
		case 20:
			return ICON_D20;
		case 24:
			return ICON_D24;
		case 30:
			return ICON_D30;
		case 100:
			return ICON_D100;
		default:
			return ICON_DX;
		}
	}

	public static class PanelDiceSelection extends JPanel implements
			ActionListener {

		private static final long serialVersionUID = 1L;
		static String d4 = "4 faces";
		static String d6 = "6 faces";
		static String d8 = "8 faces";
		static String d10 = "10 faces";
		static String d12 = "12 faces";
		static String d20 = "20 faces";
		static String d24 = "24 faces";
		static String d30 = "30 faces";
		static String d100 = "100 faces";

		int numFaces;

		JLabel picture;

		public PanelDiceSelection() {
			super(new BorderLayout());
			
			// Create the radio buttons.
			JRadioButton d4Button = new JRadioButton(d4);
			d4Button.setActionCommand(ICON_D4);
			
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
			
			JRadioButton d100Button = new JRadioButton(d100);
			d100Button.setActionCommand(ICON_D100);

			// Group the radio buttons.
			ButtonGroup group = new ButtonGroup();
			group.add(d4Button);
			group.add(d6Button);
			group.add(d8Button);
			group.add(d10Button);
			group.add(d12Button);
			group.add(d20Button);
			group.add(d24Button);
			group.add(d30Button);
			group.add(d100Button);

			// Register a listener for the radio buttons.
			d4Button.addActionListener(this);
			d6Button.addActionListener(this);
			d8Button.addActionListener(this);
			d10Button.addActionListener(this);
			d12Button.addActionListener(this);
			d20Button.addActionListener(this);
			d24Button.addActionListener(this);
			d30Button.addActionListener(this);
			d100Button.addActionListener(this);

			// Set up the picture label.
			picture = new JLabel(createImageIcon(ICON_D6));

			// The preferred size is hard-coded to be the width of the
			// widest image and the height of the tallest image.
			// A real program would compute this.
			picture.setPreferredSize(new Dimension(320, 240));

			// Put the radio buttons in a column in a panel.
			JPanel radioPanel = new JPanel(new GridLayout(0, 1));
			radioPanel.add(d4Button);
			radioPanel.add(d6Button);
			radioPanel.add(d8Button);
			radioPanel.add(d10Button);
			radioPanel.add(d12Button);
			radioPanel.add(d20Button);
			radioPanel.add(d24Button);
			radioPanel.add(d30Button);
			radioPanel.add(d100Button);

			add(radioPanel, BorderLayout.LINE_START);
			add(picture, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		}

		/** Listens to the radio buttons. */
		@Override
		public void actionPerformed(ActionEvent e) {
			picture.setIcon(createImageIcon(e.getActionCommand()));
			
			if (ICON_D4.equals(e.getActionCommand())) {
				numFaces = 4;
			} else if (ICON_D6.equals(e.getActionCommand())) {
				numFaces = 6;
			} else if (ICON_D8.equals(e.getActionCommand())) {
				numFaces = 8;
			} else if (ICON_D10.equals(e.getActionCommand())) {
				numFaces = 10;
			} else if (ICON_D12.equals(e.getActionCommand())) {
				numFaces = 12;
			} else if (ICON_D20.equals(e.getActionCommand())) {
				numFaces = 20;
			} else if (ICON_D24.equals(e.getActionCommand())) {
				numFaces = 24;
			} else if (ICON_D30.equals(e.getActionCommand())) {
				numFaces = 30;
			} else if (ICON_D100.equals(e.getActionCommand())) {
				numFaces = 100;
			} else {
				numFaces = 0;
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
