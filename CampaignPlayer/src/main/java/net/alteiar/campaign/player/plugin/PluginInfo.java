package net.alteiar.campaign.player.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.alteiar.campaign.player.tools.PropertieBase;

public class PluginInfo {
	private static final String PROP_PLUGIN_NAME = "name";
	private static final String PROP_JAR_NAME = "jar";
	private static final String PROP_DEPENDENCIES = "dependencies";
	private static final String PROP_CLASS_DOCUMENT_PLUGIN_NAME = "document.classname";
	private static final String PROP_CLASS_CORE_PLUGIN_NAME = "core.classname";

	private static final String DEPENDENCIES_SEPERATOR = ";";

	private final String name;
	private final File jar;

	private final String corePlugin;
	private final String documentPlugin;

	private final ArrayList<File> dependencies;

	public PluginInfo(String directory) throws IOException {
		this(new File(directory));
	}

	public PluginInfo(File dir) throws IOException {
		PropertieBase propertie = new PropertieBase(dir.getCanonicalPath()
				+ "/plugin.prop");
		propertie.load();

		// LOAD GENERAL INFO ABOUT PLUGIN
		name = propertie.getValue(PROP_PLUGIN_NAME);
		corePlugin = propertie.getValue(PROP_CLASS_CORE_PLUGIN_NAME);
		documentPlugin = propertie.getValue(PROP_CLASS_DOCUMENT_PLUGIN_NAME);

		// LOAD MAIN JAR
		String jarFile = propertie.getValue(PROP_JAR_NAME);
		jar = new File(dir, jarFile.trim());

		if (!jar.exists()) {
			throw new FileNotFoundException(
					"Impossible de trouver le fichier jar"
							+ jar.getCanonicalPath() + ", du plugin " + name);
		}

		// LOAD JAR DEPENDENCIES
		String depsString = propertie.getValue(PROP_DEPENDENCIES);

		String[] deps = depsString.split(DEPENDENCIES_SEPERATOR);
		dependencies = new ArrayList<>();
		for (String dep : deps) {
			File fileDep = new File(dir, dep.trim());
			dependencies.add(fileDep);
			if (!fileDep.exists()) {
				throw new FileNotFoundException(
						"Impossible de trouver le fichier jar"
								+ jar.getCanonicalPath() + ", du plugin "
								+ name);
			}
		}

	}

	public String getName() {
		return name;
	}

	public File getJar() {
		return jar;
	}

	public String getCorePlugin() {
		return corePlugin;
	}

	public String getDocumentPlugin() {
		return documentPlugin;
	}

	public ArrayList<File> getDependencies() {
		return dependencies;
	}
}
