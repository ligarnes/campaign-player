package pathfinder.effect;

import pathfinder.bean.unit.PathfinderCharacter;

public class FireTrapEffect extends TrapEffect {
	private static final long serialVersionUID = 1L;

	public FireTrapEffect() {

	}

	@Override
	protected void trapActivated(PathfinderCharacter character) {
		int currentHp = character.getCurrentHp() - 10;
		character.setCurrentHp(currentHp);
	}
}
