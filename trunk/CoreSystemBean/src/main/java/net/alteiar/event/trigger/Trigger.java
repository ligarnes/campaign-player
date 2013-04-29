package net.alteiar.event.trigger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.event.Effect;
import net.alteiar.shared.UniqueID;

public abstract class Trigger extends BasicBeans implements
		PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private Boolean activate;
	private UniqueID effectId;

	public Trigger(UniqueID effect) {
		this.activate = false;
		this.effectId = effect;
	}

	public Trigger() {
		this.activate = false;
		this.effectId = null;
	}

	public UniqueID getEffectId() {
		return effectId;
	}

	public void setEffectId(UniqueID effect) {
		this.effectId = effect;
	}

	protected Effect getEffect() {
		return CampaignClient.getInstance().getBean(effectId);
	}

	protected void setActivate(Boolean isActivate) {
		this.activate = isActivate;
	}

	public Boolean getActivate() {
		return this.activate;
	}

	/**
	 * Called when a bean is added to the system
	 * 
	 * @param bean
	 */
	public void beanAdded(BasicBeans bean) {
		if (watch(bean)) {
			bean.addPropertyChangeListener(this);
			beanChanged(bean);
		}
	}

	/**
	 * called when a bean is removed from the system
	 * 
	 * @param bean
	 */
	public void beanRemoved(BasicBeans bean) {
		bean.removePropertyChangeListener(this);
		beanChanged(bean);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		beanChanged((BasicBeans) evt.getSource());
	}

	/**
	 * 
	 * @param bean
	 * @return true if you need to watch this bean
	 */
	protected abstract Boolean watch(BasicBeans bean);

	/**
	 * called when a bean you watch is changed
	 * 
	 * @param bean
	 */
	protected abstract void beanChanged(BasicBeans bean);
}
