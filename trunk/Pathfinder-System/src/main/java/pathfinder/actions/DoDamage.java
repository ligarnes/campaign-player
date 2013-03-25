package pathfinder.actions;

import pathfinder.character.PathfinderCharacter;

public class DoDamage extends ChangeHealthPoint {

	public DoDamage(PathfinderCharacter character) {
		super(character);
	}

	@Override
	public String getName() {
		return "Dégat";
	}

	@Override
	protected void changeHealPoint(Integer healthPointModifier) {
		getCharacter().setCurrentHp(
				getCharacter().getCurrentHp() - healthPointModifier);
	}

}
