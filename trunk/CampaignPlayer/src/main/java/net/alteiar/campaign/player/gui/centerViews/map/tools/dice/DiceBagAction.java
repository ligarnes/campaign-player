package net.alteiar.campaign.player.gui.centerViews.map.tools.dice;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceSingle;
import net.alteiar.campaign.CampaignClient;

public class DiceBagAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private final DiceBagBuilder builder;
	private final Integer dice;

	public DiceBagAction(DiceBagBuilder builder, Integer dice, ImageIcon icon) {

		this.builder = builder;
		this.dice = dice;

		putValue(LARGE_ICON_KEY, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DiceBag diceBag = new DiceBag(builder.getModifier());

		for (int i = 0; i < builder.getDiceCount(); i++) {
			diceBag.addDice(new DiceSingle(dice));
		}

		CampaignClient.getInstance().getDiceRoller().roll(diceBag);
	}
}
