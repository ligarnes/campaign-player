package net.alteiar.trigger.xml;

import java.util.ArrayList;

import net.alteiar.campaign.player.gui.map.element.utils.ComboListFromXML;
import net.alteiar.utils.XMLFileLoader;

public class TriggerChoiceListGenerator implements ComboListFromXML {

	public static String path = "./ressources/EffectRessource/xml/Trigger.xml";

	public ArrayList<ArrayList<String[]>> CreateComboList(String path) {

		ArrayList<ArrayList<String[]>> result = new ArrayList<ArrayList<String[]>>();
		XMLFileLoader xmlDocument = new XMLFileLoader(path);
		ArrayList<String> triggerTypes = xmlDocument
				.getNodeNameInTag("TriggerTypes");
		result.add(new ArrayList<String[]>());

		String[] temp = new String[triggerTypes.size()];
		temp = triggerTypes.toArray(temp);
		result.get(0).add(temp);

		result.add(new ArrayList<String[]>());
		for (int i = 0; i < result.get(0).get(0).length; i++) {
			ArrayList<String> activatorsTypes = xmlDocument
					.getNodesInTagAndName("TriggerAssociation", (result.get(0)
							.get(0)[i]));

			String[] temp2 = new String[activatorsTypes.size()];
			temp2 = activatorsTypes.toArray(temp2);
			result.get(1).add(temp2);
		}

		return result;
	}

	public Class<?> getTriggerClass(String path, String name)
			throws ClassNotFoundException {
		XMLFileLoader xmlDocument = new XMLFileLoader(path);
		ArrayList<String> type = xmlDocument.getNodeItemInTag("Trigger", name);
		return Class.forName(type.get(0));
	}

	public Class<?> getActivatorClass(String path, String name,
			String triggerName) throws ClassNotFoundException {
		XMLFileLoader xmlDocument = new XMLFileLoader(path);
		String type = xmlDocument.getNodeItemInTag("Trigger", "class",
				triggerName, name);
		return Class.forName(type);
	}

	public ArrayList<Class<?>> getTriggerClasses(String path)
			throws ClassNotFoundException {
		XMLFileLoader xmlDocument = new XMLFileLoader(path);
		ArrayList<String> types = xmlDocument.getNodeItemInTag("TriggerTypes");
		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (String type : types) {
			result.add(Class.forName(type));
		}
		return result;
	}

	public ArrayList<String> getTriggerName(String path)
			throws ClassNotFoundException {
		XMLFileLoader xmlDocument = new XMLFileLoader(path);
		ArrayList<String> types = xmlDocument.getNodeNameInTag("TriggerTypes");
		return types;
	}
}
