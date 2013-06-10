package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import net.alteiar.campaign.player.Helpers;

public class PathfinderDiceToolBar extends JToolBar implements DiceBagBuilder {
	private static final long serialVersionUID = 1L;

	public static final String ICON_D4_REDUCE = "d4-reduce.png";
	public static final String ICON_D6_REDUCE = "d6-reduce.png";
	public static final String ICON_D8_REDUCE = "d8-reduce.png";
	public static final String ICON_D10_REDUCE = "d10-reduce.png";
	public static final String ICON_D12_REDUCE = "d12-reduce.png";
	public static final String ICON_D20_REDUCE = "d20-reduce.png";
	public static final String ICON_D100_REDUCE = "d100-reduce.png";

	private final JSpinner spinnerDiceCount;
	private final JSpinner spinnerModifier;

	public PathfinderDiceToolBar() {
		DiceBagAction d4 = new DiceBagAction(this, 4,
				Helpers.getIcon(ICON_D4_REDUCE));

		DiceBagAction d6 = new DiceBagAction(this, 6,
				Helpers.getIcon(ICON_D6_REDUCE));
		DiceBagAction d8 = new DiceBagAction(this, 8,
				Helpers.getIcon(ICON_D8_REDUCE));
		DiceBagAction d10 = new DiceBagAction(this, 10,
				Helpers.getIcon(ICON_D10_REDUCE));
		DiceBagAction d12 = new DiceBagAction(this, 12,
				Helpers.getIcon(ICON_D12_REDUCE));
		DiceBagAction d20 = new DiceBagAction(this, 20,
				Helpers.getIcon(ICON_D20_REDUCE));
		DiceBagAction d100 = new DiceBagAction(this, 100,
				Helpers.getIcon(ICON_D100_REDUCE));

		JPanel dices = new JPanel(new FlowLayout());
		dices.add(new ButtonCustom(d4));
		dices.add(new ButtonCustom(d6));
		dices.add(new ButtonCustom(d8));
		dices.add(new ButtonCustom(d10));
		dices.add(new ButtonCustom(d12));
		dices.add(new ButtonCustom(d20));
		dices.add(new ButtonCustom(d100));

		JPanel panel = new JPanel(new GridLayout(2, 1));
		FlowLayout fl_panelDiceCount = new FlowLayout();
		fl_panelDiceCount.setAlignment(FlowLayout.RIGHT);
		JPanel panelDiceCount = new JPanel(fl_panelDiceCount);
		panelDiceCount.add(new JLabel("nb dés:"));
		FlowLayout fl_panelModifier = new FlowLayout();
		fl_panelModifier.setAlignment(FlowLayout.RIGHT);
		JPanel panelModifier = new JPanel(fl_panelModifier);
		panelModifier.add(new JLabel("Mod. "));

		panel.add(panelDiceCount);

		spinnerDiceCount = new JSpinner();
		spinnerDiceCount.setPreferredSize(new Dimension(40, 20));
		spinnerDiceCount.setModel(new SpinnerNumberModel(1, 1, null, 1));
		panelDiceCount.add(spinnerDiceCount);
		panel.add(panelModifier);

		spinnerModifier = new JSpinner();
		spinnerModifier.setPreferredSize(new Dimension(40, 20));
		spinnerModifier.setModel(new SpinnerNumberModel(0, null, null, 1));
		panelModifier.add(spinnerModifier);

		add(panel);
		add(dices);
	}

	@Override
	public Integer getDiceCount() {
		return (Integer) spinnerDiceCount.getValue();
	}

	@Override
	public Integer getModifier() {
		return (Integer) spinnerModifier.getValue();
	}
}
