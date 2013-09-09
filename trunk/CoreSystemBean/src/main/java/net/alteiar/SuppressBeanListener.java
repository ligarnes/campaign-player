package net.alteiar;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public interface SuppressBeanListener {
	UniqueID getBeanId();

	void beanRemoved(BasicBean bean);
}
