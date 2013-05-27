package pathfinder.actions;

import pathfinder.bean.unit.Unit;

public class DoDamage extends ChangeHealthPoint {

	public DoDamage(Unit character) {
		super(character);
	}

	@Override
	public String getName() {
		return "D\u00E9gat";
	}

	@Override
	protected void changeHealPoint(Integer healthPointModifier) {
		getCharacter().setCurrentHp(
				getCharacter().getCurrentHp() - healthPointModifier);
	}

}
