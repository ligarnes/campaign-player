package net.alteiar;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public interface WaitBeanListener {
	UniqueID getBeanId();

	void beanReceived(BasicBean bean);
}
