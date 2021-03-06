package net.alteiar.effectBean.mine;

import java.beans.PropertyVetoException;
import java.util.ArrayList;

import net.alteiar.CampaignClient;
import net.alteiar.shared.UniqueID;

public final class EffectSuite extends MyEffect {
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
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_EFFECTS_PROPERTY, oldValue, effects);
			this.effects = effects;
			propertyChangeSupport.firePropertyChange(PROP_EFFECTS_PROPERTY,
					oldValue, effects);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public void addEffect(UniqueID effectId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_ADD_EFFECT_METHOD, null, effectId);
			synchronized (effects) {
				this.effects.add(effectId);
			}
			propertyChangeSupport.firePropertyChange(METH_ADD_EFFECT_METHOD,
					null, effectId);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public void removeEffect(UniqueID effectId) {
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					METH_REMOVE_EFFECT_METHOD, null, effectId);
			synchronized (effects) {
				this.effects.remove(effectId);
			}
			propertyChangeSupport.firePropertyChange(METH_REMOVE_EFFECT_METHOD,
					null, effectId);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	@Override
	public void activate() {
		System.out.println("activate effects: " + effects.size());
		for (UniqueID effectId : effects) {
			MyEffect effect = CampaignClient.getInstance().getBean(effectId);
			effect.activate();
		}
	}

	@Override
	public void desactivate() {
		for (UniqueID effectId : effects) {
			MyEffect effect = CampaignClient.getInstance().getBean(effectId);
			effect.desactivate();
		}
	}

}
