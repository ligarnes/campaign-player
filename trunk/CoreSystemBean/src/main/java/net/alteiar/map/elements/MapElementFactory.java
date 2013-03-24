package net.alteiar.map.elements;

import net.alteiar.CampaignClient;
import net.alteiar.map.Map;

public class MapElementFactory {

	/**
	 * 
	 * @param map
	 * @param element
	 * @return the element added id
	 */
	public static Long buildMapElement(MapElement element, Map map) {
		element.setMapId(map.getId());
		map.addElement(element.getId());
		CampaignClient.getInstance().addBean(element);
		return element.getId();
	}
}
