package pathfinder.gui.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.player.Player;
import pathfinder.character.PathfinderCharacter;

public class CharactersDrawable {

	public CharactersDrawable() {
	}

	public void draw(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		List<CharacterBean> characters = CampaignClient.getInstance()
				.getCharacters();

		AffineTransform at = new AffineTransform();
		at.setToTranslation(70, 0);

		AffineTransform orinalTranslate = new AffineTransform();
		orinalTranslate.setToTranslation(10, 10);

		g2.transform(orinalTranslate);
		for (CharacterBean characterBean : characters) {
			drawCharacter(g2, (PathfinderCharacter) characterBean);
			g2.transform(at);
		}
	}

	protected void drawCharacter(Graphics2D g, PathfinderCharacter character) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = null;
		try {
			background = character.getCharacterImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int x = 0;
		int y = 0;
		int width = 50;
		int height = 50;
		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		} else {
			g2.fillRect(0, 0, 50, 50);
		}

		// 5% of the character height
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		int heightLife = 10;
		int yLife = y + height - heightLife;
		int widthLife = width;

		Integer currentHp = character.getCurrentHp();
		Integer totalHp = character.getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(x, yLife, (int) (widthLife * ratio), heightLife);
		}

		g2.setColor(Color.BLACK);
		g2.drawRect(x, yLife, widthLife - 1, heightLife - 1);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));

		Player player = CampaignClient.getInstance().getBean(
				character.getOwner());
		g2.setColor(player.getColor());
		g2.fillOval(40, 30, 10, 10);

		g2.setColor(Color.BLACK);
		g2.drawOval(40, 30, 10, 10);
	}

}
