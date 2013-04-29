package net.alteiar.effectBean;

import net.alteiar.CampaignClient;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public class VisibilityArea extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VisibilityArea(ColoredShape areaOfEffect, Boolean oneUse,
			Class<? extends MapElement> typeBean, UniqueID mapId) {
		super(areaOfEffect, oneUse, typeBean, mapId);
	}

	@Override
	public void activate() {
		for (UniqueID bean : this.getActOn()) {
			MapElement element = CampaignClient.getInstance().getBean(bean);
			if (this.contain(element.getCenterPosition())) {
				element.setHiddenForPlayer(false);
			}
		}
	}

	@Override
	public void desactivate() {
		for (UniqueID bean : this.getActOn()) {
			MapElement element = CampaignClient.getInstance().getBean(bean);
			if (this.contain(element.getCenterPosition())) {
				element.setHiddenForPlayer(true);
			}
		}
	}

}
