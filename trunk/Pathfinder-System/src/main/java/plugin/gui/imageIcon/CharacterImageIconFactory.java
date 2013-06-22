package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.documents.BeanDocument;
import net.alteiar.shared.ImageUtil;
import pathfinder.bean.unit.PathfinderCharacter;

public class CharacterImageIconFactory implements ImageIconFactory {

	public CharacterImageIconFactory() {
	}

	@Override
	public BufferedImage getImage(BeanDocument doc) {
		PathfinderCharacter bean = doc.getBean();
		BufferedImage img = bean.getCharacterImage();
		if (img != null) {
			img = ImageUtil.resizeImage(img, 50, 50);
		}
		return img;
	}
}
