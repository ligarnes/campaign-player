package net.alteiar.effectBean.mine;

import net.alteiar.client.bean.BasicBeans;

public abstract class MyEffect extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public MyEffect() {
		super();
	}

	public abstract void activate();

	public abstract void desactivate();
}
