import java.util.ArrayList;

import javax.swing.JPanel;

import net.alteiar.effectBean.xml.EffectChoiceListGenerator;
import net.alteiar.shape.xml.ShapeListGenerator;
import net.alteiar.test.NewCampaignTest;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;

import org.junit.Test;

public class XMLTest extends NewCampaignTest {

	private static String TRIGGER_XML = "./ressources/EffectRessource/xml/Trigger.xml";
	private static String EFFECT_XML = "./ressources/EffectRessource/xml/Effect.xml";
	private static String SHAPE_XML = "./ressources/EffectRessource/xml/Shape.xml";

	@Test
	public void testListTrigger() {
		TriggerChoiceListGenerator generator = new TriggerChoiceListGenerator();
		ArrayList<ArrayList<String[]>> result = generator
				.CreateComboList(TRIGGER_XML);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("Array 1: size:" + result.size());
			for (int j = 0; j < result.get(i).size(); j++) {
				System.out.println("Array 2: size:" + result.get(i).size());
				for (int k = 0; k < result.get(i).get(j).length; k++) {
					System.out.println("string:" + result.get(i).get(j)[k]);
				}
			}
		}
	}

	@Test
	public void testListEffect() {
		EffectChoiceListGenerator generator = new EffectChoiceListGenerator();
		ArrayList<ArrayList<String[]>> result = generator
				.CreateComboList(EFFECT_XML);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("Array 1: size:" + result.size());
			for (int j = 0; j < result.get(i).size(); j++) {
				System.out.println("Array 2: size:" + result.get(i).size());
				for (int k = 0; k < result.get(i).get(j).length; k++) {
					System.out.println("string:" + result.get(i).get(j)[k]);
				}
			}
		}
	}

	@Test
	public void testListShape() {
		ShapeListGenerator generator = new ShapeListGenerator();
		ArrayList<ArrayList<String[]>> result = generator
				.CreateComboList(SHAPE_XML);
		for (int i = 0; i < result.size(); i++) {
			System.out.println("Array 1: size:" + result.size());
			for (int j = 0; j < result.get(i).size(); j++) {
				System.out.println("Array 2: size:" + result.get(i).size());
				for (int k = 0; k < result.get(i).get(j).length; k++) {
					System.out.println("string:" + result.get(i).get(j)[k]);
				}
			}
		}
	}

	@Test
	public void testClassTriggerType() {
		try {
			TriggerChoiceListGenerator generator = new TriggerChoiceListGenerator();
			Class<?> c = generator.getTriggerClass(
					TriggerChoiceListGenerator.path, "position");
			System.out.println("classe =" + c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testClassEffectType() {
		try {
			EffectChoiceListGenerator generator = new EffectChoiceListGenerator();
			Class<?> c = generator.getEffectClass(EFFECT_XML, "IdleEffect");
			System.out.println("classe =" + c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testClassActivatorType() {
		try {
			TriggerChoiceListGenerator generator = new TriggerChoiceListGenerator();
			Class<?> c = generator.getActivatorClass(TRIGGER_XML,
					"circle element", "position");
			System.out.println("classe =" + c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testClassElementType() {
		try {
			EffectChoiceListGenerator generator = new EffectChoiceListGenerator();
			Class<?> c = generator.getElementClass(EFFECT_XML,
					"pathfinder character", "IdleEffect");
			System.out.println("classe =" + c.getCanonicalName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testShapeBuilders() {
		try {
			ShapeListGenerator generator = new ShapeListGenerator();
			ArrayList<ArrayList<String[]>> result = generator
					.CreateComboList(SHAPE_XML);
			ArrayList<ArrayList<JPanel>> list = generator.getShapeBuilders(
					SHAPE_XML, result);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Array 1: size:" + list.size());
				for (int j = 0; j < list.get(i).size(); j++) {
					System.out.println("Array 2: size:" + list.get(i).size());
					System.out.println("type builder="
							+ list.get(i).get(j).getClass().getCanonicalName());
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
