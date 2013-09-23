package shadowrun.gui.adapter;

import java.beans.PropertyChangeListener;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;
import net.alteiar.beans.dice.DiceBag;
import net.alteiar.beans.dice.DiceSingle;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

import shadowrun.bean.unit.ShadowrunCharacter;

public class ShadowrunCharacterCombatTraker extends CombatTrackerUnit {
	private static final long serialVersionUID = 1L;

	@Element
	private UniqueID characterId;

	public ShadowrunCharacterCombatTraker(ShadowrunCharacter character) {
		this(character.getId());
	}

	public ShadowrunCharacterCombatTraker(UniqueID characterId) {
		this.characterId = characterId;

	}

	public UniqueID getCharacterId() {
		return characterId;
	}

	public void setCharacterId(UniqueID characterId) {
		this.characterId = characterId;
	}

	private ShadowrunCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(characterId);
	}

	@Override
	public String getName(boolean isDM) {
		return getCharacter().getName();
	}

	@Override
	public int getCurrentHp() {
		return getCharacter().getPhysicalPoint()
				- getCharacter().getPhysicalDamage();
	}

	@Override
	public void doDamage(int value) {

		getCharacter().setPhysicalDamage(
				getCharacter().getPhysicalDamage() + value);
	}

	@Override
	public void doHeal(int value) {
		getCharacter().setPhysicalDamage(
				getCharacter().getPhysicalDamage() - value);
	}

	@Override
	public void rollInitiative() {

		DiceBag bag = new DiceBag();
		int init = getCharacter().getInitiative();

		for (int i = 0; i < init; i++) {
			bag.addDice(new DiceSingle(6));
		}

		CampaignClient.getInstance().getDiceRoller().roll(bag);
		setInitiative(bag.getTotal());
	}

	@Override
	public void addCombatTrackerChangeListener(PropertyChangeListener listener) {
		this.getCharacter().addPropertyChangeListener(listener);
		this.addPropertyChangeListener(listener);
	}

	@Override
	public void removeCombatTrackerChangeListener(
			PropertyChangeListener listener) {
		this.getCharacter().removePropertyChangeListener(listener);
		this.removePropertyChangeListener(listener);
	}

}
