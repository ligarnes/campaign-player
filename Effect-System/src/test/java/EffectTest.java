import java.awt.Color;
import java.awt.Point;
import java.sql.Time;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterEffectElement;
import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.DelayedEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectManager;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.image.ImageBean;
import net.alteiar.utils.images.SerializableImage;



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
	{
		Effect e=new BasicEffect(new Point(1,1), false);
		ImageBean image=new ImageBean(new SerializableImage());
		PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
		PathfinderCharacterEffectElement ce=new PathfinderCharacterEffectElement(new Point(0,0),c.getId());
		Map m=new Map("map");
		ce.setMapId(m.getId());
		e.setMapId(m.getId());
		CampaignClient.getInstance().addBean(m);
		CampaignClient.getInstance().addBean(image);
		CampaignClient.getInstance().addBean(c);
		CampaignClient.getInstance().addBean(ce);
		CampaignClient.getInstance().addBean(e);
		EffectManager.getInstance().addEffect(e);
		ce.setPosition(new Point(12,12));
	}
	
	@Test
    public void testSuiteEffect()
	{
		EffectSuite e=new EffectSuite(new Point(1,1), false);
		BasicEffect t1=new BasicEffect(new Point(1,1), false);
		BasicEffect t2=new BasicEffect(new Point(2,2), false);
		e.addEffect(t1);
		e.addEffect(t2);
		ImageBean image=new ImageBean(new SerializableImage());
		PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
		PathfinderCharacterEffectElement ce=new PathfinderCharacterEffectElement(new Point(0,0),c.getId());
		Map m=new Map("map");
		ce.setMapId(m.getId());
		e.setMapId(m.getId());
		CampaignClient.getInstance().addBean(m);
		CampaignClient.getInstance().addBean(image);
		CampaignClient.getInstance().addBean(c);
		CampaignClient.getInstance().addBean(ce);
		CampaignClient.getInstance().addBean(t1);
		CampaignClient.getInstance().addBean(t2);
		CampaignClient.getInstance().addBean(e);
		EffectManager.getInstance().addEffect(e);
		ce.setPosition(new Point(12,12));
	}

	@Test
    public void testDelayedEffect()
	{
		DelayedEffect e=new DelayedEffect(new Point(1,1), Color.red,false,10000);
		BasicEffect t1=new BasicEffect(new Point(1,1), false);
		e.addEffect(t1);
		ImageBean image=new ImageBean(new SerializableImage());
		PathfinderCharacter c=new PathfinderCharacter("patrick", 15, 15,image.getId());
		PathfinderCharacterEffectElement ce=new PathfinderCharacterEffectElement(new Point(0,0),c.getId());
		Map m=new Map("map");
		ce.setMapId(m.getId());
		e.setMapId(m.getId());
		CampaignClient.getInstance().addBean(m);
		CampaignClient.getInstance().addBean(image);
		CampaignClient.getInstance().addBean(c);
		CampaignClient.getInstance().addBean(ce);
		CampaignClient.getInstance().addBean(t1);
		CampaignClient.getInstance().addBean(e);
		EffectManager.getInstance().addEffect(e);
		ce.setPosition(new Point(12,12));
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
