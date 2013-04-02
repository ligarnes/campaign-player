package plugin.gui;

import java.beans.Beans;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.documents.character.CharacterBean;
import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.general.PanelCharacterInfo;
import pathfinder.gui.general.PanelCompleteCharacterSheet;

public class PathfinderPanelCharacterFactory extends PanelCharacterFactory {

	@Override
	public JPanel buildSmallCharacterSheet() {
		return new PanelCharacterInfo();
	}

	@Override
	public JPanel buildCompleteCharacterSheet(CharacterBean character) {
		PathfinderCharacter pathfinderCharacter = (PathfinderCharacter) Beans
				.getInstanceOf(character, PathfinderCharacter.class);
		return new PanelCompleteCharacterSheet(pathfinderCharacter);
	}
}
