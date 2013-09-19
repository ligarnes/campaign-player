package net.alteiar.campaign.player.gui.sideView.combatTraker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractListModel;

import net.alteiar.beans.combatTraker.CombatTrackerUnit;
import net.alteiar.beans.combatTraker.CombatTraker;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.shared.UniqueID;
import net.alteiar.thread.MyRunnable;

public class CombatTrakerModel extends AbstractListModel<CombatTrackerUnit>
		implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private final CombatTraker traker;

	public CombatTrakerModel(CombatTraker traker) {
		this.traker = traker;

		traker.addPropertyChangeListener(CombatTraker.METH_ADD_UNIT_METHOD,
				new PropertyChangeListener() {
					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						Threads.execute(new MyRunnable() {
							@Override
							public void run() {
								addElement((UniqueID) evt.getNewValue());
							}

							@Override
							public String getTaskName() {
								return "Add unit";
							}
						});
					}
				});
		traker.addPropertyChangeListener(CombatTraker.METH_REMOVE_UNIT_METHOD,
				new PropertyChangeListener() {
					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						Threads.execute(new MyRunnable() {
							@Override
							public void run() {
								removeElement((UniqueID) evt.getNewValue());
							}

							@Override
							public String getTaskName() {
								return "Remove unit";
							}
						});
					}
				});

		traker.addPropertyChangeListener(CombatTraker.METH_SORT_METHOD, this);
		traker.addPropertyChangeListener(
				CombatTraker.PROP_CURRENT_UNIT_PROPERTY, this);

		traker.addPropertyChangeListener(CombatTraker.METH_MOVE_UNIT_TO_METHOD,
				this);
	}

	private void addElement(final UniqueID unitId) {
		// wait 5 second at worse
		CombatTrackerUnit unit = CampaignClient.getInstance().getBean(unitId,
				5000L);

		if (unit != null) {
			unit.addCombatTrackerChangeListener(CombatTrakerModel.this);
			fireIntervalAdded(CombatTrakerModel.this, getSize() - 1, getSize());
		}
	}

	private void removeElement(UniqueID unitId) {
		// wait 1 second at worse because it should be here
		CombatTrackerUnit unit = CampaignClient.getInstance().getBean(unitId,
				1000L);

		if (unit != null) {
			unit.removeCombatTrackerChangeListener(this);
			this.fireIntervalRemoved(this, 0, getSize());
		}
	}

	@Override
	public int getSize() {
		return traker.getUnitsId().size();
	}

	@Override
	public CombatTrackerUnit getElementAt(int index) {
		UniqueID beanId = traker.getUnitsId().get(index);
		return CampaignClient.getInstance().getBean(beanId);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.fireContentsChanged(this, 0, getSize());
	}

}
