package plugin.gui;

import net.alteiar.effectBean.gui.PanelEffectBuilder;

public class EffectMapElementFactory {
	static{
		PathfinderMapElementFactory.addBuilders(new PanelEffectBuilder());
	}

}
