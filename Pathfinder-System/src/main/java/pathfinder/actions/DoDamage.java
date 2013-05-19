package pathfinder.actions;

import pathfinder.bean.character.PathfinderCharacter;

public class DoDamage extends ChangeHealthPoint {

	public DoDamage(PathfinderCharacter character) {
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
