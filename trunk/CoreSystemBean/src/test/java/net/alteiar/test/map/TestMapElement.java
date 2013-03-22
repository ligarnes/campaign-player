package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.map.battle.Battle;
import net.alteiar.map.elements.Circle;
import net.alteiar.utils.images.TransfertImage;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizePixel;

import org.junit.Test;

public class TestMapElement extends TestMap {

	@Test(timeout = 5000)
	public void testCircle() {
		TransfertImage battleImages = createTransfertImage();
		Long battleId = createBattle("test battle", battleImages);
		Battle battle = CampaignClient.getInstance().getBean(battleId);

		MapElementSize circleRadius = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		Long id = CampaignClient.getInstance().addBean(
				new Circle(battleId, position, Color.RED, circleRadius));

		Circle circleClient = CampaignClient.getInstance().getBean(id, 1000L);

		assertEquals("The position should be equals", position,
				circleClient.getPosition());
		assertEquals("The color should be equals", Color.RED,
				circleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				circleClient.getAngle());
		assertEquals("The radius should be equals",
				circleRadius.getPixels(battle.getScale()), circleClient
						.getRadius().getPixels(battle.getScale()));

		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		// circleClient.draw(g2);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		// circleClient.draw(g2);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}

		Double angle = 25.0;
		Boolean isHidden = !circleClient.isHiddenForPlayer();
		Point newPosition = new Point(12, 12);
		circleClient.setHiddenForPlayer(isHidden);
		circleClient.setPosition(newPosition);
		circleClient.setAngle(angle);

		sleep(200);

		assertEquals("The position should be equals", newPosition,
				circleClient.getPosition());
		assertEquals("The color should be equals", angle,
				circleClient.getAngle());
		assertEquals("The angle should be equals", isHidden,
				circleClient.isHiddenForPlayer());

		assertTrue("", !circleClient.contain(new Point(5, 5)));
		assertTrue("", circleClient.contain(new Point(15, 15)));

	}
	/*
	 * @Test(timeout = 5000) public void testMapElementCharacter() {
	 * TransfertImage battleImages = createTransfertImage(); Battle battle =
	 * createBattle("test map element character", battleImages);
	 * 
	 * Point position = new Point(10, 10); CharacterClient character =
	 * createCharacter();
	 * 
	 * DocumentMapElementBuilder mapElementCharacter = new
	 * DocumentMapElementBuilder( battle, position, new
	 * TestPathfinderCharacter(character));
	 * 
	 * MapElementClient mapElement = createMapElement(battle,
	 * mapElementCharacter);
	 * 
	 * CampaignClient.getInstance().createMapElement(battle,
	 * mapElementCharacter);
	 * 
	 * BufferedImage imgGenerated = new BufferedImage(1000, 1000,
	 * BufferedImage.TYPE_INT_ARGB); Graphics2D g2 = (Graphics2D)
	 * imgGenerated.getGraphics(); mapElement.draw(g2, 1.0); g2.dispose();
	 * 
	 * BufferedImage imgExpected = new BufferedImage(1000, 1000,
	 * BufferedImage.TYPE_INT_ARGB); g2 = (Graphics2D)
	 * imgExpected.getGraphics(); mapElement.draw(g2, 1.0); g2.dispose();
	 * 
	 * try { assertTrue("images should be same", compareImage(imgExpected,
	 * imgGenerated)); } catch (IOException e) { fail("fail to compare images");
	 * }
	 * 
	 * imgGenerated = new BufferedImage(1000, 1000,
	 * BufferedImage.TYPE_INT_ARGB); g2 = (Graphics2D)
	 * imgGenerated.getGraphics(); mapElement.draw(g2, 7.0); g2.dispose();
	 * 
	 * imgExpected = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
	 * g2 = (Graphics2D) imgExpected.getGraphics(); mapElement.draw(g2, 7.0);
	 * g2.dispose();
	 * 
	 * try { assertTrue("images should be same", compareImage(imgExpected,
	 * imgGenerated)); } catch (IOException e) { fail("fail to compare images");
	 * } }
	 */
}
