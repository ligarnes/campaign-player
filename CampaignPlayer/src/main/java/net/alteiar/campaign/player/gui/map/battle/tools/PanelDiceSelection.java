package net.alteiar.campaign.player.gui.map.battle.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.alteiar.campaign.player.Helpers;

public class PanelDiceSelection extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	int numFaces;

	JLabel picture;

	public PanelDiceSelection() {
		super(new BorderLayout());

		// Set up the picture label.
		picture = new JLabel();
		picture.setPreferredSize(new Dimension(240, 240));

		TreeMap<Die, String> dices = new TreeMap<Die, String>(
				new Comparator<Die>() {
					@Override
					public int compare(Die o1, Die o2) {
						return o1.getNumFaces() - o2.getNumFaces();
					}
				});

		dices.put(new Die(4, 1), DiceToolBar.ICON_D4_LARGE);
		dices.put(new Die(6, 1), DiceToolBar.ICON_D6_LARGE);
		dices.put(new Die(8, 1), DiceToolBar.ICON_D8_LARGE);
		dices.put(new Die(10, 1), DiceToolBar.ICON_D10_LARGE);
		dices.put(new Die(12, 1), DiceToolBar.ICON_D12_LARGE);
		dices.put(new Die(20, 1), DiceToolBar.ICON_D20_LARGE);
		dices.put(new Die(30, 1), DiceToolBar.ICON_D30_LARGE);
		dices.put(new Die(100, 1), DiceToolBar.ICON_D100_LARGE);

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		ButtonGroup group = new ButtonGroup();
		for (Entry<Die, String> die : dices.entrySet()) {
			JRadioButton d4Button = new JRadioButton(die.getKey().getNumFaces()
					+ " faces");
			d4Button.setActionCommand(die.getValue());
			group.add(d4Button);
			d4Button.addActionListener(this);
			radioPanel.add(d4Button);

			if (die.getKey().getNumFaces() == 6) {
				d4Button.setSelected(true);
				picture.setIcon(createImageIcon(die.getValue()));
			}
		}
		numFaces = 6;

		// Put the radio buttons in a column in a panel.
		add(radioPanel, BorderLayout.LINE_START);
		add(picture, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	/** Listens to the radio buttons. */
	@Override
	public void actionPerformed(ActionEvent e) {
		picture.setIcon(createImageIcon(e.getActionCommand()));

		if (DiceToolBar.ICON_D4_LARGE.equals(e.getActionCommand())) {
			numFaces = 4;
		} else if (DiceToolBar.ICON_D6_LARGE.equals(e.getActionCommand())) {
			numFaces = 6;
		} else if (DiceToolBar.ICON_D8_LARGE.equals(e.getActionCommand())) {
			numFaces = 8;
		} else if (DiceToolBar.ICON_D10_LARGE.equals(e.getActionCommand())) {
			numFaces = 10;
		} else if (DiceToolBar.ICON_D12_LARGE.equals(e.getActionCommand())) {
			numFaces = 12;
		} else if (DiceToolBar.ICON_D20_LARGE.equals(e.getActionCommand())) {
			numFaces = 20;
		} else if (DiceToolBar.ICON_D30_LARGE.equals(e.getActionCommand())) {
			numFaces = 30;
		} else if (DiceToolBar.ICON_D100_LARGE.equals(e.getActionCommand())) {
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
		return Helpers.getIcon(path, 240, 240);
	}
}