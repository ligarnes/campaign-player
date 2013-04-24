package net.alteiar.test.beans.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.battle.Battle;
import net.alteiar.factory.MapElementFactory;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.shared.UniqueID;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeMeter;
import net.alteiar.utils.map.element.MapElementSizePixel;
import net.alteiar.utils.map.element.MapElementSizeSquare;

import org.junit.Before;
import org.junit.Test;

public class TestMapElement extends NewCampaignTest {

	private UniqueID battleId = null;

	@Before
	public void setup() {
		// create a battle
		if (battleId == null) {
			try {
				battleId = TestMap.createBattle("test battle",
						TestMap.getDefaultImage());
			} catch (IOException e) {
				fail("fail to create battle");
			}
		}
	}

	public Battle getBattle() {
		return CampaignClient.getInstance().getBean(battleId);
	}

	@Test(timeout = 5000)
	public void testMapElement() {
		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		Point position = new Point(0, 0);

		RectangleElement targetRectangle = new RectangleElement(position,
				Color.RED, width, height);

		MapElementFactory.buildMapElement(targetRectangle, getBattle());

		RectangleElement rectangle = CampaignClient.getInstance().getBean(
				targetRectangle.getId());

		assertEquals("center should be at (10, 10)", new Point(10, 10),
				rectangle.getCenterPosition());
		assertEquals("The battle id should be the same as the map id",
				battleId, rectangle.getMapId());

		// Change battle link
		UniqueID newBattleId = null;
		try {
			newBattleId = TestMap.createBattle("new battle",
					TestMap.getDefaultImage());
		} catch (IOException e) {
			fail("fail to create battle");
		}

		rectangle.setMapId(newBattleId);
		sleep(10);

		assertEquals("The battle id should be the same as the map id",
				newBattleId, rectangle.getMapId());

		CampaignClient.getInstance().removeBean(rectangle);
		assertEquals("the bean should have been removed", null, CampaignClient
				.getInstance().getBean(targetRectangle.getId()));
	}

	@Test(timeout = 5000)
	public void testRectangle() {
		Battle battle = getBattle();

		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		RectangleElement targetRectangle = new RectangleElement(position,
				Color.RED, width, height);
		MapElementFactory.buildMapElement(targetRectangle, battle);

		RectangleElement rectangle = CampaignClient.getInstance().getBean(
				targetRectangle.getId(), 300L);

		assertEquals("The position should be equals", position,
				rectangle.getPosition());
		assertEquals("The color should be equals", Color.RED,
				rectangle.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				rectangle.getAngle());
		assertEquals("The width should be equals", width.getPixels(battle
				.getScale()), rectangle.getWidth().getPixels(battle.getScale()));
		assertEquals("The height should be equals", height.getPixels(battle
				.getScale()), rectangle.getHeight()
				.getPixels(battle.getScale()));

		MapElementSize newWidth = new MapElementSizeSquare(2.0);
		MapElementSize newHeight = new MapElementSizeSquare(3.0);
		rectangle.setWidth(newWidth);
		rectangle.setHeight(newHeight);
		sleep(5);
		assertEquals("The width should be equals", newWidth.getPixels(battle
				.getScale()), rectangle.getWidth().getPixels(battle.getScale()));
		assertEquals("The height should be equals", newHeight.getPixels(battle
				.getScale()), rectangle.getHeight()
				.getPixels(battle.getScale()));

		BufferedImage realImg = new BufferedImage(200, 200,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = realImg.createGraphics();
		rectangle.draw(g2, 1.0);
		g2.dispose();

		targetRectangle.setWidth(newWidth);
		targetRectangle.setHeight(newHeight);

		BufferedImage targetImg = new BufferedImage(200, 200,
				BufferedImage.TYPE_INT_ARGB);
		g2 = targetImg.createGraphics();
		rectangle.draw(g2, 1.0);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(targetImg, realImg));
		} catch (IOException e) {
			fail("fail to compare images");
		}
	}

	@Test(timeout = 5000)
	public void testCircle() {
		Battle battle = CampaignClient.getInstance().getBean(battleId);

		MapElementSize circleRadius = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		CircleElement circleClient = new CircleElement(position, Color.RED,
				circleRadius);
		MapElementFactory.buildMapElement(circleClient, battle);

		circleClient = CampaignClient.getInstance().getBean(
				circleClient.getId(), 300L);

		assertEquals("The position should be equals", position,
				circleClient.getPosition());
		assertEquals("The color should be equals", Color.RED,
				circleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				circleClient.getAngle());
		assertEquals("The radius should be equals",
				circleRadius.getPixels(battle.getScale()), circleClient
						.getRadius().getPixels(battle.getScale()));

		assertEquals("The width and the height should be equals",
				circleClient.getWidthPixels(), circleClient.getHeightPixels());

		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		circleClient.draw(g2, 1.0);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		circleClient.draw(g2, 1.0);
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

		sleep(10);

		assertEquals("The position should be equals", newPosition,
				circleClient.getPosition());
		assertEquals("The color should be equals", angle,
				circleClient.getAngle());
		assertEquals("The angle should be equals", isHidden,
				circleClient.isHiddenForPlayer());

		assertTrue("the circle not contain point (5,5)",
				!circleClient.contain(new Point(5, 5)));
		assertTrue("the circle contain point (17,17)",
				circleClient.contain(new Point(17, 17)));

		MapElementSize newCircleRadius = new MapElementSizeMeter(1.5);
		circleClient.setRadius(newCircleRadius);
		sleep(10);
		assertEquals("radius should be equals",
				newCircleRadius.getPixels(battle.getScale()), circleClient
						.getRadius().getPixels(battle.getScale()));

		Color color = Color.BLUE;
		circleClient.setColor(color);
		sleep(10);
		assertEquals("radius should be equals", color, circleClient.getColor());
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
