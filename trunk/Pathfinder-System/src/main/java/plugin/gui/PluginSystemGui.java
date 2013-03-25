package plugin.gui;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;

public class PluginSystemGui implements IPluginSystemGui {

	@Override
	public PanelCharacterFactory getGuiCharacterFactory() {
		return new PathfinderPanelCharacterFactory();
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return new PathfinderMapElementFactory().getBuilders();
	}
}
