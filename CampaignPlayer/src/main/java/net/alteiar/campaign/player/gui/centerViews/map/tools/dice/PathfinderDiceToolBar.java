package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import javax.swing.JButton;
import javax.swing.JToolBar;

import net.alteiar.campaign.player.Helpers;
import net.alteiar.dice.DiceSingle;

public class PathfinderDiceToolBar extends JToolBar {
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

	public PathfinderDiceToolBar() {

		DiceAction d4 = new DiceAction(new DiceSingle(4),
				Helpers.getIcon(ICON_D4_REDUCE));
		DiceAction d6 = new DiceAction(new DiceSingle(6),
				Helpers.getIcon(ICON_D6_REDUCE));
		DiceAction d8 = new DiceAction(new DiceSingle(8),
				Helpers.getIcon(ICON_D8_REDUCE));
		DiceAction d10 = new DiceAction(new DiceSingle(10),
				Helpers.getIcon(ICON_D10_REDUCE));
		DiceAction d12 = new DiceAction(new DiceSingle(12),
				Helpers.getIcon(ICON_D12_REDUCE));
		DiceAction d20 = new DiceAction(new DiceSingle(20),
				Helpers.getIcon(ICON_D20_REDUCE));
		DiceAction d100 = new DiceAction(new DiceSingle(100),
				Helpers.getIcon(ICON_D100_REDUCE));

		add(new JButton(d4));
		add(new JButton(d6));
		add(new JButton(d8));
		add(new JButton(d10));
		add(new JButton(d12));
		add(new JButton(d20));
		add(new JButton(d100));
	}
}
