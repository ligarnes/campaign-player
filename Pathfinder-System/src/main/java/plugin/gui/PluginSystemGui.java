package plugin.gui;

import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;

public class PluginSystemGui implements IPluginSystemGui {

	@Override
	public PanelCharacterFactory getGuiCharacterFactory() {
		return new PathfinderPanelCharacterFactory();
	}
}
