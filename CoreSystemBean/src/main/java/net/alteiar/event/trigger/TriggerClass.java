package net.alteiar.event.trigger;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public abstract class TriggerClass<E> extends Trigger {
	private static final long serialVersionUID = 1L;

	private Class<E> typeOfActivator;

	public TriggerClass() {
		super();
	}

	public TriggerClass(UniqueID effect, Class<E> classes) {
		super(effect);
		typeOfActivator = classes;
	}

	@Override
	protected Boolean watch(BasicBean bean) {
		return Beans.isInstanceOf(bean, typeOfActivator);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void beanChanged(BasicBean bean) {
		triggerPropertyChange((E) bean);
	}

	protected abstract void triggerPropertyChange(E bean);

}
