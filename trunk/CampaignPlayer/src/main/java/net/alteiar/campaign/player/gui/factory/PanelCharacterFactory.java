package net.alteiar.campaign.player.gui.factory;

import javax.swing.JPanel;

import net.alteiar.documents.character.Character;

public abstract class PanelCharacterFactory {

	public abstract JPanel buildSmallCharacterSheet();

	public abstract JPanel buildCompleteCharacterSheet(Character character);
}
