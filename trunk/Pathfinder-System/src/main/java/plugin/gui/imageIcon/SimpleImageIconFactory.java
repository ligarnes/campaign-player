package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.client.bean.BasicBean;

public class SimpleImageIconFactory<E extends BasicBean> extends
		ImageIconFactory<E> {
	private final Class<E> classes;
	private final BufferedImage image;

	public SimpleImageIconFactory(Class<E> classes, BufferedImage image) {
		this.classes = classes;
		this.image = image;
	}

	@Override
	public Class<E> getDocumentClass() {
		return classes;
	}

	@Override
	public BufferedImage getImage(E bean) {
		return image;
	}

}
