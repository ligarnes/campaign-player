package net.alteiar.campaign.player.gui.factory.newPlugin;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.documents.character.Character;

public interface ICorePlugin {

	JPanel buildSmallCharacterSheet();

	JPanel buildCompleteCharacterSheet(Character character);

	DrawInfo getDrawInfo(MapEditableInfo mapInfo);
}
