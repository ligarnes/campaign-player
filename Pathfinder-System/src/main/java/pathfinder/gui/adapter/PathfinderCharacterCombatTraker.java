package pathfinder.gui.adapter;

import java.beans.PropertyChangeListener;

import net.alteiar.CampaignClient;
import net.alteiar.combatTraker.CombatTrackerUnit;
import net.alteiar.dice.DiceBag;
import net.alteiar.dice.DiceSingle;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

import pathfinder.bean.unit.PathfinderCharacter;

public class PathfinderCharacterCombatTraker extends CombatTrackerUnit {
	private static final long serialVersionUID = 1L;

	@Element
	private UniqueID characterId;

	public PathfinderCharacterCombatTraker(PathfinderCharacter character) {
		this(character.getId());
	}

	public PathfinderCharacterCombatTraker(UniqueID characterId) {
		this.characterId = characterId;

	}

	public UniqueID getCharacterId() {
		return characterId;
	}

	public void setCharacterId(UniqueID characterId) {
		this.characterId = characterId;
	}

	private PathfinderCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(characterId);
	}

	@Override
	public String getName(boolean isDM) {
		return getCharacter().getName();
	}

	@Override
	public int getCurrentHp() {
		return getCharacter().getCurrentHp();
	}

	@Override
	public void doDamage(int value) {
		getCharacter().setCurrentHp(getCharacter().getCurrentHp() - value);
	}

	@Override
	public void doHeal(int value) {
		getCharacter().setCurrentHp(getCharacter().getCurrentHp() + value);
	}

	@Override
	public void rollInitiative() {
		DiceSingle dice = new DiceSingle(20);
		DiceBag bag = new DiceBag(dice, getCharacter().getInitMod());
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
