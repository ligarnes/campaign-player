package net.alteiar.campaign.player.gui.dashboard;

import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.client.shared.observer.campaign.ICampaignObserver;

public class PanelListBattle extends PanelList implements ICampaignObserver {
	private static final long serialVersionUID = 1L;

	public PanelListBattle() {
		super("Combats");

		CampaignClient.INSTANCE.addCampaignListener(this);

		for (IBattleClient battle : CampaignClient.INSTANCE.getBattles()) {
			panels.put(battle, new PanelSimpleBattle(battle));
		}

		panelCreate = new PanelCreateBattle();

		this.initialize();
	}

	@Override
	public void battleAdded(IBattleClient battle) {
		PanelSimpleBattle panel = new PanelSimpleBattle(battle);
		this.addElement(battle, panel);
	}

	@Override
	public void battleRemoved(IBattleClient battle) {
		this.removeElement(battle);
	}

	@Override
	public void characterAdded(ICharacterSheetClient character) {
		// Do not care
	}

	@Override
	public void characterRemoved(ICharacterSheetClient character) {
		// Do not care
	}

	@Override
	public void playerAdded(IPlayerClient player) {
		// Do not care
	}

	@Override
	public void playerRemoved(IPlayerClient player) {
		// Do not care
	}

	@Override
	public void noteAdded(NoteClient note) {
		// Do not care
	}

	@Override
	public void noteRemoved(NoteClient note) {
		// Do not care
	}

	@Override
	public void monsterAdded(ICharacterSheetClient note) {
		// Do not care
	}

	@Override
	public void monsterRemoved(ICharacterSheetClient note) {
		// Do not care
	}
}
