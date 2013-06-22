package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.BeanDocument;

public class SimpleImageIconFactory implements ImageIconFactory {
	private final BufferedImage image;

	public SimpleImageIconFactory(BufferedImage image) {
		this.image = image;
	}

	@Override
	public BufferedImage getImage(BeanDocument bean) {
		return image;
	}

}
