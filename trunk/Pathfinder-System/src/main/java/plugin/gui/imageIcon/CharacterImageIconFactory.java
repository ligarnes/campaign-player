package plugin.gui.imageIcon;

import java.io.IOException;

import javax.swing.ImageIcon;

import pathfinder.character.PathfinderCharacter;

public class CharacterImageIconFactory extends
		ImageIconFactory<PathfinderCharacter> {

	public CharacterImageIconFactory() {
	}

	@Override
	public Class<PathfinderCharacter> getDocumentClass() {
		return PathfinderCharacter.class;
	}

	@Override
	public ImageIcon getImage(PathfinderCharacter bean) {
		try {
			return new ImageIcon(bean.getCharacterImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}
}
