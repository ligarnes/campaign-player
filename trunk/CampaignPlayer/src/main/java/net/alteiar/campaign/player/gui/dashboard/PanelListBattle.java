package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.CampaignAdapter;
import net.alteiar.CampaignClient;
import net.alteiar.map.battle.Battle;

public class PanelListBattle extends PanelList {
	private static final long serialVersionUID = 1L;

	public PanelListBattle() {
		super("Combats");

		CampaignClient.getInstance()
				.addCampaignListener(new CampaignListener());

		for (Battle battle : CampaignClient.getInstance().getBattles()) {
			addBattle(battle);
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateBattle();
	}

	private void addBattle(Battle battle) {
		PanelSimpleBattle panel = new PanelSimpleBattle(battle);
		addElement(battle, panel);
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void battleAdded(Battle battle) {
			addBattle(battle);
		}

		@Override
		public void battleRemoved(Battle battle) {
			removeElement(battle);
		}
	}
}
