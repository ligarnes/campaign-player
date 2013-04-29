package net.alteiar.effectBean;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.UniqueID;

public class IdleEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdleEffect(ColoredShape shape, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(shape, oneUse, typeBean, mapId);
	}

	@Override
	public void activate() {
	}

	@Override
	public void desactivate() {
	}

}
