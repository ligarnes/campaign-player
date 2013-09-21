package net.alteiar.trigger;

import java.beans.VetoableChangeListener;
import java.util.HashSet;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.MapBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;

public abstract class MyAreaBean extends MapElementDecorator implements
		VetoableChangeListener {
	private static final long serialVersionUID = 1L;

	public MyAreaBean(MapElement mapElement) {
		super(mapElement);

	}

	@Override
	public void setMapId(UniqueID mapId) {
		super.setMapId(mapId);

		MapBean map = getMap();
		HashSet<UniqueID> elements = map.getElements();
		for (UniqueID elementId : elements) {
			MapElement element = CampaignClient.getInstance()
					.getBean(elementId);

			// element.contain(p)
		}
	}

}
