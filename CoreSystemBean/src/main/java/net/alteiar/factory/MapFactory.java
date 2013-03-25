package net.alteiar.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.alteiar.image.ImageBean;
import net.alteiar.map.Map;
import net.alteiar.map.MapFilter;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.WebImage;

public class MapFactory {

	public static void createMap(Map map, File backgroundImage)
			throws IOException {
		ImageBean background = new ImageBean(new SerializableImage(
				backgroundImage));
		BeanFactory
				.createBean("map-image_" + map.getName(), background);
		createMap(map, background);
	}

	public static void createMap(Map map, String backgroundUrl)
			throws IOException {
		ImageBean background = new ImageBean(new WebImage(backgroundUrl));
		BeanFactory
				.createBean("map-image_" + map.getName(), background);
		createMap(map, background);
	}

	public static void createMap(Map map, ImageBean background)
			throws IOException {
		BufferedImage backgroundImage = background.getImage().restoreImage();
		map.setWidth(backgroundImage.getWidth());
		map.setHeight(backgroundImage.getHeight());

		MapFilter filter = new MapFilter(map.getWidth(), map.getHeight());
		map.setFilter(filter.getId());
		map.setBackground(background.getId());

		BeanFactory.createBean(filter);
		BeanFactory.createBean(map.getName(), map);
	}
}
