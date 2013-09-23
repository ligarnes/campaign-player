package shadowrun.gui.drawable;

import generic.DocumentTypeConstant;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.player.Player;
import shadowrun.bean.unit.ShadowrunCharacter;
import shadowrun.gui.mapElement.BarElement;

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
					DocumentTypeConstant.CHARACTER)
					&& characterBean.getPublic()) {
				drawCharacter(g2, characterBean);
				g2.transform(at);
			}
		}
	}

	public static int WIDTH = 50;
	public static int HEIGHT = 50;
	public static int HEIGHT_HEALTH_BAR = 7;

	protected void drawCharacter(Graphics2D g, BeanDocument doc) {
		Graphics2D g2 = (Graphics2D) g.create();

		ShadowrunCharacter character = CampaignClient.getInstance().getBean(
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

	protected void drawHealthBar(Graphics2D g, ShadowrunCharacter character) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		int heightLife = 10;
		int yLife = HEIGHT - HEIGHT_HEALTH_BAR;
		int widthLife = WIDTH;

		BarElement physicalBar = new BarElement(Color.RED, Color.GREEN);
		BarElement stunBar = new BarElement(Color.RED, Color.BLUE);

		float physical = character.getPhysicalPoint().floatValue();
		float physicalDamage = character.getPhysicalDamage().floatValue();
		float probPhysical = 1 - (physicalDamage / physical);

		float stun = character.getStunPoint().floatValue();
		float stunDamage = character.getStunDamage().floatValue();
		float probStun = 1 - (stunDamage / stun);

		physicalBar.drawBar(g2, 1.0, 0, yLife, heightLife, widthLife,
				probPhysical);

		stunBar.drawBar(g2, 1.0, 0, yLife - heightLife, heightLife, widthLife,
				probStun);
	}

	protected void drawPlayerColor(Graphics2D g2, BeanDocument character) {
		Player player = CampaignClient.getInstance().getBean(
				character.getOwner());
		g2.setColor(player.getRealColor());
		g2.fillOval(0, 0, 10, 10);

		g2.setColor(Color.BLACK);
		g2.drawOval(0, 0, 10, 10);
	}

}
