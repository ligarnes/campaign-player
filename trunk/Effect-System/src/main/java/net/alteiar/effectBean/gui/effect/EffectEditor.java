package net.alteiar.effectBean.gui.effect;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pathfinder.gui.mapElement.PathfinderCharacterElement;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.documents.map.Map;
import net.alteiar.effectBean.Effect;
import net.alteiar.effectBean.EffectSuite;
import net.alteiar.effectBean.xml.EffectChoiceListGenerator;
import net.alteiar.shared.UniqueID;
import net.alteiar.trigger.TriggerBean;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;

public class EffectEditor extends PanelMapElementEditor<Effect>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox listTrigger;
	private JButton addButton;
	private ArrayList<TriggerBean> triggers;
	private ArrayList<Integer> selectedTriggers;
	private Integer selectedTrigger;
	
	public EffectEditor(Effect mapElement) throws ClassNotFoundException {
		super(mapElement);
		this.add(new JLabel("Lier des effets au déclencheur:"), BorderLayout.NORTH);
		selectedTrigger=1;
		Map map=CampaignClient.getInstance().getBean(mapElement.getMapId());
		HashSet<UniqueID> elements=map.getElements();
		triggers=new ArrayList<TriggerBean>();
		selectedTriggers=new ArrayList<Integer>();
		for(UniqueID element:elements)
		{
			BasicBeans effect=CampaignClient.getInstance().getBean(element);
			if(Beans.isInstanceOf(effect, TriggerBean.class))
			{
				triggers.add((TriggerBean) effect);
			}
		}
		triggers.get(selectedTrigger).setSelected(true);
		String[] effectList=new String[triggers.size()];
		TriggerChoiceListGenerator tGenerator=new TriggerChoiceListGenerator();
		ArrayList<Class<?>> classList=tGenerator.getTriggerClasses(TriggerChoiceListGenerator.path); 
		ArrayList<String> triggerNameList=tGenerator.getTriggerName(TriggerChoiceListGenerator.path);
		for(int j=0;j<triggers.size();j++)
		{
			for(int i=0;i<classList.size();i++)
			{
				if(Beans.isInstanceOf(triggers.get(j),classList.get(i)))
				{
					effectList[j]=triggerNameList.get(i);
				}
			}
		}
		
		listTrigger=new JComboBox(effectList);
		listTrigger.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				triggers.get(selectedTrigger).setSelected(false);
				JComboBox temp=(JComboBox) e.getSource();
				selectedTrigger=temp.getSelectedIndex();
				triggers.get(selectedTrigger).setSelected(true);
			}
			
		});
		addButton=new JButton("Add");
		addButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				selectedTriggers.add(selectedTrigger);
			}
			
		});
		this.add(new JLabel("Ajouter un effet au déclencheur:"), BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.add(buildRow("Choix de l'effet: ", listTrigger));
		panelCenter.add(buildRow("Ajout de l'effet au déclencheur: ", addButton));
		this.add(panelCenter, BorderLayout.CENTER);
		
	}
	
	private static JPanel buildRow(String name, JPanel panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}
	
	private static JPanel buildRow(String name, JComponent panel) {
		JPanel container = new JPanel(new FlowLayout());
		container.add(new JLabel(name));
		container.add(panel);
		return container;
	}
	
	public Boolean isDataValid() {
		return true;
	}

	public String getInvalidMessage() {
		return "";
	}

	@Override
	public void applyModification() {
		
		Effect e = getMapElement();
        Set<Integer> set = new HashSet() ;
        set.addAll(selectedTriggers) ;
        ArrayList<Integer> distinctList = new ArrayList(set) ;
        for(Integer indexEffect: distinctList)
        {
        	EffectSuite s=(EffectSuite) triggers.get(indexEffect).getEffect();
        	s.addEffect(e);
        }
	}

}
