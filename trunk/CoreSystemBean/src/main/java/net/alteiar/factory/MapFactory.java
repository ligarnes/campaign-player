package net.alteiar.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.image.ImageBean;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.WebImage;

public class MapFactory {

	public static void createMap(String name, Map map, File backgroundImage)
			throws IOException {
		ImageBean background = new ImageBean(new SerializableImage(
				backgroundImage));
		CampaignClient.getInstance().addNotPermaBean(background);
		createMap(name, map, background);
	}

	public static void createMap(String name, Map map, URL backgroundUrl)
			throws IOException {
		ImageBean background = new ImageBean(new WebImage(backgroundUrl));
		CampaignClient.getInstance().addNotPermaBean(background);
		createMap(name, map, background);
	}

	public static void createMap(String name, Map map, ImageBean background)
			throws IOException {
		BufferedImage backgroundImage = background.getImage().restoreImage();
		map.setWidth(backgroundImage.getWidth());
		map.setHeight(backgroundImage.getHeight());

		MapFilter filter = new MapFilter(map.getWidth(), map.getHeight());
		map.setFilter(filter.getId());
		map.setBackground(background.getId());

		CampaignClient.getInstance().addNotPermaBean(filter);
		CampaignClient.getInstance().addNotPermaBean(name, map);
	}
}
