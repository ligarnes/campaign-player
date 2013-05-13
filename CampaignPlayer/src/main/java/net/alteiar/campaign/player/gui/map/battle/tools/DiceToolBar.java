package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.dice.DiceBag;
import net.alteiar.dice.DiceSingle;

public class DiceToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	public static final String ICON_D4_LARGE = "d4-large.png";
	public static final String ICON_D6_LARGE = "d6-large.png";
	public static final String ICON_D8_LARGE = "d8-large.png";
	public static final String ICON_D10_LARGE = "d10-large.png";
	public static final String ICON_D12_LARGE = "d12-large.png";
	public static final String ICON_D20_LARGE = "d20-large.png";
	public static final String ICON_D30_LARGE = "d30-large.png";
	public static final String ICON_D100_LARGE = "d100-large.png";
	public static final String ICON_DX_LARGE = "dx-large.png";

	public static final String ICON_D4_REDUCE = "d4-reduce.png";
	public static final String ICON_D6_REDUCE = "d6-reduce.png";
	public static final String ICON_D8_REDUCE = "d8-reduce.png";
	public static final String ICON_D10_REDUCE = "d10-reduce.png";
	public static final String ICON_D12_REDUCE = "d12-reduce.png";
	public static final String ICON_D20_REDUCE = "d20-reduce.png";
	public static final String ICON_D30_REDUCE = "d30-reduce.png";
	public static final String ICON_D100_REDUCE = "d100-reduce.png";
	public static final String ICON_DX_REDUCE = "dx-reduce.png";

	private final ArrayList<Die> dice;

	public DiceToolBar() {
		dice = new ArrayList<Die>();

		JButton rollButton = new JButton("Lancer les d\u00E9s");
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice();
			}
		});
		this.add(rollButton);

		addDie(new Die(new DiceSingle(4)));
		addDie(new Die(new DiceSingle(6)));
		addDie(new Die(new DiceSingle(8)));
		addDie(new Die(new DiceSingle(10)));
		addDie(new Die(new DiceSingle(12)));
		addDie(new Die(new DiceSingle(20)));
		addDie(new Die(new DiceSingle(100)));

		JButton addDieButton = new JButton("Ajouter un d\u00E9");
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
		JToggleButton dieButton = new JToggleButton(
				Helpers.getIcon(getDieIcon(die)));
		dieButton.addActionListener(die);
		dieButton.setToolTipText("Nombre de face: "
				+ die.getDice().getFaceCount());
		this.dice.add(die);
		this.add(dieButton);
	}

	private boolean rollDice() {
		boolean atLeastOneDieSelected = false;
		DiceBag bag = new DiceBag();
		for (Die die : this.dice) {
			if (die.isSelected()) {
				bag.addDice(die.getDice());
				atLeastOneDieSelected = true;
			}
		}

		CampaignClient.getInstance().getDiceRoller().roll(bag);
		if (!atLeastOneDieSelected) {
			// TODO : ne pas /crire ceci dans le chat,
			// mais bien dans un pop up window qui sera montr\u00E9
			// uniquement à l'utilisateur.
			CampaignClient.getInstance().getChat()
					.talk("Aucun d\u00E9 s\u00E9lectionn\u00E9.");
		}
		return atLeastOneDieSelected;
	}

	public final Die ask() {
		PanelDiceSelection panelDiceSelection = new PanelDiceSelection();

		int result = JOptionPane.showConfirmDialog(null, panelDiceSelection,
				"Cr\u00E9ation d'un nouveau d\u00E9",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			return new Die(new DiceSingle(panelDiceSelection.getNumFaces()));
		} else {
			return null;
		}
	}

	private String getDieIcon(Die die) {
		switch (die.getDice().getFaceCount()) {
		case 4:
			return ICON_D4_REDUCE;
		case 6:
			return ICON_D6_REDUCE;
		case 8:
			return ICON_D8_REDUCE;
		case 10:
			return ICON_D10_REDUCE;
		case 12:
			return ICON_D12_REDUCE;
		case 20:
			return ICON_D20_REDUCE;
		case 30:
			return ICON_D30_REDUCE;
		case 100:
			return ICON_D100_REDUCE;
		default:
			return ICON_DX_REDUCE;
		}
	}

}
