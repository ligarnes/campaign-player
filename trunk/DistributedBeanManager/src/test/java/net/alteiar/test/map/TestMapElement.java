package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.DocumentMapElementBuilder;
import net.alteiar.server.document.map.element.MapElementClient;
import net.alteiar.server.document.map.element.size.MapElementSize;
import net.alteiar.server.document.map.element.size.MapElementSizePixel;

import org.junit.Test;

import test.pathfinder.mapElement.character.TestPathfinderCharacter;
import test.pathfinder.mapElement.shape.TestCircle;

public class TestMapElement extends TestMap {

	@Test(timeout = 5000)
	public void testCircle() {
		TransfertImage battleImages = createTransfertImage();
		BattleClient battle = createBattle("test battle", battleImages);

		MapElementSize circleRadius = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		DocumentMapElementBuilder documentBuilder = new DocumentMapElementBuilder(
				battle, position, new TestCircle(Color.RED, circleRadius));

		Long id = CampaignClient.getInstance().createDocument(documentBuilder);

		MapElementClient circleClient = (MapElementClient) CampaignClient
				.getInstance().getDocument(id, 1000L);

		assertEquals("The position should be equals", position,
				circleClient.getPosition());
		assertEquals("The color should be equals", Color.RED,
				((TestCircle) circleClient.getElement()).getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				circleClient.getAngle());
		assertEquals("The radius should be equals",
				circleRadius.getPixels(battle.getScale()),
				((TestCircle) circleClient.getElement()).getRadius());

		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		circleClient.draw(g2);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		circleClient.draw(g2);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}

		Double angle = 25.0;
		Boolean isHidden = !circleClient.getIsHidden();
		Point newPosition = new Point(12, 12);
		circleClient.setIsHidden(isHidden);
		circleClient.setPosition(newPosition);
		circleClient.setAngle(angle);

		sleep(200);

		assertEquals("The position should be equals", newPosition,
				circleClient.getPosition());
		assertEquals("The color should be equals", angle,
				circleClient.getAngle());
		assertEquals("The angle should be equals", isHidden,
				circleClient.getIsHidden());

		assertTrue("", !circleClient.contain(new Point(5, 5)));
		assertTrue("", circleClient.contain(new Point(15, 15)));

	}

	protected MapElementClient createMapElement(BattleClient battle,
			DocumentMapElementBuilder mapElement) {

		int previous = battle.getElements().size();
		CampaignClient.getInstance().createMapElement(battle, mapElement);
		int current = battle.getElements().size();
		while (previous == current) {
			sleep(50);
			current = battle.getElements().size();
		}
		return battle.getElements().get(previous);
	}

	@Test(timeout = 5000)
	public void testMapElementCharacter() {
		TransfertImage battleImages = createTransfertImage();
		BattleClient battle = createBattle("test map element character",
				battleImages);

		Point position = new Point(10, 10);
		CharacterClient character = createCharacter();

		DocumentMapElementBuilder mapElementCharacter = new DocumentMapElementBuilder(
				battle, position, new TestPathfinderCharacter(character));

		MapElementClient mapElement = createMapElement(battle,
				mapElementCharacter);

		CampaignClient.getInstance().createMapElement(battle,
				mapElementCharacter);

		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		mapElement.draw(g2, 1.0);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		mapElement.draw(g2, 1.0);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}

		imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgGenerated.getGraphics();
		mapElement.draw(g2, 7.0);
		g2.dispose();

		imgExpected = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		mapElement.draw(g2, 7.0);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}
	}
}
