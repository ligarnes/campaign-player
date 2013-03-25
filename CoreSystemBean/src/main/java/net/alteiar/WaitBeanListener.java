package net.alteiar;

import net.alteiar.client.bean.BasicBeans;

public interface WaitBeanListener {
	Long getBeanId();

	void beanReceived(BasicBeans bean);
}
