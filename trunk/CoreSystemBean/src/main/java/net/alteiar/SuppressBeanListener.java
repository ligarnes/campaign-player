package net.alteiar;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public interface SuppressBeanListener {
	UniqueID getBeanId();

	void beanRemoved(BasicBean bean);
}
