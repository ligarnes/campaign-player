package net.alteiar.campaign.player.plugin.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.BeanDocument;

public class NullImageIconFactory implements ImageIconFactory {

	@Override
	public BufferedImage getImage(BeanDocument bean) {
		return null;
	}

}
