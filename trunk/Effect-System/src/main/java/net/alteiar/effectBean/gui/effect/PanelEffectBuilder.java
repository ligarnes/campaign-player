package net.alteiar.effectBean.gui.effect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.action.ComboBoxChangeFunctionFirstCombo;
import net.alteiar.campaign.player.gui.map.element.action.PanelChangeFunctionFirstCombo;
import net.alteiar.campaign.player.gui.map.element.utils.PanelComboBoxLinkedWithPanel;
import net.alteiar.campaign.player.gui.map.element.utils.PanelElementSize;
import net.alteiar.campaign.player.gui.map.element.utils.PanelLinkedComboBox;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.effectBean.BasicEffect;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.effectBean.xml.EffectChoiceListGenerator;
import net.alteiar.map.elements.CircleElement;
import net.alteiar.map.elements.ColoredShape;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shape.xml.ShapeListGenerator;
import net.alteiar.trigger.TriggerBean;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizePixel;

public class PanelEffectBuilder extends PanelMapElementBuilder{

	private static final long serialVersionUID = 1L;

	private  PanelComboBoxLinkedWithPanel shapePanel=null;
	private  PanelLinkedComboBox listEffect=null;
	private  JCheckBox isOneUse=null;
	private  ShapeListGenerator sGenerator=null;
	private  EffectChoiceListGenerator eGenerator=null;
	
	public PanelEffectBuilder() {
		try {
			eGenerator=new EffectChoiceListGenerator();
			sGenerator=new ShapeListGenerator();
			
			ArrayList<ArrayList<String[]>> shapeChoice=sGenerator.CreateComboList(ShapeListGenerator.path);
			ArrayList<ArrayList<String[]>> effectChoice=eGenerator.CreateComboList(EffectChoiceListGenerator.path);
			ArrayList<ArrayList<JPanel>> shapeBuilders;

			shapeBuilders = sGenerator.getShapeBuilders(ShapeListGenerator.path, shapeChoice);
		
		
			shapePanel= new PanelComboBoxLinkedWithPanel(shapeChoice,shapeBuilders);
			listEffect = new PanelLinkedComboBox(effectChoice);
			isOneUse=new JCheckBox("Effet à une utilisation:");
			
			ComboBoxChangeFunctionFirstCombo effectAddListener=new ComboBoxChangeFunctionFirstCombo(listEffect); 
			PanelChangeFunctionFirstCombo shapeAddListener=new PanelChangeFunctionFirstCombo(shapePanel); 
			
			effectAddListener.addListener();
			shapeAddListener.addListener();
			this.setLayout(new BorderLayout());
			this.add(new JLabel("Ajouter un déclencheur"), BorderLayout.NORTH);
	
			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
			panelCenter.add(buildRow("Choix de la zone d'effet: ", shapePanel));
			panelCenter.add(isOneUse);
			panelCenter.add(buildRow("Choix de l'effet: ", listEffect));
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
		return "Effet";
	}
	@Override
	public String getElementDescription() {
		return "Ajoute un Effet sur la carte";
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	@Override
	public MapElement buildMapElement(Point position) {
		Effect result=null;
		try {
			Class<? extends Effect> tClass=(Class<? extends Effect>) eGenerator.getEffectClass(EffectChoiceListGenerator.path,(String)listEffect.getComboBoxe(0).getSelectedItem());
			Class<? extends BasicBeans> elementClass=(Class<? extends BasicBeans>) eGenerator.getElementClass(EffectChoiceListGenerator.path,(String)listEffect.getComboBoxe(1).getSelectedItem(),(String)listEffect.getComboBoxe(0).getSelectedItem());
			ColoredShape shape=(ColoredShape) ((PanelMapElementBuilder)shapePanel.getModelListChoice(0,shapePanel.getCurrentPan(0))).buildMapElement(position);
			Boolean oneUse=isOneUse.isSelected();
			System.out.println("oneUse="+oneUse);
			Class<?>[] parameterTypes={ColoredShape.class,Boolean.class,Class.class};
			Constructor<? extends Effect> tConstructor=tClass.getConstructor(parameterTypes);
			Object[] parameter={shape,oneUse,elementClass};
			result=tConstructor.newInstance(parameter);
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
		}finally{
			return result;
		}
	}

}
