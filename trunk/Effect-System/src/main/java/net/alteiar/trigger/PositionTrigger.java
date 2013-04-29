package net.alteiar.trigger;

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
	public void triggerPropertyChange(MapElement element) {
		if (getBoundingBox().intersects(element.getBoundingBox())
				|| getBoundingBox().contains(element.getBoundingBox())) {
			System.out.println(getBoundingBox() + " == "
					+ element.getBoundingBox());
			if (!this.isActivate()) {
				this.getEffect().activate();
				this.setIsActivate(true);
			}
		} else if (this.isActivate()) {
			this.getEffect().desactivate();
			this.setIsActivate(false);
		}
	}
}
