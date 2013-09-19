package net.alteiar.factory;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.campaign.CampaignClient;

public class MapElementFactory {

	/**
	 * 
	 * @param map
	 * @param element
	 */
	public static void buildMapElement(MapElement element, MapBean map) {
		element.setMapId(map.getId());
		map.addElement(element.getId());
		CampaignClient.getInstance().addBean(element);
	}

	/**
	 * 
	 * @param map
	 * @param element
	 */
	public static void removeMapElement(MapElement element, MapBean map) {
		map.removeElement(element.getId());
		CampaignClient.getInstance().removeBean(element);
	}
}
