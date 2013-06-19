package plugin.gui.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.shared.ImageUtil;
import pathfinder.bean.unit.PathfinderCharacter;

public class CharacterImageIconFactory extends
		ImageIconFactory<PathfinderCharacter> {

	public CharacterImageIconFactory() {
	}

	@Override
	public Class<PathfinderCharacter> getDocumentClass() {
		return PathfinderCharacter.class;
	}

	@Override
	public BufferedImage getImage(PathfinderCharacter bean) {
		BufferedImage img = bean.getCharacterImage();
		if (img != null) {
			img = ImageUtil.resizeImage(img, 50, 50);
		}
		return img;
	}
}
