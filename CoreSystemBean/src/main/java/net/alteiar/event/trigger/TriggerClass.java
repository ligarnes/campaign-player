package net.alteiar.event.trigger;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBeans;
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
	protected Boolean watch(BasicBeans bean) {
		return Beans.isInstanceOf(bean, typeOfActivator);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void beanChanged(BasicBeans bean) {
		triggerPropertyChange((E) bean);
	}

	protected abstract void triggerPropertyChange(E bean);

}
