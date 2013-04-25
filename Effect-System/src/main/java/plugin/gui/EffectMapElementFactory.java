package plugin.gui;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.effectBean.gui.Effect.PanelEffectBuilder;
import net.alteiar.effectBean.gui.trigger.PanelTriggerBuilder;

public class EffectMapElementFactory {
	protected static final ArrayList<PanelMapElementBuilder> panelBuilder = new ArrayList<PanelMapElementBuilder>();

	static {
		panelBuilder.add(new PanelTriggerBuilder());
		panelBuilder.add(new PanelEffectBuilder());
	}

	public ArrayList<PanelMapElementBuilder> getBuilders() {
		return panelBuilder;
	}
	
	static public void addBuilders(PanelMapElementBuilder panel) {
		panelBuilder.add(panel);
	}

}
