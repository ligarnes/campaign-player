package pathfinder.gui.mapElement.builder;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.IPanelBuilders;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;

public class PanelBuilder implements IPanelBuilders {

	private static final ArrayList<PanelMapElementBuilder> panelBuilder = new ArrayList<PanelMapElementBuilder>();

	static {
		panelBuilder.add(new PanelCircleBuilder());
		panelBuilder.add(new PanelRectangleBuilder());
		panelBuilder.add(new PanelCharacterBuilder());
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getBuilders() {
		return panelBuilder;
	}
}
