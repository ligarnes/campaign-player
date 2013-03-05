package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.map.Scale;
import net.alteiar.server.document.map.element.colored.circle.CircleClient;
import net.alteiar.server.document.map.element.colored.circle.DocumentCircleBuilder;
import net.alteiar.server.document.map.element.colored.rectangle.DocumentRectangleBuilder;
import net.alteiar.server.document.map.element.colored.rectangle.RectangleClient;
import net.alteiar.server.document.map.element.size.MapElementSize;
import net.alteiar.server.document.map.element.size.MapElementSizeMeter;
import net.alteiar.server.document.map.element.size.MapElementSizePixel;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

import org.junit.Test;

public class TestMapElement extends BasicTest {

	@Test(timeout = 5000)
	public void testCircle() {
		MapElementSize circleRadius = new MapElementSizePixel(20.0);
		Point position = new Point(5, 5);

		DocumentCircleBuilder circle = new DocumentCircleBuilder(position,
				Color.RED, circleRadius);

		Long id = CampaignClient.getInstance().createDocument(circle);

		CircleClient circleClient = (CircleClient) CampaignClient.getInstance()
				.getDocument(id, 1000L);

		Scale testScale = new Scale(150, 1.5);
		assertEquals("The position should be equals", position,
				circleClient.getPosition());
		assertEquals("The color should be equals", Color.RED,
				circleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				circleClient.getAngle());
		assertEquals("The radius should be equals",
				circleRadius.getPixels(testScale), circleClient.getRadius()
						.getPixels(testScale));

		Scale drawScale = new Scale(1, 1.0);
		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		circleClient.draw(g2, drawScale, 1.0);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		circleClient.draw(g2, drawScale, 1.0);
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

		assertTrue("", !circleClient.contain(new Point(5, 5), testScale, 1.0));
		assertTrue("", circleClient.contain(new Point(22, 22), testScale, 1.0));
	}

	@Test(timeout = 5000)
	public void testRectangle() {
		MapElementSize rectangleWidth = new MapElementSizeMeter(1.5);
		MapElementSize rectangleHeight = new MapElementSizeSquare(3.0);
		Point position = new Point(10, 10);
		Color targetColor = Color.BLUE;

		DocumentRectangleBuilder circle = new DocumentRectangleBuilder(
				position, targetColor, rectangleWidth, rectangleHeight);

		Long id = CampaignClient.getInstance().createDocument(circle);

		RectangleClient rectangleClient = (RectangleClient) CampaignClient
				.getInstance().getDocument(id, 1000L);

		Scale testScale = new Scale(150, 1.5);
		assertEquals("The position should be equals", position,
				rectangleClient.getPosition());
		assertEquals("The color should be equals", targetColor,
				rectangleClient.getColor());
		assertEquals("The angle should be equals", Double.valueOf(0),
				rectangleClient.getAngle());

		assertEquals("The width should be equals",
				rectangleWidth.getPixels(testScale), rectangleClient.getWidth()
						.getPixels(testScale));
		assertEquals("The height should be equals",
				rectangleHeight.getPixels(testScale), rectangleClient
						.getHeight().getPixels(testScale));

		Scale drawScale = new Scale(1, 1.0);
		BufferedImage imgGenerated = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) imgGenerated.getGraphics();
		rectangleClient.draw(g2, drawScale, 1.0);
		g2.dispose();

		BufferedImage imgExpected = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imgExpected.getGraphics();
		rectangleClient.draw(g2, drawScale, 1.0);
		g2.dispose();

		try {
			assertTrue("images should be same",
					compareImage(imgExpected, imgGenerated));
		} catch (IOException e) {
			fail("fail to compare images");
		}

	}
}
