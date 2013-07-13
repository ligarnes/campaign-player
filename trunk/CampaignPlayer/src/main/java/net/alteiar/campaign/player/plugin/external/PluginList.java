package net.alteiar.campaign.player.plugin.external;

import java.util.HashMap;
import java.util.Iterator;


public class PluginList<V extends IPluginElement> {

	private final HashMap<String, V> plugins;

	public PluginList() {
		plugins = new HashMap<String, V>();
	}

	public void addPlugin(V plugin) {
		plugins.put(plugin.getType(), plugin);
	}

	public V getDocumentPlugin(String documentType) {
		return plugins.get(documentType);
	}

	public Iterator<V> iteratorDocumentPlugin() {
		return plugins.values().iterator();
	}
}
