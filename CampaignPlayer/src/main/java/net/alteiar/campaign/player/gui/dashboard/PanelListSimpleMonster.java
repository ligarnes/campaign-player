package net.alteiar.campaign.player.gui.dashboard;

import javax.swing.JPanel;

import net.alteiar.client.ICampaignListener;
import net.alteiar.server.document.map.battle.BattleClient;

public class PanelListSimpleMonster extends PanelList implements
		ICampaignListener {
	private static final long serialVersionUID = 1L;

	public PanelListSimpleMonster() {
		super("Monstres");

		net.alteiar.client.CampaignClient.getInstance().addCampaignListener(
				this);
		/*
		 * for (ICharacterSheetClient character :
		 * CampaignClient.getInstance().getMonsters) { monsterAdded(character);
		 * }
		 */
	}

	@Override
	protected JPanel createPanelCreate() {
		return new PanelCreateCharacter(true);
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
	 * @Override public void monsterAdded(ICharacterSheetClient character) {
	 * PanelSimpleCharacter panel = new PanelSimpleCharacter(character, true);
	 * this.addElement(character, panel); }
	 * 
	 * @Override public void monsterRemoved(ICharacterSheetClient character) {
	 * this.removeElement(character); }
	 * 
	 * @Override public void characterAdded(ICharacterSheetClient character) {
	 * // Do not care }
	 * 
	 * @Override public void characterRemoved(ICharacterSheetClient note) { //
	 * Do not care }
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
	 */
}
