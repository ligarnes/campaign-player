package pathfinder.gui.builder;

import net.alteiar.campaign.player.gui.map.element.IPanelBuilders;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;

public class PanelBuilder implements IPanelBuilders {

	private final PanelMapElementBuilder[] panelBuilder = new PanelMapElementBuilder[] {
			new PanelCircleBuilder(), new PanelRectangleBuilder(), };

	@Override
	public PanelMapElementBuilder[] getBuilders() {
		return panelBuilder;
	}
}
