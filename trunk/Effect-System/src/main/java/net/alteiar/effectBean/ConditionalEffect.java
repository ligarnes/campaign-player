package net.alteiar.effectBean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.shared.UniqueID;

public class ConditionalEffect extends EffectSuite implements
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	public ConditionalEffect(ColoredShape shape, Boolean oneUse,
			Class<? extends BasicBeans> typeBean, UniqueID mapId)
			throws ClassNotFoundException {
		super(shape, oneUse, typeBean, mapId);
	}

	public void activate(Boolean isFullfilled) {
		if (isFullfilled) {
			effects.get(0).activate();
		} else {
			effects.get(1).activate();
		}
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		Boolean fullfillment = (Boolean) arg0.getNewValue();
		this.activate(fullfillment);
	}

}
