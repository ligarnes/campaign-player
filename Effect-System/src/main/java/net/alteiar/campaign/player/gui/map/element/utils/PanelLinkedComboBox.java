package net.alteiar.campaign.player.gui.map.element.utils;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.action.LinkedComboAddListener;

public class PanelLinkedComboBox extends JPanel{
	
		   private ArrayList<DefaultComboBoxModel[]> comboModels;
		   private ComboBoxWithModel[] comboBoxes;

		   public PanelLinkedComboBox(ArrayList<ArrayList<String[]>> list) {
			   comboModels=new ArrayList<DefaultComboBoxModel[]>();
			   for (int i = 0; i < list.size(); i++) {
					   DefaultComboBoxModel[] temp=new DefaultComboBoxModel[list.get(i).size()];
					   for(int j=0;j<list.get(i).size();j++){
						   temp[j]=new DefaultComboBoxModel(list.get(i).get(j));
				   		}
					   comboModels.add(i, temp);
				   }
			   comboBoxes=new ComboBoxWithModel[list.size()];	   
			   System.out.println("length="+comboBoxes.length);
			   for (int i = 0; i < comboBoxes.length; i++) {
					   comboBoxes[i] = new ComboBoxWithModel((comboModels.get(i))[0]);
					   comboBoxes[i].setSelectedIndex(i);
					   add(comboBoxes[i]);
				   }
		   }
		   
		   public ArrayList<DefaultComboBoxModel[]> getComboModels(){
			   return comboModels;
		   }
		   
		   public DefaultComboBoxModel[] getComboModelChoices(int indexComboBox){
			   return comboModels.get(indexComboBox);
		   }
		   
		   public DefaultComboBoxModel getComboModelChoice(int indexComboBox,int indexComboModel){
			   return comboModels.get(indexComboBox)[indexComboModel];
		   }
		   
		   public void setComboModels(ArrayList<DefaultComboBoxModel[]> comboModels){
			   this.comboModels=comboModels;
		   }
		   
		   public  void getComboModelChoices(int indexCombobox,DefaultComboBoxModel[] comboModelChoices){
			  comboModels.set(indexCombobox, comboModelChoices);
		   }
		   
		   public void getComboModelChoice(int indexCombobox,int indexComboModel,DefaultComboBoxModel comboModelChoice){
			   comboModels.get(indexCombobox)[indexComboModel]=comboModelChoice;
		   }
		   
		   public ComboBoxWithModel[] getComboBoxes()
		   {
			   return comboBoxes;
		   }
		   
		   public ComboBoxWithModel getComboBoxe(int i)
		   {
			   return comboBoxes[i];
		   }
		   
		   public void setComboBoxes(ComboBoxWithModel[] comboBoxes)
		   {
			   this.comboBoxes=comboBoxes;
		   }
		   
		   public void setComboBoxe(int i,ComboBoxWithModel comboBox)
		   {
			   comboBoxes[i]=comboBox;
		   }

		   public void setModelToComboBox(int indexComboBox,int indexComboModel)
		   {
			   comboBoxes[indexComboBox].setModel(comboModels.get(indexComboBox)[indexComboModel]);
		   }
}
