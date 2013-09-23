package plugin.imageIcon;

import java.awt.image.BufferedImage;

import net.alteiar.campaign.player.plugin.imageIcon.ImageIconFactory;
import net.alteiar.documents.BeanDocument;
import net.alteiar.shared.ImageUtil;
import shadowrun.bean.unit.ShadowrunCharacter;

public class CharacterImageIconFactory implements ImageIconFactory {

	public CharacterImageIconFactory() {
	}

	@Override
	public BufferedImage getImage(BeanDocument doc) {
		ShadowrunCharacter bean = doc.getBean();
		BufferedImage img = bean.getCharacterImage();
		if (img != null) {
			img = ImageUtil.resizeImage(img, 50, 50);
		}
		return img;
	}
}
