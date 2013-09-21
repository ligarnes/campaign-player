package net.alteiar.campaign.player.gui.map.element.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.alteiar.campaign.player.gui.map.element.utils.ComboBoxWithModel;
import net.alteiar.campaign.player.gui.map.element.utils.PanelComboBoxLinkedWithPanel;


public class PanelChangeFunctionFirstCombo extends LinkedComboAddListener{
	public PanelChangeFunctionFirstCombo(PanelComboBoxLinkedWithPanel linkedComboBox) {
		super(linkedComboBox);
	}

	@Override
	public void addListener() {
		this.linkedComboBox.getComboBoxe(0).addActionListener(new FirstComboListener());
		for(int i=1;i<this.linkedComboBox.getComboBoxes().length;i++)
		{
			this.linkedComboBox.getComboBoxe(i).addActionListener(new OtherComboListener());
		}
		
	}

	 private class FirstComboListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			 ComboBoxWithModel combo = (ComboBoxWithModel) e.getSource();
	         String selection = combo.getSelectedItem().toString();
	         combo.setSelectedItem(selection);
	         int index=combo.getSelectedIndex();
	         
	         for(int i=1;i<linkedComboBox.getComboBoxes().length;i++)
	         {
	        	 linkedComboBox.setModelToComboBox(i, index);
	         }
	         PanelComboBoxLinkedWithPanel temp=(PanelComboBoxLinkedWithPanel)linkedComboBox;
	         for(int i=0;i<temp.getModelList().size();i++)
	         {
	        	 System.out.println("ici");
		         temp.setModelToPanel(i, index);
	         }
		}
	 }
	 
	 private class OtherComboListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			 ComboBoxWithModel combo = (ComboBoxWithModel) e.getSource();
	         String selection = combo.getSelectedItem().toString();
	         combo.setSelectedItem(selection);
		}
	 }
}
