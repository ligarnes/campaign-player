package net.alteiar.newversion.client;

import net.alteiar.newversion.shared.bean.BasicBean;

public interface DocumentManagerListener {

	/**
	 * call when a bean is loaded
	 * 
	 * @param bean
	 */
	void beanAdded(BasicBean bean);

	void beanRemoved(BasicBean bean);
}
