import java.awt.Color;
import java.awt.Point;
import java.sql.Time;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.DelayedEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.trigger.PositionTrigger;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizePixel;



public class EffectTest extends NewCampaignTest {
	
	@Before
	public void beforeTest() {
		super.beforeTest();
	}

	@After
	public void afterTest() {
		super.afterTest();
	}

	@Test
    public void testActivate()
	{	try {
			MapElementSize width = new MapElementSizePixel(20.0);
			MapElementSize height = new MapElementSizePixel(20.0);
			Effect e = new BasicEffect(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class);
			ImageBean image=new ImageBean(new SerializableImage());
			PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
			PathfinderCharacterElement ce=new PathfinderCharacter(new Point(1,1), c);
			Map m=new Map("map");
			PositionTrigger pt=new PositionTrigger(new CircleElement(new Point(18,18),Color.red,width ),e,PathfinderCharacterElement.class);
			ce.setMapId(m.getId());
			e.setMapId(m.getId());
			pt.setMapId(m.getId());
			CampaignClient.getInstance().addBean(m);
			CampaignClient.getInstance().addBean(image);
			CampaignClient.getInstance().addBean(c);
			CampaignClient.getInstance().addBean(ce);
			CampaignClient.getInstance().addBean(e);
			ce=CampaignClient.getInstance().getBean(ce.getId());
			ce.setPosition(new Point(20,20));
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Test
    public void testSuiteEffect()
	{
		try {
			MapElementSize width = new MapElementSizePixel(20.0);
			MapElementSize height = new MapElementSizePixel(20.0);
			EffectSuite e;
		
			e = new EffectSuite(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class);
		
			BasicEffect t1=new BasicEffect(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class);
			BasicEffect t2=new BasicEffect(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class);
			e.addEffect(t1);
			e.addEffect(t2);
			PositionTrigger pt=new PositionTrigger(new CircleElement(new Point(18,18),Color.red,width ),e,PathfinderCharacterElement.class);
			ImageBean image=new ImageBean(new SerializableImage());
			PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
			PathfinderCharacterElement ce=new PathfinderCharacterElement(new Point(1,1), c);
			Map m=new Map("map");
			ce.setMapId(m.getId());
			t1.setMapId(m.getId());
			t2.setMapId(m.getId());
			e.setMapId(m.getId());
			pt.setMapId(m.getId());
			CampaignClient.getInstance().addBean(m);
			CampaignClient.getInstance().addBean(image);
			CampaignClient.getInstance().addBean(c);
			CampaignClient.getInstance().addBean(ce);
			CampaignClient.getInstance().addBean(t1);
			CampaignClient.getInstance().addBean(t2);
			CampaignClient.getInstance().addBean(e);
			CampaignClient.getInstance().addBean(pt);
			ce=CampaignClient.getInstance().getBean(ce.getId());
			ce.setPosition(new Point(20,20));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
    public void testDelayedEffect()
	{
		try {
			MapElementSize width = new MapElementSizePixel(20.0);
			MapElementSize height = new MapElementSizePixel(20.0);
			DelayedEffect e=new DelayedEffect(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class,10000);
			BasicEffect t1;

			t1 = new BasicEffect(new CircleElement(new Point(18,18),Color.red, width ),false,PathfinderCharacter.class);
		
			e.addEffect(t1);
			ImageBean image=new ImageBean(new SerializableImage());
			PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
			PathfinderCharacterElement ce=new PathfinderCharacterElement(new Point(1,1), c);
			Map m=new Map("map");
			ce.setMapId(m.getId());
			e.setMapId(m.getId());
			t1.setMapId(m.getId());
			CampaignClient.getInstance().addBean(m);
			CampaignClient.getInstance().addBean(image);
			CampaignClient.getInstance().addBean(c);
			CampaignClient.getInstance().addBean(ce);
			CampaignClient.getInstance().addBean(t1);
			CampaignClient.getInstance().addBean(e);
			ce=CampaignClient.getInstance().getBean(ce.getId());
			ce.setPosition(new Point(20,20));
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
