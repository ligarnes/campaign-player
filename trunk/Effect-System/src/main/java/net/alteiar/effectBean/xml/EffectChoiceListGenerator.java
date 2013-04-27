package net.alteiar.effectBean.xml;

import java.net.URL;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.utils.ComboListFromXML;
import net.alteiar.trigger.xml.TriggerChoiceListGenerator;
import net.alteiar.utils.XMLFileLoader;

public class EffectChoiceListGenerator implements ComboListFromXML{
	
	public static String path="./ressources/EffectRessource/xml/Effect.xml";

	public ArrayList<ArrayList<String[]>> CreateComboList(String path) {
		ArrayList<ArrayList<String[]>> result=new ArrayList<ArrayList<String[]>>();
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> triggerTypes=xmlDocument.getNodeNameInTag("EffectTypes");
		result.add(new ArrayList<String[]>());
		
		String[] temp = new String[triggerTypes.size()];
	    temp = triggerTypes.toArray(temp);
		result.get(0).add(temp);
		
		
		result.add(new ArrayList<String[]>());
		for(int i=0;i<result.get(0).get(0).length;i++)
		{
			ArrayList<String> activatorsTypes=xmlDocument.getNodesInTagAndName("EffectAssociation", (result.get(0).get(0)[i]));
			
			String[] temp2 = new String[activatorsTypes.size()];
		    temp2 = activatorsTypes.toArray(temp2);
			result.get(1).add(temp2);
		}
		
		return result;
	}
	
	public Class<?> getEffectClass(String path,String name) throws ClassNotFoundException {
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> type=xmlDocument.getNodeItemInTag("Effect", name);
		return Class.forName(type.get(0));
	}
	
	public Class<?> getElementClass(String path,String name,String triggerName) throws ClassNotFoundException {
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		String type=xmlDocument.getNodeItemInTag("Effect","class", triggerName,name);
		return Class.forName(type);
	}
	
	public ArrayList<Class<?>> getEffectClasses(String path) throws ClassNotFoundException {
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> types=xmlDocument.getNodeItemInTag("EffectTypes");
		ArrayList<Class<?>> result=new ArrayList<Class<?>>();
		for(String type:types)
		{
			result.add(Class.forName(type));
		}
		return result;
	}
	
	public ArrayList<String> getEffectName(String path) throws ClassNotFoundException {
		XMLFileLoader xmlDocument=new XMLFileLoader(path);
		ArrayList<String> types=xmlDocument.getNodeNameInTag("EffectTypes");
		return types;
	}
}
