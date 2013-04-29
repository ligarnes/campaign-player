package net.alteiar.effectBean;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.UniqueID;

public class BasicEffect extends Effect {
	private static final long serialVersionUID = 1L;

	public BasicEffect(ColoredShape shape, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(shape, oneUse, typeBean, mapId);
	}

	@Override
	public void activate() {
		System.out.println("Basic Effect Activated");
	}

	@Override
	public void desactivate() {
		System.out.println("Basic Effect desactivated");
		if (this.isOneUse()) {
			CampaignClient.getInstance().removeBean(this);
		}
	}

}
