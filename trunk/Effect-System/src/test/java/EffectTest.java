import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.DelayedEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.factory.MapElementFactory;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.RectangleElement;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.trigger.PositionTrigger;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizePixel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EffectTest extends NewCampaignTest {

	@Override
	@Before
	public void beforeTest() {
		super.beforeTest();
	}

	@Override
	@After
	public void afterTest() {
		super.afterTest();
	}

	@Test
	public void testActivate() {
		Map map = new Map("map");
		CampaignClient.getInstance().addBean(map);
		map = CampaignClient.getInstance().getBean(map.getId(), 2000);

		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		Effect effect = new BasicEffect(new CircleElement(new Point(25, 25),
				Color.red, width), false, RectangleElement.class, map.getId());

		RectangleElement rectangleElement = new RectangleElement(
				new Point(4, 4), Color.BLACK, width, height);

		PositionTrigger positionTrigger = new PositionTrigger(
				new CircleElement(new Point(18, 18), Color.red, width),
				effect.getId(), RectangleElement.class, map.getId());

		MapElementFactory.buildMapElement(effect, map);
		MapElementFactory.buildMapElement(rectangleElement, map);
		MapElementFactory.buildMapElement(positionTrigger, map);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());

		rectangleElement = CampaignClient.getInstance().getBean(
				rectangleElement.getId(), 300);

		rectangleElement.setPosition(new Point(15, 15));
		sleep(10);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
	}

	@Test
	public void testSuiteEffect() {
		Map map = new Map("map");
		CampaignClient.getInstance().addBean(map);
		map = CampaignClient.getInstance().getBean(map.getId(), 2000);

		MapElementSize width = new MapElementSizePixel(10.0);
		MapElementSize height = new MapElementSizePixel(10.0);
		EffectSuite effectSuite = new EffectSuite(new CircleElement(new Point(
				18, 18), Color.red, width), false, RectangleElement.class,
				map.getId());

		BasicEffect basicEffect1 = new BasicEffect(new CircleElement(new Point(
				18, 18), Color.red, width), false, RectangleElement.class,
				map.getId());
		BasicEffect basicEffect2 = new BasicEffect(new CircleElement(new Point(
				18, 18), Color.red, width), false, RectangleElement.class,
				map.getId());
		effectSuite.addEffect(basicEffect1);
		effectSuite.addEffect(basicEffect2);
		PositionTrigger pt = new PositionTrigger(new CircleElement(new Point(
				18, 18), Color.red, width), effectSuite.getId(),
				RectangleElement.class, map.getId());

		MapElementFactory.buildMapElement(effectSuite, map);
		MapElementFactory.buildMapElement(basicEffect1, map);
		MapElementFactory.buildMapElement(basicEffect2, map);
		MapElementFactory.buildMapElement(pt, map);

		RectangleElement rectangleElement = new RectangleElement(
				new Point(4, 4), Color.BLACK, width, height);
		MapElementFactory.buildMapElement(rectangleElement, map);

		rectangleElement = CampaignClient.getInstance().getBean(
				rectangleElement.getId(), 300);
		rectangleElement.setPosition(new Point(20, 20));

	}

	@Test
	public void testDelayedEffect() {
		Map map = new Map("map");
		CampaignClient.getInstance().addBean(map);
		map = CampaignClient.getInstance().getBean(map.getId(), 2000);

		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		DelayedEffect effect = new DelayedEffect(new CircleElement(new Point(
				18, 18), Color.red, width), false, RectangleElement.class,
				10000, map.getId());
		BasicEffect t1 = new BasicEffect(new CircleElement(new Point(18, 18),
				Color.red, width), false, RectangleElement.class, map.getId());

		effect.addEffect(t1);

		MapElementFactory.buildMapElement(effect, map);
		MapElementFactory.buildMapElement(t1, map);

		RectangleElement rectangleElement = new RectangleElement(
				new Point(4, 4), Color.BLACK, width, height);
		MapElementFactory.buildMapElement(rectangleElement, map);

		rectangleElement = CampaignClient.getInstance().getBean(
				rectangleElement.getId(), 200);
		rectangleElement.setPosition(new Point(20, 20));

	}
}
