package plugin.gui.imageIcon;

import java.awt.Graphics2D;
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
		BufferedImage img = ImageUtil.resizeImage(bean.getCharacterImage(), 50,
				50);
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		g2.dispose();
		return img;
	}

}
