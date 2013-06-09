package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.alteiar.CampaignClient;
import net.alteiar.dice.DiceBag;
import net.alteiar.dice.DiceSingle;

public class DiceAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public DiceBag diceBag;

	public DiceAction(DiceSingle dice, ImageIcon icon) {
		this(new DiceBag(dice), icon);
	}

	public DiceAction(DiceBag diceBag, ImageIcon icon) {
		this.diceBag = diceBag;

		putValue(LARGE_ICON_KEY, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CampaignClient.getInstance().getDiceRoller().roll(diceBag);
	}

}
