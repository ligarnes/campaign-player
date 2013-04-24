package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.AuthorizationBean;

public class SimpleImageIconFactory<E extends AuthorizationBean> extends
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
