package net.alteiar.combatTraker;

import java.beans.PropertyChangeListener;

import net.alteiar.newversion.shared.bean.BasicBean;

import org.simpleframework.xml.Element;

public abstract class CombatTrackerUnit extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_INITIATIVE_PROPERTY = "initiative";

	@Element
	private Integer initiative;

	public CombatTrackerUnit() {
		initiative = 0;
	}

	public abstract String getName(boolean isDM);

	public abstract int getCurrentHp();

	public abstract void doDamage(int value);

	public abstract void doHeal(int value);

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		int oldValue = this.initiative;

		if (notifyRemote(PROP_INITIATIVE_PROPERTY, oldValue, initiative)) {
			this.initiative = initiative;
			notifyLocal(PROP_INITIATIVE_PROPERTY, oldValue, initiative);
		}
	}

	public abstract void rollInitiative();

	public abstract void addCombatTrackerChangeListener(
			PropertyChangeListener listener);

	public abstract void removeCombatTrackerChangeListener(
			PropertyChangeListener listener);
}