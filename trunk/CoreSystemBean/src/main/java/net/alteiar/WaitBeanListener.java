package net.alteiar;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.shared.UniqueID;

public interface WaitBeanListener {
	UniqueID getBeanId();

	void beanReceived(BasicBeans bean);
}
