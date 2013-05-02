package pathfinder.effect;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.event.trigger.TriggerMapArea;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public class TriggerCharacter extends TriggerMapArea {
	private static final long serialVersionUID = 1L;

	public TriggerCharacter() {

	}

	public TriggerCharacter(UniqueID effect, MapElement element) {
		super(effect, element);
	}

	@Override
	protected Boolean watch(BasicBean bean) {
		if (super.watch(bean)) {
			if (Beans.isInstanceOf(bean, PathfinderCharacterElement.class)) {
				return true;
			}
		}
		return false;
	}

}
