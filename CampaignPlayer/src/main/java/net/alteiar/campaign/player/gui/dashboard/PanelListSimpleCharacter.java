package net.alteiar.campaign.player.gui.dashboard;

import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.client.shared.campaign.battle.IBattleClient;
import net.alteiar.client.shared.campaign.character.ICharacterSheetClient;
import net.alteiar.client.shared.campaign.notes.NoteClient;
import net.alteiar.client.shared.campaign.player.IPlayerClient;
import net.alteiar.client.shared.observer.campaign.ICampaignObserver;

public class PanelListSimpleCharacter extends PanelList implements
		ICampaignObserver {
	private static final long serialVersionUID = 1L;

	public PanelListSimpleCharacter() {
		super("Personnages");

		CampaignClient.INSTANCE.addCampaignListener(this);

		for (ICharacterSheetClient character : CampaignClient.INSTANCE
				.getAllCharacter()) {
			panels.put(character, new PanelSimpleCharacter(character, false));
		}

		panelCreate = new PanelCreateCharacter(false);

		this.initialize();
	}

	@Override
	public void characterAdded(ICharacterSheetClient character) {
		PanelSimpleCharacter panel = new PanelSimpleCharacter(character, false);
		this.addElement(character, panel);
	}

	@Override
	public void characterRemoved(ICharacterSheetClient character) {
		this.removeElement(character);
	}

	@Override
	public void battleAdded(IBattleClient battle) {
		// Do not care
	}

	@Override
	public void battleRemoved(IBattleClient battle) {
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
