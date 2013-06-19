package net.alteiar.campaign.player.gui.factory.newPlugin;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;

public interface ICorePlugin {

	JPanel buildSmallCharacterSheet();

	DrawFilter getDrawInfo(MapEditableInfo mapInfo);
}
