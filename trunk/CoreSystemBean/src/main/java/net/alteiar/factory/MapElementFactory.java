package net.alteiar.factory;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.map.elements.MapElement;

public class MapElementFactory {

	/**
	 * 
	 * @param map
	 * @param element
	 */
	public static void buildMapElement(MapElement element, Map map) {
		element.setMapId(map.getId());
		map.addElement(element.getId());
		CampaignClient.getInstance().addNotPermaBean(element);
	}

	/**
	 * 
	 * @param map
	 * @param element
	 */
	public static void removeMapElement(MapElement element, Map map) {
		map.removeElement(element.getId());
		CampaignClient.getInstance().removeBean(element);
	}
}
