package pathfinder.effect;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.event.Effect;
import pathfinder.bean.unit.PathfinderCharacter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public abstract class TrapEffect extends Effect {
	private static final long serialVersionUID = 1L;

	@Override
	public void activate(BasicBean bean) {
		if (Beans.isInstanceOf(bean, PathfinderCharacterElement.class)) {
			trapActivated(((PathfinderCharacterElement) bean).getCharacter());
		}
	}

	@Override
	public void desactivate(BasicBean bean) {

	}

	protected abstract void trapActivated(PathfinderCharacter character);
}
