import java.awt.Point;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterEffectElement;
import pathfinder.mapElement.character.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectManager;
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
}
