package net.alteiar.client;

import net.alteiar.client.bean.BasicBean;

public interface DocumentManagerListener {

	/**
	 * call when a bean is loaded
	 * 
	 * @param bean
	 */
	void beanAdded(BasicBean bean);

	void beanRemoved(BasicBean bean);
}
