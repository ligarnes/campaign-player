package net.alteiar.effectBean.mine;

public abstract class OneShotEffect extends MyEffect {
	private static final long serialVersionUID = 1L;

	private Boolean isActivated;

	public OneShotEffect() {
		isActivated = false;
	}

	@Override
	public final void activate() {
		if (!isActivated) {
			isActivated = true;
			singleActivate();
		}
	}

	protected abstract void singleActivate();

}
