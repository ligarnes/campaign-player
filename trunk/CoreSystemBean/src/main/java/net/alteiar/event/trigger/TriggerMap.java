package net.alteiar.event.trigger;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class TriggerMap<E extends MapElement> extends TriggerClass<E> {
	private static final long serialVersionUID = 1L;

	private UniqueID mapId;

	public TriggerMap() {
	}

	public TriggerMap(UniqueID effect, Class<E> classes, UniqueID mapId) {
		super(effect, classes);
		this.mapId = mapId;
	}

	@Override
	protected Boolean watch(BasicBean bean) {
		if (super.watch(bean)) {
			MapElement element = (MapElement) Beans.getInstanceOf(bean,
					MapElement.class);
			if (mapId.equals(element.getMapId())) {
				return true;
			}
			return false;
		}
		return false;
	}
}
