package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.client.CampaignAdapter;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelListBattle extends PanelList {
	private static final long serialVersionUID = 1L;

	public PanelListBattle() {
		super("Combats");

		CampaignClient.getInstance()
				.addCampaignListener(new CampaignListener());

		for (BattleClient battle : CampaignClient.getInstance().getBattles()) {
			addBattle(battle);
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateBattle();
	}

	private void addBattle(BattleClient battle) {
		PanelSimpleBattle panel = new PanelSimpleBattle(battle);
		addElement(battle, panel);
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void battleAdded(BattleClient battle) {
			addBattle(battle);
		}

		@Override
		public void battleRemoved(BattleClient battle) {
			removeElement(battle);
		}
	}
}
