package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.images.SerializableImage;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.test.BasicTest;

import org.junit.Test;

public class TestMap extends BasicTest {

	public TransfertImage createTransfertImage(String path) {
		TransfertImage battleImages = null;
		try {
			battleImages = new SerializableImage(new File(path));
		} catch (IOException e) {
			fail("cannot read file guerrier.jpg");
		}

		return battleImages;
	}

	public TransfertImage createTransfertImage() {
		return createTransfertImage("./test/ressources/guerrier.jpg");
	}

	public BattleClient createBattle(String battleName,
			TransfertImage battleImage) {

		List<BattleClient> current = CampaignClient.getInstance().getBattles();

		int previousSize = current.size();
		CampaignClient.getInstance().createBattle(battleName, battleImage);

		int currentSize = current.size();
		while (previousSize == currentSize) {
			current = CampaignClient.getInstance().getBattles();
			currentSize = current.size();

			sleep(50);
		}

		return CampaignClient.getInstance().getBattles().get(previousSize);
	}

	@Test(timeout = 10000)
	public void testCreateBattle() {
		String targetName = "test battle";

		TransfertImage battleImages = createTransfertImage();
		BattleClient created = createBattle(targetName, battleImages);
		assertEquals("Battle name have a wrong name", targetName,
				created.getName());

		// Change turn
		int previousTurn = created.getCurrentTurn();
		assertEquals("current turn should be 0", 0, previousTurn);

		created.nextTurn();
		sleep(300);

		int currentTurn = created.getCurrentTurn();
		assertEquals("current turn should be incremented", (previousTurn + 1),
				currentTurn);

		// Map Test Element
		Scale scale = created.getScale();
		created.setScale(new Scale(scale.getPixels() + 5, scale.getMetre()));

		int width = created.getWidth();
		int height = created.getHeight();

		try {
			BufferedImage targetImages = battleImages.restoreImage();
			int expectedWidth = targetImages.getWidth();
			int expectedHeight = targetImages.getHeight();
			assertEquals("width should be same", width, expectedWidth);
			assertEquals("height should be same", height, expectedHeight);

			assertTrue("Images should be same",
					compareImage(created.getBackground(), targetImages));

			assertTrue(
					"Images filter should be same",
					compareImage(created.getFilter(),
							generateShape(width, height, false)));

			created.showRectangle(0, 0, width, height);
			sleep(200);

			assertTrue(
					"Images filter should be same",
					compareImage(created.getFilter(),
							generateShape(width, height, true)));

			created.hideRectangle(0, 0, width, height);
			sleep(200);

			assertTrue(
					"Images filter should be same",
					compareImage(created.getFilter(),
							generateShape(width, height, false)));

		} catch (IOException e) {
			fail("cannot read file guerrier.jpg");
		}

		// Remove the battle
		CampaignClient.getInstance().removeDocument(created);
	}

	private static final Float ALPHA_COMPOSITE_HIDE_PJ = 1.0f;
	private static final Float ALPHA_COMPOSITE_HIDE_MJ = 0.7f;

	private void drawVisible(Graphics2D g2, Shape shape) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 0.0f));

		g2.fill(shape);
	}

	private void drawHide(Graphics2D g2, Shape shape) {
		Float alpha = ALPHA_COMPOSITE_HIDE_PJ;
		if (CampaignClient.getInstance().getCurrentPlayer().isMj()) {
			alpha = ALPHA_COMPOSITE_HIDE_MJ;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

		g2.fill(shape);
	}

	private BufferedImage generateShape(int width, int height, boolean isVisible) {
		// create an image from graphics
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Shape shape = new Rectangle2D.Double(0, 0, width, height);
		if (isVisible) {
			drawVisible(g2, shape);
		} else {
			drawHide(g2, shape);
		}
		g2.dispose();

		return img;
	}
}
