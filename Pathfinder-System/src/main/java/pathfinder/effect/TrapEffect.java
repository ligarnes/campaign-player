package pathfinder.effect;

import java.beans.Beans;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.event.Effect;
import pathfinder.character.PathfinderCharacter;
import pathfinder.gui.mapElement.PathfinderCharacterElement;

public abstract class TrapEffect extends Effect {
	private static final long serialVersionUID = 1L;

	@Override
	public void activate(BasicBeans bean) {
		if (Beans.isInstanceOf(bean, PathfinderCharacterElement.class)) {
			trapActivated(((PathfinderCharacterElement) bean).getCharacter());
		}
	}

	@Override
	public void desactivate(BasicBeans bean) {

	}

	protected abstract void trapActivated(PathfinderCharacter character);
}
