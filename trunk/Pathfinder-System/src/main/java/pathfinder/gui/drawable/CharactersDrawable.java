package pathfinder.gui.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.player.Player;
import pathfinder.DocumentTypeConstant;
import pathfinder.bean.unit.PathfinderCharacter;

public class CharactersDrawable {

	public CharactersDrawable() {
	}

	public void draw(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		HashSet<BeanDocument> characters = CampaignClient.getInstance()
				.getDocuments();

		AffineTransform at = new AffineTransform();
		at.setToTranslation(70, 0);

		AffineTransform orinalTranslate = new AffineTransform();
		orinalTranslate.setToTranslation(10, 10);

		g2.transform(orinalTranslate);
		for (BeanDocument characterBean : characters) {
			if (characterBean.getDocumentType().equals(
					DocumentTypeConstant.CHARACTER)) {
				drawCharacter(g2, characterBean);
				g2.transform(at);
			}
		}
	}

	public static int WIDTH = 50;
	public static int HEIGHT = 50;
	public static int HEIGHT_HEALTH_BAR = 10;

	protected void drawCharacter(Graphics2D g, BeanDocument doc) {
		Graphics2D g2 = (Graphics2D) g.create();

		PathfinderCharacter character = CampaignClient.getInstance().getBean(
				doc.getBeanId());

		BufferedImage background = character.getCharacterImage();

		if (background != null) {
			g2.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		} else {
			g2.fillRect(0, 0, 50, 50);
		}

		drawHealthBar(g2, character);
		drawPlayerColor(g2, doc);
	}

	protected void drawHealthBar(Graphics2D g, PathfinderCharacter character) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		int heightLife = 10;
		int yLife = HEIGHT - HEIGHT_HEALTH_BAR;
		int widthLife = WIDTH;

		Integer currentHp = character.getCurrentHp();
		Integer totalHp = character.getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(0, yLife, (int) (widthLife * ratio), heightLife);
		}

		g2.setColor(Color.BLACK);
		g2.drawRect(0, yLife, widthLife - 1, heightLife - 1);
		g2.dispose();
	}

	protected void drawPlayerColor(Graphics2D g2, BeanDocument character) {
		Player player = CampaignClient.getInstance().getBean(
				character.getOwner());
		g2.setColor(player.getColor());
		g2.fillOval(40, 30, 10, 10);

		g2.setColor(Color.BLACK);
		g2.drawOval(40, 30, 10, 10);
	}

}
