package net.alteiar.event;

import net.alteiar.client.bean.BasicBean;

public abstract class Effect extends BasicBean {
	private static final long serialVersionUID = 1L;

	public Effect() {
		super();
	}

	public abstract void activate(BasicBean bean);

	public abstract void desactivate(BasicBean bean);

}
