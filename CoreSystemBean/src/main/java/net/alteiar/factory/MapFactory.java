package net.alteiar.factory;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.alteiar.beans.map.MapBean;
import net.alteiar.beans.map.filter.CharacterMapFilter;
import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.utils.file.images.SerializableImage;
import net.alteiar.utils.file.images.TransfertImage;
import net.alteiar.utils.file.images.WebImage;

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

		Image backgroundImage = background.getImage().restoreImage();
		map.setWidth(backgroundImage.getWidth(null));
		map.setHeight(backgroundImage.getHeight(null));

		// ManualMapFilter filter = new ManualMapFilter(map.getId());
		CharacterMapFilter filter = new CharacterMapFilter(map);
		CampaignClient.getInstance().addBean(filter);

		map.setFilter(filter.getId());
		map.setBackground(background.getId());

		return map;
	}
}
