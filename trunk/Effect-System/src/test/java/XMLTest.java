import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.DelayedEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.effectBean.xml.EffectChoiceListGenerator;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.shape.xml.ShapeListGenerator;
import net.alteiar.trigger.PositionTrigger;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;
import net.alteiar.utils.XMLFileLoader;
import net.alteiar.utils.images.SerializableImage;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizePixel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathfinder.character.PathfinderCharacter;
import pathfinder.mapElement.character.PathfinderCharacterElement;


public class XMLTest extends NewCampaignTest{

	@Before
	public void beforeTest() {
		super.beforeTest();
	}

	@After
	public void afterTest() {
		super.afterTest();
	}

	@Test
    public void testListTrigger()
	{	TriggerChoiceListGenerator generator=new TriggerChoiceListGenerator();
		ArrayList<ArrayList<String[]>>result=generator.CreateComboList("src/main/java/net/alteiar/trigger/xml/Trigger.xml");
		for(int i=0;i<result.size();i++)
		{
			System.out.println("Array 1: size:"+result.size());
			for(int j=0;j<result.get(i).size();j++)
			{
				System.out.println("Array 2: size:"+result.get(i).size());
				for(int k=0;k<result.get(i).get(j).length;k++)
				{
					System.out.println("string:"+result.get(i).get(j)[k]);
				}
			}
		}
	}
	
	@Test
    public void testListEffect()
	{	
		EffectChoiceListGenerator generator=new EffectChoiceListGenerator();
		ArrayList<ArrayList<String[]>>result=generator.CreateComboList("src/main/java/net/alteiar/effectBean/xml/Effect.xml");
		for(int i=0;i<result.size();i++)
		{
			System.out.println("Array 1: size:"+result.size());
			for(int j=0;j<result.get(i).size();j++)
			{
				System.out.println("Array 2: size:"+result.get(i).size());
				for(int k=0;k<result.get(i).get(j).length;k++)
				{
					System.out.println("string:"+result.get(i).get(j)[k]);
				}
			}
		}
	}
	
	@Test
    public void testListShape()
	{	ShapeListGenerator generator=new ShapeListGenerator();
		ArrayList<ArrayList<String[]>>result=generator.CreateComboList("src/main/java/net/alteiar/shape/xml/Shape.xml");
		for(int i=0;i<result.size();i++)
		{
			System.out.println("Array 1: size:"+result.size());
			for(int j=0;j<result.get(i).size();j++)
			{
				System.out.println("Array 2: size:"+result.get(i).size());
				for(int k=0;k<result.get(i).get(j).length;k++)
				{
					System.out.println("string:"+result.get(i).get(j)[k]);
				}
			}
		}
	}
	
	@Test
    public void testClassTriggerType()
	{
		try {
			TriggerChoiceListGenerator generator=new TriggerChoiceListGenerator();
			Class<?> c=generator.getTriggerClass(TriggerChoiceListGenerator.path,"position");
			System.out.println("classe ="+c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
    public void testClassEffectType()
	{
		try {
			EffectChoiceListGenerator generator=new EffectChoiceListGenerator();
			Class<?> c=generator.getEffectClass("src/main/java/net/alteiar/EffectBean/xml/Effect.xml","IdleEffect");
			System.out.println("classe ="+c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void testClassActivatorType()
	{
		try {
			TriggerChoiceListGenerator generator=new TriggerChoiceListGenerator();
			Class<?> c=generator.getActivatorClass("src/main/java/net/alteiar/trigger/xml/Trigger.xml", "circle element", "position");
			System.out.println("classe ="+c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void testClassElementType()
	{
		try {
			EffectChoiceListGenerator generator=new EffectChoiceListGenerator();
			Class<?> c=generator.getElementClass("src/main/java/net/alteiar/EffectBean/xml/Effect.xml", "pathfinder character", "IdleEffect");
			System.out.println("classe ="+c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
    public void testShapeBuilders()
	{			
		try {
			ShapeListGenerator generator=new ShapeListGenerator();
			ArrayList<ArrayList<String[]>>result=generator.CreateComboList("src/main/java/net/alteiar/shape/xml/Shape.xml");
			ArrayList<ArrayList<JPanel>> list=generator.getShapeBuilders("src/main/java/net/alteiar/shape/xml/Shape.xml", result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println("Array 1: size:"+list.size());
				for(int j=0;j<list.get(i).size();j++)
				{
					System.out.println("Array 2: size:"+list.get(i).size());
					System.out.println("type builder="+list.get(i).get(j).getClass().getCanonicalName());
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
