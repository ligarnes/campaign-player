package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.client.CampaignAdapter;
import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;

public class PanelListSimpleCharacter extends PanelList {
	private static final long serialVersionUID = 1L;

	public PanelListSimpleCharacter() {
		super("Personnages");

		CampaignClient.getInstance()
				.addCampaignListener(new CampaignListener());

		for (CharacterClient character : CampaignClient.getInstance()
				.getCharacters()) {
			addCharacter(character);
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateCharacter(false);
	}

	private void addCharacter(CharacterClient character) {
		PanelSimpleCharacter panel = new PanelSimpleCharacter(character, false);
		addElement(character, panel);
	}

	private class CampaignListener extends CampaignAdapter {
		@Override
		public void characterAdded(CharacterClient character) {
			addCharacter(character);
		}

		@Override
		public void characterRemoved(CharacterClient character) {
			removeElement(character);
		}
	}
}
