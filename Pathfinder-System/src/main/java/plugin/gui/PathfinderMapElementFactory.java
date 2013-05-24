package plugin.gui;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import pathfinder.gui.mapElement.builder.PanelCharacterBuilder;
import pathfinder.gui.mapElement.builder.PanelCircleBuilder;
import pathfinder.gui.mapElement.builder.PanelMonsterBuilder;
import pathfinder.gui.mapElement.builder.PanelRectangleBuilder;

public class PathfinderMapElementFactory {

	private static final ArrayList<PanelMapElementBuilder> panelBuilder = new ArrayList<PanelMapElementBuilder>();

	static {
		panelBuilder.add(new PanelCircleBuilder());
		panelBuilder.add(new PanelRectangleBuilder());
		panelBuilder.add(new PanelCharacterBuilder());
		panelBuilder.add(new PanelMonsterBuilder());
	}

	public ArrayList<PanelMapElementBuilder> getBuilders() {
		return panelBuilder;
	}
}
