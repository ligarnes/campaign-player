package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.client.CampaignClient;
import net.alteiar.client.ICampaignListener;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelListBattle extends PanelList implements ICampaignListener {
	private static final long serialVersionUID = 1L;

	public PanelListBattle() {
		super("Combats");

		net.alteiar.client.CampaignClient.getInstance().addCampaignListener(
				this);

		for (BattleClient battle : CampaignClient.getInstance().getBattles()) {
			battleAdded(battle);
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateBattle();
	}

	@Override
	public void battleAdded(BattleClient battle) {
		PanelSimpleBattle panel = new PanelSimpleBattle(battle);
		this.addElement(battle, panel);
	}

	@Override
	public void battleRemoved(BattleClient battle) {
		this.removeElement(battle);
	}
}
