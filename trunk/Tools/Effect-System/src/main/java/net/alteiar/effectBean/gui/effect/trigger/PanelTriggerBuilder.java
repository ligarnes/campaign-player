package net.alteiar.effectBean.gui.effect.trigger;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.action.ComboBoxChangeFunctionFirstCombo;
import net.alteiar.campaign.player.gui.map.element.action.PanelChangeFunctionFirstCombo;
import net.alteiar.campaign.player.gui.map.element.utils.PanelComboBoxLinkedWithPanel;
import net.alteiar.campaign.player.gui.map.element.utils.PanelLinkedComboBox;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shape.xml.ShapeListGenerator;
import net.alteiar.trigger.TriggerBean;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;

public class PanelTriggerBuilder extends PanelMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private PanelComboBoxLinkedWithPanel shapePanel = null;
	private PanelLinkedComboBox listTrigger = null;
	private ShapeListGenerator sGenerator = null;
	private TriggerChoiceListGenerator tGenerator = null;

	public PanelTriggerBuilder() {
		try {
			tGenerator = new TriggerChoiceListGenerator();
			sGenerator = new ShapeListGenerator();

			ArrayList<ArrayList<String[]>> shapeChoice = sGenerator
					.CreateComboList(ShapeListGenerator.path);
			ArrayList<ArrayList<String[]>> triggerChoice = tGenerator
					.CreateComboList(TriggerChoiceListGenerator.path);
			ArrayList<ArrayList<JPanel>> shapeBuilders;

			shapeBuilders = sGenerator.getShapeBuilders(
					ShapeListGenerator.path, shapeChoice);

			shapePanel = new PanelComboBoxLinkedWithPanel(shapeChoice,
					shapeBuilders);
			listTrigger = new PanelLinkedComboBox(triggerChoice);

			ComboBoxChangeFunctionFirstCombo triggerAddListener = new ComboBoxChangeFunctionFirstCombo(
					listTrigger);
			PanelChangeFunctionFirstCombo shapeAddListener = new PanelChangeFunctionFirstCombo(
					shapePanel);

			triggerAddListener.addListener();
			shapeAddListener.addListener();
			this.setLayout(new BorderLayout());
			this.add(new JLabel("Ajouter un déclencheur"), BorderLayout.NORTH);

			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
			panelCenter.add(buildRow("Choix de la zone d'activation: ",
					shapePanel));
			panelCenter.add(buildRow("Choix de déclencheur: ", listTrigger));
			this.add(panelCenter, BorderLayout.CENTER);
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

	private static JPanel buildRow(String name, JPanel panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}

	@Override
	public Boolean isAvailable() {
		return true;
	}

	@Override
	public String getElementName() {
		return "Déclencheur";
	}

	@Override
	public String getElementDescription() {
		return "Ajoute un déclencheur sur la carte";
	}

	@Override
	public MapElement buildMapElement(Point position) {
		TriggerBean result = null;
		try {
			System.out.println("ici1");
			Class<? extends TriggerBean> tClass = (Class<? extends TriggerBean>) tGenerator
					.getTriggerClass(TriggerChoiceListGenerator.path,
							(String) listTrigger.getComboBoxe(0)
									.getSelectedItem());
			System.out.println("ici3");
			Class<? extends BasicBean> activatorClass = (Class<? extends BasicBean>) tGenerator
					.getActivatorClass(TriggerChoiceListGenerator.path,
							(String) listTrigger.getComboBoxe(1)
									.getSelectedItem(), (String) listTrigger
									.getComboBoxe(0).getSelectedItem());
			System.out.println("ici4");
			ColoredShape shape = (ColoredShape) ((PanelMapElementBuilder) shapePanel
					.getModelListChoice(0, shapePanel.getCurrentPan(0)))
					.buildMapElement(position);
			System.out.println("ici5");
			// TODO must bug here null pointer exception
			Effect e = new EffectSuite(shape, false, MapElement.class, null);
			System.out.println("ici6");
			Class<?>[] parameterTypes = { ColoredShape.class, Effect.class,
					Class.class };
			System.out.println("ici7");
			Constructor<? extends TriggerBean> tConstructor = tClass
					.getConstructor(parameterTypes);
			System.out.println("ici8");
			Object[] parameter = { shape, e, activatorClass };
			System.out.println("ici9");
			result = tConstructor.newInstance(parameter);
			System.out.println("ici10");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			return result;
		}
	}

}
