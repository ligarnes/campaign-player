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
import net.alteiar.server.document.images.TransfertImage;
import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.colored.circle.CircleClient;
import net.alteiar.server.document.map.element.colored.circle.DocumentCircleBuilder;
import net.alteiar.server.document.map.element.colored.rectangle.DocumentRectangleBuilder;
import net.alteiar.server.document.map.element.colored.rectangle.RectangleClient;
import net.alteiar.server.document.map.element.size.MapElementSize;
import net.alteiar.server.document.map.element.size.MapElementSizeMeter;
import net.alteiar.server.document.map.element.size.MapElementSizePixel;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

import org.junit.Test;

public class TestMapElement extends TestMap {

	@Test(timeout = 5000)
	public void testCircle() {
		TransfertImage battleImages = createTransfertImage();
		BattleClient battle = createBattle("test battle", battleImages);

		MapElementSize circleRadius = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		DocumentCircleBuilder circle = new DocumentCircleBuilder(battle,
				position, Color.RED, circleRadius);

		Long id = CampaignClient.getInstance().createDocument(circle);

		CircleClient circleClient = (CircleClient) CampaignClient.getInstance()
				.getDocument(id, 1000L);

		assertEquals("The position should be equals", position,
				circleClient.getPosition());
		assertEquals("The color should be equals", Color.RED,
				circleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				circleClient.getAngle());
		assertEquals("The radius should be equals",
				circleRadius.getPixels(battle.getScale()),
				circleClient.getRadius());

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

	@Test(timeout = 5000)
	public void testRectangle() {
		TransfertImage battleImages = createTransfertImage();
		BattleClient battle = createBattle("test battle", battleImages);

		MapElementSize rectangleWidth = new MapElementSizeMeter(1.5);
		MapElementSize rectangleHeight = new MapElementSizeSquare(3.0);
		Point position = new Point(10, 10);
		Color targetColor = Color.BLUE;

		DocumentRectangleBuilder circle = new DocumentRectangleBuilder(
				battle.getId(), position, targetColor, rectangleWidth,
				rectangleHeight);

		Long id = CampaignClient.getInstance().createDocument(circle);

		RectangleClient rectangleClient = (RectangleClient) CampaignClient
				.getInstance().getDocument(id, 1000L);

		assertEquals("The position should be equals", position,
				rectangleClient.getPosition());
		assertEquals("The color should be equals", targetColor,
				rectangleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				rectangleClient.getAngle());

		assertEquals("The width should be equals",
				rectangleWidth.getPixels(battle.getScale()),
				rectangleClient.getWidth());
		assertEquals("The height should be equals",
				rectangleHeight.getPixels(battle.getScale()),
				rectangleClient.getHeight());

		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		rectangleClient.draw(g2, 1.0);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		rectangleClient.draw(g2, 1.0);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}
	}
}
