package plugin.gui.imageIcon;

import javax.swing.ImageIcon;

import net.alteiar.documents.AuthorizationBean;

public abstract class ImageIconFactory<E extends AuthorizationBean> {

	public abstract Class<E> getDocumentClass();

	public abstract ImageIcon getImage(E bean);

}
