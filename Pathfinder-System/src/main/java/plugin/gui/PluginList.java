package plugin.gui;

import java.util.Collection;
import java.util.HashMap;

public class PluginList {

	private final HashMap<String, DocumentPlugin> plugins;

	public PluginList() {
		plugins = new HashMap<>();
	}

	public void addPlugin(DocumentPlugin plugin) {
		plugins.put(plugin.getDocumentType(), plugin);
	}

	public Collection<DocumentPlugin> getPlugins() {
		return plugins.values();
	}

	public DocumentPlugin getPlugin(String documentType) {
		return plugins.get(documentType);
	}

}
