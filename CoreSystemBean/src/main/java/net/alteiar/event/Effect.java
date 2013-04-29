package net.alteiar.event;

import net.alteiar.client.bean.BasicBeans;

public abstract class Effect extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public Effect() {
		super();
	}

	public abstract void activate(BasicBeans bean);

	public abstract void desactivate(BasicBeans bean);

}
