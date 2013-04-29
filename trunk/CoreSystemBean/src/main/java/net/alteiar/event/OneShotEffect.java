package net.alteiar.event;

import net.alteiar.client.bean.BasicBeans;

public abstract class OneShotEffect extends Effect {
	private static final long serialVersionUID = 1L;

	private Boolean isActivated;

	public OneShotEffect() {
		isActivated = false;
	}

	@Override
	public final void activate(BasicBeans bean) {
		if (!isActivated) {
			isActivated = true;
			singleActivate(bean);
		}
	}

	protected abstract void singleActivate(BasicBeans bean);

}
