package pathfinder.actions;

import net.alteiar.server.document.character.CharacterClient;

public class DoDamage extends ChangeHealthPoint {

	public DoDamage(CharacterClient character) {
		super(character);
	}

	@Override
	public String getName() {
		return "Dégat";
	}

	@Override
	protected void changeHealPoint(Integer healthPointModifier) {
		getCharacter()
				.setCurrentHp(getCharacter().getCurrentHp() - healthPointModifier);
	}

}
