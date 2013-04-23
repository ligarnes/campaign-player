package plugin.gui.imageIcon;

import javax.swing.ImageIcon;

import net.alteiar.documents.AuthorizationBean;

public class SimpleImageIconFactory<E extends AuthorizationBean> extends
		ImageIconFactory<E> {
	private final Class<E> classes;
	private final ImageIcon image;

	public SimpleImageIconFactory(Class<E> classes, ImageIcon image) {
		this.classes = classes;
		this.image = image;
	}

	@Override
	public Class<E> getDocumentClass() {
		return classes;
	}

	@Override
	public ImageIcon getImage(E bean) {
		return image;
	}

}
