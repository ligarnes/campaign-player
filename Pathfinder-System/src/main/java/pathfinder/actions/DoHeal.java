package pathfinder.actions;

import net.alteiar.server.document.character.CharacterClient;

public class DoHeal extends ChangeHealthPoint {

	public DoHeal(CharacterClient character) {
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
