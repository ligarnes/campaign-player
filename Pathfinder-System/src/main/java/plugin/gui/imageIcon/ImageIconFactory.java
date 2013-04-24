package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.AuthorizationBean;

public abstract class ImageIconFactory<E extends AuthorizationBean> {

	public abstract Class<E> getDocumentClass();

	public abstract BufferedImage getImage(E bean);

}
