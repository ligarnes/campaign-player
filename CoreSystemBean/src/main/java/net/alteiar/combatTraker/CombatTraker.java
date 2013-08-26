package net.alteiar.combatTraker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class CombatTraker extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String METH_MOVE_UNIT_TO_METHOD = "moveUnitTo";
	public static final String METH_REMOVE_UNIT_METHOD = "removeUnit";
	public static final String METH_ADD_UNIT_METHOD = "addUnit";
	public static final String METH_SORT_METHOD = "sort";

	public static final String PROP_UNITS_PROPERTY = "units";
	public static final String PROP_CURRENT_TURN_PROPERTY = "currentTurn";
	public static final String PROP_CURRENT_UNIT_PROPERTY = "currentUnit";

	@ElementList
	private ArrayList<UniqueID> unitsId;

	@Element
	private Integer currentTurn;

	@Element
	private Integer currentUnit;

	public CombatTraker() {
		unitsId = new ArrayList<UniqueID>();
		currentTurn = 0;
		currentUnit = 0;
	}

	public ArrayList<CombatTrackerUnit> getUnits() {
		return CampaignClient.getInstance().getBeans(unitsId);
	}

	public ArrayList<UniqueID> getUnitsId() {
		return unitsId;
	}

	protected void setUnits(ArrayList<UniqueID> units) {
		ArrayList<UniqueID> oldValue = this.unitsId;

		if (notifyRemote(PROP_UNITS_PROPERTY, oldValue, units)) {
			this.unitsId = units;
			notifyLocal(PROP_UNITS_PROPERTY, oldValue, units);
		}
	}

	public Integer getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Integer currentTurn) {
		Integer oldValue = this.currentTurn;

		if (notifyRemote(PROP_CURRENT_TURN_PROPERTY, oldValue, currentTurn)) {
			this.currentTurn = currentTurn;
			notifyLocal(PROP_CURRENT_TURN_PROPERTY, oldValue, currentTurn);
		}
	}

	public Integer getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(Integer currentUnit) {
		Integer oldValue = this.currentUnit;
		if (notifyRemote(PROP_CURRENT_UNIT_PROPERTY, oldValue, currentUnit)) {
			this.currentUnit = currentUnit;
			notifyLocal(PROP_CURRENT_UNIT_PROPERTY, oldValue, currentUnit);
		}
	}

	/**
	 * change the current unit to the next, if their are no more unit the we go
	 * back to the first one and increment the turn number
	 */
	public void nextUnit() {
		int next = this.currentUnit + 1;

		if (next >= unitsId.size()) {
			next = 0;
			setCurrentTurn(currentTurn + 1);
		}

		setCurrentUnit(next);
	}

	public void reset() {
		setCurrentTurn(0);
		setCurrentUnit(0);
	}

	public void addUnit(UniqueID unit) {
		if (notifyRemote(METH_ADD_UNIT_METHOD, null, unit)) {
			unitsId.add(unit);
			notifyLocal(METH_ADD_UNIT_METHOD, null, unit);
		}
	}

	public void removeUnit(UniqueID unit) {
		if (notifyRemote(METH_REMOVE_UNIT_METHOD, null, unit)) {
			unitsId.remove(unit);
			notifyLocal(METH_REMOVE_UNIT_METHOD, null, unit);
		}
	}

	public void moveUnit(int currentIdx, int newIdx) {
		moveUnitTo(new MoveAction(currentIdx, newIdx));
	}

	private void moveUnitTo(MoveAction move) {
		if (notifyRemote(METH_MOVE_UNIT_TO_METHOD, null, move)) {
			UniqueID unit = unitsId.get(move.getOldIdx());

			if (move.getNewIdx() >= unitsId.size()) {
				unitsId.add(unit);
			} else {
				unitsId.add(move.getNewIdx(), unit);
			}

			if (move.moveAfter()) {
				unitsId.remove(unit);
			} else {
				unitsId.remove(move.getOldIdx() + 1);
			}

			notifyLocal(METH_MOVE_UNIT_TO_METHOD, null, move);
		}
	}

	public void sort(Comparator<CombatTrackerUnit> comparator) {
		if (notifyRemote(METH_SORT_METHOD, null, comparator)) {
			// First we sort the units
			List<CombatTrackerUnit> units = getUnits();
			Collections.sort(units, comparator);

			// Then we recopy the ids in the right order
			// I think it is a better idea than doing a comparator of UniqueId
			this.unitsId.clear();
			for (CombatTrackerUnit combatTrackerUnit : units) {
				this.unitsId.add(combatTrackerUnit.getId());
			}

			notifyLocal(METH_SORT_METHOD, null, comparator);
		}
	}

	/**
	 * 
	 * @author ligarnes
	 * 
	 *         This class allow to move item within the list without changing
	 *         the list. This is good only because we have to distribute less
	 *         data
	 */
	public class MoveAction implements Serializable {
		private static final long serialVersionUID = 1L;

		private int oldIdx;
		private int newIdx;

		public MoveAction(int oldIdx, int newIdx) {
			super();
			this.oldIdx = oldIdx;
			this.newIdx = newIdx;
		}

		public int getOldIdx() {
			return oldIdx;
		}

		public void setOldIdx(int oldIdx) {
			this.oldIdx = oldIdx;
		}

		public int getNewIdx() {
			return newIdx;
		}

		public void setNewIdx(int newIdx) {
			this.newIdx = newIdx;
		}

		public boolean moveAfter() {
			return newIdx > oldIdx;
		}
	}
}
