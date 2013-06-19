package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.client.bean.BasicBean;

public abstract class ImageIconFactory<E extends BasicBean> {

	public abstract Class<E> getDocumentClass();

	public abstract BufferedImage getImage(E bean);

}
