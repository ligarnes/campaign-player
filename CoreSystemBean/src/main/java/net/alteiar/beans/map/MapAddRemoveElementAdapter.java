package net.alteiar.beans.map;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.shared.UniqueID;

public abstract class MapAddRemoveElementAdapter implements
		PropertyChangeListener {

	private final Long timeout;

	public MapAddRemoveElementAdapter() {
		timeout = 300L;
	}

	public MapAddRemoveElementAdapter(Long timeout) {
		this.timeout = timeout;
	}

	@Override
	public final void propertyChange(PropertyChangeEvent evt) {
		if (MapBean.METH_ADD_ELEMENT_METHOD.equals(evt.getPropertyName())) {
			MapElement element = CampaignClient.getInstance().getBean(
					(UniqueID) evt.getNewValue(), timeout);
			mapElementAdded(element);
		} else if (MapBean.METH_REMOVE_ELEMENT_METHOD.equals(evt
				.getPropertyName())) {
			MapElement element = CampaignClient.getInstance().getBean(
					(UniqueID) evt.getNewValue(), timeout);
			mapElementAdded(element);
		}
	}

	protected void mapElementAdded(MapElement element) {

	}

	protected void mapElementRemoved(MapElement element) {

	}

}
