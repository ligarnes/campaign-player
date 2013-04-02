package plugin.gui;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.factory.IPluginSystemGui;
import net.alteiar.campaign.player.gui.factory.PanelCharacterFactory;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import pathfinder.gui.document.PanelCreateCharacter;
import pathfinder.gui.document.PanelCreateImage;

public class PluginSystemGui implements IPluginSystemGui {

	@Override
	public PanelCharacterFactory getGuiCharacterFactory() {
		return new PathfinderPanelCharacterFactory();
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		return new PathfinderMapElementFactory().getBuilders();
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> documentsBuilder = new ArrayList<PanelDocumentBuilder>();
		documentsBuilder.add(new pathfinder.gui.document.PanelCreateBattle());
		documentsBuilder.add(new PanelCreateImage());
		documentsBuilder.add(new PanelCreateCharacter());
		return documentsBuilder;
	}
}
