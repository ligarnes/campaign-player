package shadowrun.actions;

import shadowrun.bean.unit.ShadowrunCharacter;

public class DoHeal extends ChangeHealthPoint {

	public DoHeal(ShadowrunCharacter character) {
		super(character);
	}

	@Override
	public String getName() {
		return "Soins";
	}

	@Override
	protected void changePhysicalPoint(Integer healthPointModifier) {
		getCharacter().setPhysicalDamage(
				getCharacter().getPhysicalDamage() - healthPointModifier);
	}

	@Override
	protected void changeStunPoint(Integer healthPointModifier) {
		getCharacter().setStunDamage(
				getCharacter().getStunDamage() - healthPointModifier);
	}

}
