package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.client.CampaignClient;
import net.alteiar.client.ICampaignListener;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelListSimpleCharacter extends PanelList implements
		ICampaignListener {
	private static final long serialVersionUID = 1L;

	public PanelListSimpleCharacter() {
		super("Personnages");

		net.alteiar.client.CampaignClient.getInstance().addCampaignListener(
				this);

		for (CharacterClient character : CampaignClient.getInstance()
				.getCharacters()) {
			// characterAdded(character);
		}
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateCharacter(false);
	}

	@Override
	public void battleAdded(BattleClient battle) {
		// do not care
	}

	@Override
	public void battleRemoved(BattleClient battle) {
		// do not care
	}

	/*
	 * @Override public void characterAdded(CharacterClient character) {
	 * System.out.println("add: " + character.getName() + " | id: " +
	 * character.getId()); PanelSimpleCharacter panel = new
	 * PanelSimpleCharacter(character, false); this.addElement(character,
	 * panel); }
	 * 
	 * @Override public void characterRemoved(CharacterClient character) {
	 * System.out.println("remove: " + character.getName() + " | id: " +
	 * character.getId()); this.removeElement(character); }
	 * 
	 * @Override public void battleAdded(IBattleClient battle) { // Do not care
	 * }
	 * 
	 * @Override public void battleRemoved(IBattleClient battle) { // Do not
	 * care }
	 * 
	 * @Override public void playerAdded(IPlayerClient player) { // Do not care
	 * }
	 * 
	 * @Override public void playerRemoved(IPlayerClient player) { // Do not
	 * care }
	 * 
	 * @Override public void noteAdded(NoteClient note) { // Do not care }
	 * 
	 * @Override public void noteRemoved(NoteClient note) { // Do not care }
	 * 
	 * @Override public void monsterAdded(ICharacterSheetClient note) { // Do
	 * not care }
	 * 
	 * @Override public void monsterRemoved(ICharacterSheetClient note) { // Do
	 * not care }
	 */
}
