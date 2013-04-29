package net.alteiar.trigger;

import java.beans.PropertyChangeEvent;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public class PositionTrigger extends TriggerBean {
	private static final long serialVersionUID = 1L;

	public PositionTrigger(ColoredShape areaOfActivation, UniqueID e,
			Class<? extends BasicBeans> typeBean, UniqueID mapId) {
		super(areaOfActivation, e, typeBean, mapId);
	}

	@Override
	public void triggerPropertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().contentEquals(
				MapElement.PROP_POSITION_PROPERTY)) {
			MapElement element = (MapElement) event.getSource();

			if (getBoundingBox().intersects(element.getBoundingBox())) {
				this.getEffect().activation();
				this.setIsActivate(true);
			} else {
				if (this.isActivate()) {
					this.getEffect().desactivate();
					this.setIsActivate(false);
				}
			}
		}
	}
}
