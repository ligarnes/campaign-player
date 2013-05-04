package net.alteiar.event;

import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

public final class EffectSuite extends Effect {
	private static final long serialVersionUID = 1L;

	private static final String PROP_EFFECTS_PROPERTY = "effects";

	public static final String METH_ADD_EFFECT_METHOD = "addEffect";
	public static final String METH_REMOVE_EFFECT_METHOD = "removeEffect";

	private ArrayList<UniqueID> effects;

	public EffectSuite() {
		effects = new ArrayList<UniqueID>();
	}

	protected void setEffect(ArrayList<UniqueID> effects) {
		ArrayList<UniqueID> oldValue = this.effects;
		if (notifyRemote(PROP_EFFECTS_PROPERTY, oldValue, effects)) {
			this.effects = effects;
			propertyChangeSupport.firePropertyChange(PROP_EFFECTS_PROPERTY,
					oldValue, effects);
		}
	}

	public void addEffect(UniqueID effectId) {
		if (notifyRemote(METH_ADD_EFFECT_METHOD, null, effectId)) {
			synchronized (effects) {
				this.effects.add(effectId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_EFFECT_METHOD,
					null, effectId);
		}
	}

	public void removeEffect(UniqueID effectId) {
		if (notifyRemote(METH_REMOVE_EFFECT_METHOD, null, effectId)) {
			synchronized (effects) {
				this.effects.remove(effectId);
			}
			propertyChangeSupport.firePropertyChange(METH_REMOVE_EFFECT_METHOD,
					null, effectId);
		}
	}

	@Override
	public void activate(BasicBean bean) {
		for (UniqueID effectId : effects) {
			Effect effect = CampaignClient.getInstance().getBean(effectId);
			effect.activate(bean);
		}
	}

	@Override
	public void desactivate(BasicBean bean) {
		for (UniqueID effectId : effects) {
			Effect effect = CampaignClient.getInstance().getBean(effectId);
			effect.desactivate(bean);
		}
	}

}
