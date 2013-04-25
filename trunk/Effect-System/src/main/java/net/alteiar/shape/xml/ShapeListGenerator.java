package net.alteiar.shape.xml;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.map.element.utils.ComboListFromXML;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;
import net.alteiar.utils.XMLFileLoader;

public class ShapeListGenerator implements ComboListFromXML{

	public static String path="./ressources/EffectRessource/xml/Shape.xml";
	
	public ArrayList<ArrayList<String[]>> CreateComboList(String path) {
		ArrayList<ArrayList<String[]>> result=new ArrayList<ArrayList<String[]>>();
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> triggerTypes=xmlDocument.getNodeNameInTag("ShapeElement");
		result.add(new ArrayList<String[]>());
		
		String[] temp = new String[triggerTypes.size()];
	    temp = triggerTypes.toArray(temp);
		result.get(0).add(temp);
		return result;
	}
	
	public Class<?> getShapeBuilderClass(String path,String name) throws ClassNotFoundException {
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> type=xmlDocument.getNodeItemInTag("shape", name);
		return Class.forName(type.get(0));
	}
	
	public ArrayList<ArrayList<JPanel>> getShapeBuilders(String path,ArrayList<ArrayList<String[]>> name) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<ArrayList<JPanel>> result=new ArrayList<ArrayList<JPanel>>();
		for(int i=0;i<name.size();i++)
		{
			result.add(new ArrayList<JPanel>());
			for(int j=0;j<name.get(i).size();j++)
			{
				for(int k=0;k<name.get(i).get(j).length;k++)
				{
					result.get(i).add((JPanel)(getShapeBuilderClass(path,name.get(i).get(j)[k]).newInstance()));
				}
			}
		}
		
		return result;
	}

}
