import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.MapBean;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.DelayedEffect;
import net.alteiar.effectBean.mine.EffectSuite;
import net.alteiar.effectBean.mine.MyEffect;
import net.alteiar.effectBean.mine.OneShotEffect;
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

	private static class MyInternalEffect extends MyEffect {
		private static final long serialVersionUID = 1L;

		private int effectActivationCount;
		private int effectDesactivationCount;

		public MyInternalEffect() {
			effectActivationCount = 0;
			effectDesactivationCount = 0;
		}

		public int getActivationCount() {
			return effectActivationCount;
		}

		public int getDesactivationCount() {
			return effectDesactivationCount;
		}

		@Override
		public void activate() {
			effectActivationCount++;
		}

		@Override
		public void desactivate() {
			effectDesactivationCount++;
		}

	}

	private static class MyOneShotInternalEffect extends OneShotEffect {
		private static final long serialVersionUID = 1L;

		private int effectActivationCount;
		private int effectDesactivationCount;

		public MyOneShotInternalEffect() {
			effectActivationCount = 0;
			effectDesactivationCount = 0;
		}

		public int getActivationCount() {
			return effectActivationCount;
		}

		public int getDesactivationCount() {
			return effectDesactivationCount;
		}

		@Override
		protected void singleActivate() {
			effectActivationCount++;
		}

		@Override
		public void desactivate() {
			effectDesactivationCount++;
		}
	}

	@Test
	public void testActivate() {
		MapBean map = addBean(new MapBean("map"));

		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		MyInternalEffect effect = addBean(new MyInternalEffect());

		RectangleElement rectangleElement = new RectangleElement(
				new Point(0, 0), Color.BLACK, width, height);

		PositionTrigger positionTrigger = new PositionTrigger(
				new CircleElement(new Point(25, 25), Color.red, width),
				effect.getId(), RectangleElement.class, map.getId());

		MapElementFactory.buildMapElement(rectangleElement, map);
		MapElementFactory.buildMapElement(positionTrigger, map);
		positionTrigger = getBeans(positionTrigger);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());
		assertEquals("the effect should have been activated 0", 0,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				effect.getDesactivationCount());

		rectangleElement = getBeans(rectangleElement);

		rectangleElement.setPosition(new Point(15, 15));
		sleep(10);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
		assertEquals("the effect should have been activated once", 1,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				effect.getDesactivationCount());

		rectangleElement.setPosition(new Point(0, 0));

		sleep(10);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());
		assertEquals("the effect should have been activated once", 1,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated once", 1,
				effect.getDesactivationCount());

		RectangleElement rectangle2 = new RectangleElement(new Point(15, 15),
				Color.BLACK, width, height);
		MapElementFactory.buildMapElement(rectangle2, map);

		getBeans(rectangle2);
		sleep(10);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
		assertEquals("the effect should have been activated twice", 2,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated once", 1,
				effect.getDesactivationCount());
	}

	@Test
	public void testOneShotEffect() {
		MapBean map = addBean(new MapBean("map"));

		MapElementSize width = new MapElementSizePixel(20.0);
		MapElementSize height = new MapElementSizePixel(20.0);
		MyOneShotInternalEffect effect = addBean(new MyOneShotInternalEffect());

		RectangleElement rectangleElement = new RectangleElement(
				new Point(0, 0), Color.BLACK, width, height);

		PositionTrigger positionTrigger = new PositionTrigger(
				new CircleElement(new Point(25, 25), Color.red, width),
				effect.getId(), RectangleElement.class, map.getId());

		MapElementFactory.buildMapElement(rectangleElement, map);
		MapElementFactory.buildMapElement(positionTrigger, map);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());

		assertEquals("the effect should have been activated 0", 0,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				effect.getDesactivationCount());

		rectangleElement = getBeans(rectangleElement);

		rectangleElement.setPosition(new Point(15, 15));
		sleep(100);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
		assertEquals("the effect should have been activated once", 1,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				effect.getDesactivationCount());

		rectangleElement.setPosition(new Point(0, 0));

		sleep(100);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());
		assertEquals("the effect should have been activated once", 1,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated once", 1,
				effect.getDesactivationCount());

		RectangleElement rectangle2 = new RectangleElement(new Point(15, 15),
				Color.BLACK, width, height);
		MapElementFactory.buildMapElement(rectangle2, map);

		sleep(100);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
		assertEquals("the effect should have been activated once", 1,
				effect.getActivationCount());
		assertEquals("the effect should have been desactivated once", 1,
				effect.getDesactivationCount());
	}

	@Test
	public void testSuiteEffect() {
		MapBean map = addBean(new MapBean("map"));

		MapElementSize width = new MapElementSizePixel(10.0);
		MapElementSize height = new MapElementSizePixel(10.0);
		EffectSuite effectSuite = addBean(new EffectSuite());

		MyInternalEffect basicEffect1 = addBean(new MyInternalEffect());
		MyInternalEffect basicEffect2 = addBean(new MyInternalEffect());

		effectSuite.addEffect(basicEffect1.getId());
		effectSuite.addEffect(basicEffect2.getId());

		PositionTrigger positionTrigger = new PositionTrigger(
				new CircleElement(new Point(20, 20), Color.red, width),
				effectSuite.getId(), RectangleElement.class, map.getId());

		MapElementFactory.buildMapElement(positionTrigger, map);
		positionTrigger = getBeans(positionTrigger);

		RectangleElement rectangleElement = new RectangleElement(
				new Point(0, 0), Color.BLACK, width, height);
		MapElementFactory.buildMapElement(rectangleElement, map);

		rectangleElement = getBeans(rectangleElement);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());
		assertEquals("the effect should have been activated 0", 0,
				basicEffect1.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				basicEffect1.getDesactivationCount());
		assertEquals("the effect should have been activated 0", 0,
				basicEffect2.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				basicEffect2.getDesactivationCount());

		rectangleElement.setPosition(new Point(20, 20));

		sleep(10);

		assertTrue("trigger should be activated", positionTrigger.isActivate());
		assertEquals("the effect should have been activated 1", 1,
				basicEffect1.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				basicEffect1.getDesactivationCount());
		assertEquals("the effect should have been activated 1", 1,
				basicEffect2.getActivationCount());
		assertEquals("the effect should have been desactivated 0", 0,
				basicEffect2.getDesactivationCount());

		rectangleElement.setPosition(new Point(0, 0));

		sleep(10);

		assertTrue("trigger should'nt be activated",
				!positionTrigger.isActivate());
		assertEquals("the effect should have been activated 1", 1,
				basicEffect1.getActivationCount());
		assertEquals("the effect should have been desactivated 1", 1,
				basicEffect1.getDesactivationCount());
		assertEquals("the effect should have been activated 1", 1,
				basicEffect2.getActivationCount());
		assertEquals("the effect should have been desactivated 1", 1,
				basicEffect2.getDesactivationCount());

	}

	// @Test
	public void testDelayedEffect() {
		MapBean map = new MapBean("map");
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
