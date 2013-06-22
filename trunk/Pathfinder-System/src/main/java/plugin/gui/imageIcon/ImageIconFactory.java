package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.BeanDocument;

public interface ImageIconFactory {
	BufferedImage getImage(BeanDocument bean);
}
