package net.alteiar.effectBean.mine;

import net.alteiar.client.bean.BasicBean;

public abstract class MyEffect extends BasicBean {
	private static final long serialVersionUID = 1L;

	public MyEffect() {
		super();
	}

	public abstract void activate();

	public abstract void desactivate();
}
