package net.alteiar.factory;

import net.alteiar.CampaignClient;
import net.alteiar.map.Map;
import net.alteiar.map.elements.MapElement;

public class MapElementFactory {

	/**
	 * 
	 * @param map
	 * @param element
	 * @return the element added id
	 */
	public static void buildMapElement(MapElement element, Map map) {
		element.setMapId(map.getId());
		map.addElement(element.getId());
		CampaignClient.getInstance().addBean(element);
	}
}
