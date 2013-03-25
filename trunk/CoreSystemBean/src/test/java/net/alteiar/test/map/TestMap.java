package net.alteiar.test.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.image.ImageBean;
import net.alteiar.map.Map;
import net.alteiar.map.MapFilter;
import net.alteiar.map.battle.Battle;
import net.alteiar.map.elements.MapElementFactory;
import net.alteiar.map.elements.RectangleElement;
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

	public static TransfertImage createTransfertImage(String path) {
		TransfertImage battleImages = null;
		try {
			battleImages = new SerializableImage(new File(path));
		} catch (IOException e) {
			fail("cannot read file " + path);
		}

		return battleImages;
	}

	public static TransfertImage createTransfertImage() {
		return createTransfertImage("./test/ressources/guerrier.jpg");
	}

	public static ImageBean createBeanImage() {
		return new ImageBean(
				createTransfertImage("./test/ressources/guerrier.jpg"));
	}

	public static Long createBattle(String battleName,
			TransfertImage battleImage) throws IOException {
		List<Battle> current = CampaignClient.getInstance().getBattles();

		ImageBean imageBean = new ImageBean(battleImage);
		CampaignClient.getInstance().addBean(imageBean);
		Long imageId = imageBean.getId();

		BufferedImage image = battleImage.restoreImage();
		Integer width = image.getWidth();
		Integer height = image.getHeight();

		MapFilter filter = new MapFilter(width, height);
		CampaignClient.getInstance().addBean(filter);
		Long filterId = filter.getId();

		int previousSize = current.size();
		Battle battleBean = new Battle(battleName, filterId, imageId, width,
				height);
		CampaignClient.getInstance().addBean(battleBean);
		Long battleId = battleBean.getId();

		int currentSize = current.size();
		while (previousSize == currentSize) {
			current = CampaignClient.getInstance().getBattles();
			currentSize = current.size();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				fail("not able to sleep");
			}
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
		Battle emptyBattle = new Battle();
		assertEquals("verify emptyBattle", emptyBattle, emptyBattle);

		TransfertImage battleImage = createTransfertImage();
		Long battleId = -1L;
		try {
			battleId = createBattle("test battle 10", battleImage);
		} catch (IOException e) {
			fail("fail to create battle");
		}
		Battle battle = CampaignClient.getInstance().getBean(battleId);

		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				innerClassCall();
			}
		};
		battle.addPropertyChangeListener(listener);

		Point position = new Point(5, 5);
		Color color = Color.GREEN;
		MapElementSize width = new MapElementSizeMeter(6.0);
		MapElementSize height = new MapElementSizePixel(42.0);

		RectangleElement rectangle = new RectangleElement(position, color,
				width, height);

		Collection<Long> elementsOnMap = battle.getElements();
		assertTrue("The map shouldn't have any element",
				elementsOnMap.isEmpty());

		Long rectangleId = MapElementFactory.buildMapElement(rectangle, battle);

		while (elementsOnMap.isEmpty()) {
			sleep(10);
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
			sleep(10);
			elementsOnMap = battle.getElements();
		}

		assertTrue("The map should'nt have any element",
				elementsOnMap.isEmpty());

		battle.setScale(new Scale(25, 1.5));
		battle.setFilter(0L);

		sleep(5);
		// Should have 4 call
		// (add element, remove element, scale change, filter change)
		verifyInnerClassCall(4);

		battle.removePropertyChangeListener(listener);
	}

	@Test
	public void testScale() {
		Scale scale = new Scale();

		assertEquals("default scale should be 0", Double.valueOf(0.0),
				scale.getMeter());
		assertEquals("default scale should be 0", Integer.valueOf(0),
				scale.getPixels());

		Double meters = 1.5;
		Integer pixels = 150;

		scale.setMeter(meters);
		scale.setPixels(150);

		assertEquals("meters should have changed", meters, scale.getMeter());
		assertEquals("pixels should have changed", pixels, scale.getPixels());

		Double meters2 = 5.0;
		Integer pixels2 = 300;
		Double meters3 = null;
		Integer pixels3 = null;
		Scale scale1 = new Scale(pixels, meters);
		Scale scale2 = new Scale(pixels2, meters);
		Scale scale3 = new Scale(pixels, meters2);
		Scale scale4 = new Scale(pixels3, meters);
		Scale scale5 = new Scale(pixels, meters3);

		assertTrue("Scale should be equals to itself", scale.equals(scale));
		assertTrue("Scale should'nt be equals to null", !scale.equals(null));
		assertTrue("Scale should'nt be equals to other object",
				!scale.equals(""));

		assertTrue("Scale should be equals to a same scale",
				scale.equals(scale1));
		assertTrue("Scale should be equals to a same scale",
				!scale.equals(scale2));
		assertTrue("Scale should be equals to a same scale",
				!scale.equals(scale3));
		assertTrue("Scale should be equals to a same scale",
				!scale4.equals(scale));
		assertTrue("Scale should be equals to a same scale",
				!scale5.equals(scale));

		assertTrue("Scale hashcode should be equals to a same scale hashCode",
				scale.hashCode() == scale1.hashCode());

		assertTrue(
				"Scale hashcode should be different from other scale hashCode",
				scale.hashCode() != scale3.hashCode());
	}

	@Test(timeout = 10000)
	public void testMap() {
		Map emptyMap = new Map();
		assertEquals("verify emptyMap", emptyMap, emptyMap);

		String targetName = "test battle";

		TransfertImage battleImage = createTransfertImage();
		Long battleId = -1L;
		try {
			battleId = createBattle(targetName, battleImage);
		} catch (IOException e) {
			fail("fail to create battle");
		}
		Map map = CampaignClient.getInstance().getBean(battleId);
		assertEquals("Map name have a wrong name", targetName, map.getName());

		String newName = "new map name";
		map.setName(newName);

		Scale newScale = new Scale(map.getScale().getPixels() + 5, map
				.getScale().getMeter());
		map.setScale(newScale);

		Integer newWidth = map.getWidth() + 5;
		map.setWidth(newWidth);

		Integer newHeight = map.getHeight() + 5;
		map.setHeight(newHeight);

		ImageBean imageBean = createBeanImage();
		CampaignClient.getInstance().addBean(imageBean);
		Long newImage = imageBean.getId();

		map.setBackground(newImage);

		Long newFilter = -1L;
		map.setFilter(newFilter);

		HashSet<Long> set = new HashSet<Long>();
		set.add(12L);
		set.add(13L);
		map.setElements(set);

		sleep(10);
		assertEquals("Map name have a wrong name", newName, map.getName());
		assertEquals("Map scale should be changed", newScale, map.getScale());
		assertEquals("Map width should be changed", newWidth, map.getWidth());
		assertEquals("Map height should be changed", newHeight, map.getHeight());
		assertEquals("Map image should be changed", newImage,
				map.getBackground());
		assertEquals("Map image should be changed", newFilter, map.getFilter());
		assertEquals("Map elements should be changed", set, map.getElements());
	}

	@Test(timeout = 10000)
	public void testBattle() {
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
		sleep(10);

		int currentTurn = created.getTurn();
		assertEquals("current turn should be incremented", (previousTurn + 1),
				currentTurn);

		// Remove the battle
		CampaignClient.getInstance().removeBean(created);
	}

	@Test
	public void testMapFilter() {
		TransfertImage battleImage = createTransfertImage();
		Long battleId = -1L;
		try {
			battleId = createBattle("new battle", battleImage);
		} catch (IOException e) {
			fail("fail to create battle");
		}
		Battle mapFiltered = CampaignClient.getInstance().getBean(battleId);

		int width = mapFiltered.getWidth();
		int height = mapFiltered.getHeight();

		try {
			BufferedImage targetImages = battleImage.restoreImage();
			int expectedWidth = targetImages.getWidth();
			int expectedHeight = targetImages.getHeight();
			assertEquals("width should be same", width, expectedWidth);
			assertEquals("height should be same", height, expectedHeight);

			/*
			 * BufferedImage image = new BufferedImage(mapFiltered.getWidth(),
			 * mapFiltered.getHeight(), targetImages.getType());
			 * 
			 * Graphics2D g = (Graphics2D) image.getGraphics();
			 * mapFiltered.draw(g, 1.0); g.dispose();
			 * 
			 * assertTrue("Images should be same", compareImage(image,
			 * targetImages));
			 */

			// Test filter
			double compareZoomFactor = 2.75;

			MapFilter filter = new MapFilter(width, height);
			filter.showPolygon(new Polygon(new int[] { 5, 25, 25, 5 },
					new int[] { 5, 5, 25, 25 }, 4));
			filter.hidePolygon(new Polygon(new int[] { 5, 25, 25, 5 },
					new int[] { 5, 5, 25, 25 }, 4));

			CampaignClient.getInstance().addBean(filter);
			Long filterId = filter.getId();

			mapFiltered.setFilter(filterId);
			filter = CampaignClient.getInstance().getBean(filterId);

			BufferedImage filteredImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * compareZoomFactor),
					(int) (mapFiltered.getHeight() * compareZoomFactor),
					BufferedImage.TYPE_INT_ARGB);

			BufferedImage targetFilteredImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * 2.75),
					(int) (mapFiltered.getHeight() * 2.75),
					BufferedImage.TYPE_INT_ARGB);

			MapFilter targetFilter = new MapFilter(filteredImage.getWidth(),
					filteredImage.getHeight());

			Graphics2D g = (Graphics2D) filteredImage.getGraphics();
			mapFiltered.drawBackground(g, 2.75);
			mapFiltered.drawFilter(g, 2.75);
			g.dispose();

			g = (Graphics2D) targetFilteredImage.getGraphics();
			AffineTransform transform = new AffineTransform();
			transform.scale(2.75, 2.75);
			g.drawImage(targetImages, transform, null);
			targetFilter.draw(g, 2.75);
			g.dispose();

			assertTrue("Images filter should be same",
					compareImage(filteredImage, targetFilteredImage));

			// Change filter
			Polygon showPolygon = new Polygon(new int[] { 15, 50, 50, 15 },
					new int[] { 15, 15, 50, 50 }, 4);
			filter.showPolygon(showPolygon);
			targetFilter.showPolygon(showPolygon);
			sleep(10);

			BufferedImage filteredShowImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * 0.75),
					(int) (mapFiltered.getHeight() * 0.75),
					BufferedImage.TYPE_INT_ARGB);

			BufferedImage targetFilteredShowImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * 0.75),
					(int) (mapFiltered.getHeight() * 0.75),
					BufferedImage.TYPE_INT_ARGB);

			g = (Graphics2D) filteredShowImage.getGraphics();
			mapFiltered.drawBackground(g, 0.75);
			mapFiltered.drawFilter(g, 0.75);
			g.dispose();

			g = (Graphics2D) targetFilteredShowImage.getGraphics();
			transform = new AffineTransform();
			transform.scale(0.75, 0.75);
			g.drawImage(targetImages, transform, null);
			targetFilter.draw(g, 0.75);
			g.dispose();

			assertTrue("Images filter should be same",
					compareImage(filteredShowImage, targetFilteredShowImage));

			// Compare with previous filter, should have changed
			filteredShowImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * compareZoomFactor),
					(int) (mapFiltered.getHeight() * compareZoomFactor),
					BufferedImage.TYPE_INT_ARGB);

			g = (Graphics2D) filteredShowImage.getGraphics();
			mapFiltered.drawBackground(g, compareZoomFactor);
			mapFiltered.drawFilter(g, compareZoomFactor);
			g.dispose();

			assertTrue("Images filter should not be same",
					!compareImage(filteredShowImage, filteredImage));

			// Change filter
			Polygon hidePolygon = new Polygon(new int[] { 15, 20, 20, 15 },
					new int[] { 15, 15, 20, 20 }, 4);
			filter.hidePolygon(hidePolygon);
			targetFilter.hidePolygon(hidePolygon);
			sleep(10);

			BufferedImage filteredShowHideImage = new BufferedImage(
					mapFiltered.getWidth() * 1, mapFiltered.getHeight() * 1,
					BufferedImage.TYPE_INT_ARGB);

			BufferedImage targetFilteredShowHideImage = new BufferedImage(
					mapFiltered.getWidth() * 1, mapFiltered.getHeight() * 1,
					BufferedImage.TYPE_INT_ARGB);

			g = (Graphics2D) filteredShowHideImage.getGraphics();
			mapFiltered.drawBackground(g, 1);
			mapFiltered.drawFilter(g, 1);
			g.dispose();

			g = (Graphics2D) targetFilteredShowHideImage.getGraphics();
			transform = new AffineTransform();
			transform.scale(1, 1);
			g.drawImage(targetImages, transform, null);
			targetFilter.draw(g, 1);
			g.dispose();

			assertTrue(
					"Images filter should be same",
					compareImage(filteredShowHideImage,
							targetFilteredShowHideImage));

			// Compare with previous filter, should have changed
			filteredShowImage = new BufferedImage(
					(int) (mapFiltered.getWidth() * compareZoomFactor),
					(int) (mapFiltered.getHeight() * compareZoomFactor),
					BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) filteredShowImage.getGraphics();
			mapFiltered.drawBackground(g, compareZoomFactor);
			mapFiltered.drawFilter(g, compareZoomFactor);
			filter.draw(g, compareZoomFactor);
			g.dispose();
			assertTrue("Images filter should not be same",
					!compareImage(filteredShowHideImage, filteredShowImage));
		} catch (IOException e) {
			fail("cannot read file guerrier.jpg");
		}

		// Remove the battle
		CampaignClient.getInstance().removeBean(mapFiltered);
	}
}
