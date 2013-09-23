package shadowrun.actions;

import shadowrun.bean.unit.ShadowrunCharacter;

public class DoDamage extends ChangeHealthPoint {

	public DoDamage(ShadowrunCharacter character) {
		super(character);
	}

	@Override
	public String getName() {
		return "D\u00E9gat";
	}

	@Override
	protected void changePhysicalPoint(Integer healthPointModifier) {
		getCharacter().setPhysicalDamage(
				getCharacter().getPhysicalDamage() + healthPointModifier);
	}

	@Override
	protected void changeStunPoint(Integer healthPointModifier) {
		getCharacter().setStunDamage(
				getCharacter().getStunDamage() + healthPointModifier);
	}
}
