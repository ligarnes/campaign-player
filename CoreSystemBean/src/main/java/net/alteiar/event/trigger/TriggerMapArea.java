package net.alteiar.event.trigger;

import net.alteiar.CampaignClient;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public class TriggerMapArea extends TriggerMap<MapElement> {
	private static final long serialVersionUID = 1L;
	private UniqueID mapElementId;

	protected TriggerMapArea() {
	}

	public TriggerMapArea(UniqueID effect, MapElement element) {
		super(effect, MapElement.class, element.getMapId());

		this.mapElementId = element.getId();
	}

	protected MapElement getMapElement() {
		return CampaignClient.getInstance().getBean(mapElementId);
	}

	@Override
	protected void triggerPropertyChange(MapElement element) {
		if (element.equals(getMapElement())) {
			// it's our mapElement area
			return;
		}

		if (getMapElement().getBoundingBox().intersects(
				element.getBoundingBox())
				|| getMapElement().getBoundingBox().contains(
						element.getBoundingBox())) {
			if (!this.getActivate()) {
				this.getEffect().activate(element);
				this.setActivate(true);
			}
		} else if (this.getActivate()) {
			this.getEffect().desactivate(element);
			this.setActivate(false);
		}
	}

}
