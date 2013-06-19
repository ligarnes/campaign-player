package net.alteiar.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.alteiar.CampaignClient;
import net.alteiar.image.ImageBean;
import net.alteiar.map.MapBean;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.TransfertImage;
import net.alteiar.utils.images.WebImage;

public class MapFactory {

	public static MapBean createMap(String name, File backgroundImage)
			throws IOException {
		return createMap(name, new SerializableImage(backgroundImage));
	}

	public static MapBean createMap(String name, URL backgroundUrl)
			throws IOException {
		return createMap(name, new WebImage(backgroundUrl));
	}

	public static MapBean createMap(String name, TransfertImage background)
			throws IOException {
		return createMap(name, new ImageBean(background));
	}

	public static MapBean createMap(String name, ImageBean background)
			throws IOException {
		MapBean map = new MapBean(name);

		CampaignClient.getInstance().addBean(background);

		BufferedImage backgroundImage = background.getImage().restoreImage();
		map.setWidth(backgroundImage.getWidth());
		map.setHeight(backgroundImage.getHeight());

		MapFilter filter = new MapFilter(map.getWidth(), map.getHeight());
		CampaignClient.getInstance().addBean(filter);

		map.setFilter(filter.getId());
		map.setBackground(background.getId());

		return map;
	}
}
