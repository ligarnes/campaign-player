package net.alteiar.campaign.player.gui.factory;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;

public interface IPluginSystemGui {

	PanelCharacterFactory getGuiCharacterFactory();

	ArrayList<PanelMapElementBuilder> getGuiMapElementFactory();
}
