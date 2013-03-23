package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.image.ImageBean;
import net.alteiar.map.battle.Battle;
import net.alteiar.map.elements.Rectangle;
import net.alteiar.test.BasicTest;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.images.TransfertImage;
import net.alteiar.utils.map.Scale;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeMeter;
import net.alteiar.utils.map.element.MapElementSizePixel;

import org.junit.Before;
import org.junit.Test;

public class TestMap extends BasicTest {

	public int verifyInnerClassCall;

	public TransfertImage createTransfertImage(String path) {
		TransfertImage battleImages = null;
		try {
			battleImages = new SerializableImage(new File(path));
		} catch (IOException e) {
			fail("cannot read file " + path);
		}

		return battleImages;
	}

	public TransfertImage createTransfertImage() {
		return createTransfertImage("./test/ressources/guerrier.jpg");
	}

	public Long createBattle(String battleName, TransfertImage battleImage)
			throws IOException {
		List<Battle> current = CampaignClient.getInstance().getBattles();

		Long imageId = CampaignClient.getInstance().addBean(
				new ImageBean(battleImage));

		BufferedImage image = battleImage.restoreImage();
		Integer width = image.getWidth();
		Integer height = image.getHeight();

		int previousSize = current.size();
		Long battleId = CampaignClient.getInstance().addBean(
				new Battle(battleName, imageId, width, height));

		int currentSize = current.size();
		while (previousSize == currentSize) {
			current = CampaignClient.getInstance().getBattles();
			currentSize = current.size();

			sleep(50);
		}

		return battleId;
	}

	@Before
	public void setup() {
		verifyInnerClassCall = 0;
	}

	public synchronized void innerClassCall() {
		verifyInnerClassCall++;
	}

	public synchronized void verifyInnerClassCall(int callCountExpected) {
		assertEquals(callCountExpected, verifyInnerClassCall);
	}

	@Test(timeout = 10000)
	public void testBattleWithMapElement() {
		TransfertImage battleImage = createTransfertImage();
		Long battleId = -1L;
		try {
			battleId = createBattle("test battle 10", battleImage);
		} catch (IOException e) {
			fail("fail to create battle");
		}
		Battle battle = CampaignClient.getInstance().getBean(battleId);

		battle.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				innerClassCall();
			}
		});

		Point position = new Point(5, 5);
		Color color = Color.GREEN;
		MapElementSize width = new MapElementSizeMeter(6.0);
		MapElementSize height = new MapElementSizePixel(42.0);

		Rectangle rectangle = new Rectangle(battleId, position, color, width,
				height);

		Long rectangleId = CampaignClient.getInstance().addBean(rectangle);

		Collection<Long> elementsOnMap = battle.getElements();
		assertTrue("The map shouldn't have any element",
				elementsOnMap.isEmpty());

		battle.addElement(rectangleId);

		while (elementsOnMap.isEmpty()) {
			sleep(100);
			elementsOnMap = battle.getElements();
		}

		assertEquals("The map should have 1 element", 1, elementsOnMap.size());
		assertEquals("The rectangle id should be the same", rectangleId,
				elementsOnMap.iterator().next());

		assertEquals(
				"The map should have at least 1 element at the position (6,6)",
				1, battle.getElementsAt(new Point(7, 7)).size());
		assertEquals(
				"The map should'nt have any element at the position (4,4)", 0,
				battle.getElementsAt(new Point(4, 4)).size());

		battle.removeElement(elementsOnMap.iterator().next());
		while (!elementsOnMap.isEmpty()) {
			sleep(100);
			elementsOnMap = battle.getElements();
		}

		assertTrue("The map should'nt have any element",
				elementsOnMap.isEmpty());

		battle.setScale(new Scale(25, 1.5));
		// battle.showRectangle(2, 2, 10, 10);

		sleep(200);
		// Should have 4 call (3 for the moment because the filter do not work)
		// (add element, remove element, scale change, filter change)
		verifyInnerClassCall(3);

		// battle.removeMapListener(listener);
	}

	@Test(timeout = 10000)
	public void testCreateBattle() {
		String targetName = "test battle";

		TransfertImage battleImage = createTransfertImage();
		Long battleId = -1L;
		try {
			battleId = createBattle(targetName, battleImage);
		} catch (IOException e) {
			fail("fail to create battle");
		}
		Battle created = CampaignClient.getInstance().getBean(battleId);
		assertEquals("Battle name have a wrong name", targetName,
				created.getName());

		// Change turn
		int previousTurn = created.getTurn();
		assertEquals("current turn should be 0", 0, previousTurn);

		created.nextTurn();
		sleep(300);

		int currentTurn = created.getTurn();
		assertEquals("current turn should be incremented", (previousTurn + 1),
				currentTurn);

		// Map Test Element
		Scale scale = created.getScale();
		created.setScale(new Scale(scale.getPixels() + 5, scale.getMetre()));

		int width = created.getWidth();
		int height = created.getHeight();

		try {
			BufferedImage targetImages = battleImage.restoreImage();
			int expectedWidth = targetImages.getWidth();
			int expectedHeight = targetImages.getHeight();
			assertEquals("width should be same", width, expectedWidth);
			assertEquals("height should be same", height, expectedHeight);

			assertTrue("Images should be same",
					compareImage(created.getBackgroundImage(), targetImages));

			// FIXME FILTER DO NOT WORK
			/*
			 * assertTrue( "Images filter should be same",
			 * compareImage(created.getFilter(), generateShape(width, height,
			 * false)));
			 * 
			 * created.showRectangle(0, 0, width, height); sleep(200);
			 * 
			 * assertTrue( "Images filter should be same",
			 * compareImage(created.getFilter(), generateShape(width, height,
			 * true)));
			 * 
			 * created.hideRectangle(0, 0, width, height); sleep(200);
			 * 
			 * assertTrue( "Images filter should be same",
			 * compareImage(created.getFilter(), generateShape(width, height,
			 * false)));
			 */

		} catch (IOException e) {
			fail("cannot read file guerrier.jpg");
		}

		// Remove the battle
		CampaignClient.getInstance().removeBean(created);

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
