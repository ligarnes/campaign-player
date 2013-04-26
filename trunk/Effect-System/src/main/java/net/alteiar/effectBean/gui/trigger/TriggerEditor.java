package net.alteiar.effectBean.gui.trigger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.effectBean.Effect;
import net.alteiar.trigger.TriggerBean;

public class TriggerEditor extends PanelMapElementEditor<TriggerBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox listEffect;
	
	public TriggerEditor(TriggerBean mapElement) {
		super(mapElement);
		ArrayList<BasicBeans> effects=CampaignClient.getInstance().getBeanFromClass(Effect.class);
		for(BasicBeans effect:effects)
		{
			Effect effet=(Effect)effect;
			
		}
		
	}
	
	public Boolean isDataValid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInvalidMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyModification() {
		// TODO Auto-generated method stub
		
	}

}
