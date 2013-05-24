package pathfinder.actions;

import pathfinder.bean.unit.PathfinderCharacter;

public class DoHeal extends ChangeHealthPoint {

	public DoHeal(PathfinderCharacter character) {
		super(character);
	}

	@Override
	public String getName() {
		return "Soins";
	}

	@Override
	protected void changeHealPoint(Integer healthPointModifier) {
		getCharacter().setCurrentHp(
				getCharacter().getCurrentHp() + healthPointModifier);
	}

}
