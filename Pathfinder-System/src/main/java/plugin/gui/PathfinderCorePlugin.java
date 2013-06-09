package plugin.gui;

import java.beans.Beans;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.factory.newPlugin.ICorePlugin;
import net.alteiar.documents.character.Character;
import pathfinder.bean.unit.PathfinderCharacter;
import pathfinder.gui.general.PanelCharacterInfo;
import pathfinder.gui.general.PanelCompleteCharacterSheet;
import pathfinder.map.state.PathfinderDrawInfo;

public class PathfinderCorePlugin implements ICorePlugin {

	@Override
	public JPanel buildSmallCharacterSheet() {
		return new PanelCharacterInfo();
	}

	@Override
	public JPanel buildCompleteCharacterSheet(Character character) {
		PathfinderCharacter pathfinderCharacter = (PathfinderCharacter) Beans
				.getInstanceOf(character, PathfinderCharacter.class);
		return new PanelCompleteCharacterSheet(pathfinderCharacter);
	}

	@Override
	public DrawFilter getDrawInfo(MapEditableInfo mapInfo) {
		return new PathfinderDrawInfo(mapInfo);
	}

}
