package net.alteiar.campaign.player.gui.factory;

import javax.swing.JPanel;

import net.alteiar.character.CharacterBean;

public abstract class PanelCharacterFactory {

	public abstract JPanel buildSmallCharacterSheet();

	public abstract JPanel buildCompleteCharacterSheet(CharacterBean character);
}
