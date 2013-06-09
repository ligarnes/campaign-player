package net.alteiar.campaign.player.gui.factory.newPlugin;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.documents.character.Character;

public interface ICorePlugin {

	JPanel buildSmallCharacterSheet();

	JPanel buildCompleteCharacterSheet(Character character);

	DrawFilter getDrawInfo(MapEditableInfo mapInfo);
}
