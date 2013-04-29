package net.alteiar.client;

import net.alteiar.client.bean.BasicBeans;

public interface DocumentManagerListener {

	/**
	 * call when a bean is loaded
	 * 
	 * @param bean
	 */
	void beanAdded(BasicBeans bean);

	void beanRemoved(BasicBeans bean);
}
