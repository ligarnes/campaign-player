package net.alteiar.campaign.player.plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import net.alteiar.campaign.player.PropertieBase;
import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.logger.ExceptionTool;
import net.alteiar.campaign.player.plugin.external.DocumentPlugin;
import net.alteiar.campaign.player.plugin.external.IPlugin;
import net.alteiar.campaign.player.plugin.external.MapElementPlugin;
import net.alteiar.campaign.player.plugin.external.PluginList;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.elements.MapElement;

import org.apache.log4j.Logger;

public class PluginSystem {

	private static PluginSystem INSTANCE = new PluginSystem();

	public static PluginSystem getInstance() {
		return INSTANCE;
	}

	private static final String PROP_PLUGIN_NAME = "name";
	private static final String PROP_JAR_NAME = "jar";
	private static final String PROP_CLASS_DOCUMENT_PLUGIN_NAME = "document.classname";
	private static final String PROP_CLASS_CORE_PLUGIN_NAME = "core.classname";

	private ICorePlugin core;
	private final PluginList<DocumentPlugin> pluginDocument;
	private final PluginList<MapElementPlugin> pluginElements;

	public PluginSystem() {

		pluginDocument = new PluginList<DocumentPlugin>();
		pluginElements = new PluginList<MapElementPlugin>();

		try {
			loadPlugin("./ressources/plugin/pathfinder/");
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("enable to load plugin ", e);
			ExceptionTool.showError(e);
		}
	}

	public void loadPlugin(String directory) throws FileNotFoundException,
			IOException {

		File dir = new File(directory);

		PropertieBase propertie = new PropertieBase(directory + "plugin.prop");
		propertie.load();

		String jarFile = propertie.getValue(PROP_JAR_NAME);
		File jar = new File(dir, jarFile.trim());

		if (!jar.exists()) {
			throw new FileNotFoundException("Le fichier de plugin: "
					+ jar.getCanonicalPath() + " n'as pas été trouvé");
		}
		ClassLoaderUtil.addFile(jar);

		String pluginName = propertie.getValue(PROP_PLUGIN_NAME);

		try {
			// load document plugin
			loadDocumentPlugin(propertie);

			// load map elements

			// load system specific
			loadCorePlugin(propertie);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(
					"enable to load plugin " + pluginName, e);
			ExceptionTool.showError(e);
		}
	}

	private void loadCorePlugin(PropertieBase propertie)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		String className = propertie.getValue(PROP_CLASS_CORE_PLUGIN_NAME);

		core = (ICorePlugin) ClassLoader.getSystemClassLoader()
				.loadClass(className).newInstance();
	}

	private void loadDocumentPlugin(PropertieBase propertie)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		String className = propertie.getValue(PROP_CLASS_DOCUMENT_PLUGIN_NAME);

		IPlugin plugin;
		plugin = (IPlugin) ClassLoader.getSystemClassLoader()
				.loadClass(className).newInstance();

		for (DocumentPlugin plug : plugin.getDocuments()) {
			pluginDocument.addPlugin(plug);
		}

		for (MapElementPlugin plug : plugin.getMapElements()) {
			pluginElements.addPlugin(plug);
		}
	}

	public DrawFilter getDrawInfo(MapEditableInfo mapInfo) {
		return core.getDrawInfo(mapInfo);
	}

	public JPanel buildSmallCharacterSheet() {
		return core.buildSmallCharacterSheet();
	}

	public PanelViewDocument getViewPanel(BeanDocument bean) {
		PanelViewDocument found = getPlugin(bean).getViewer();

		if (found != null) {
			found.setDocument(bean);
		}

		return found;
	}

	public BufferedImage getDocumentIcon(BeanDocument bean) {
		BufferedImage found = null;

		DocumentPlugin plugin = getPlugin(bean);
		if (plugin != null) {
			found = plugin.getIcon().getImage(bean);
		}

		return found;
	}

	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> builders = new ArrayList<PanelDocumentBuilder>();

		Iterator<DocumentPlugin> itt = pluginDocument.iteratorDocumentPlugin();
		while (itt.hasNext()) {
			builders.add(itt.next().getBuilder());
		}

		return builders;
	}

	protected DocumentPlugin getPlugin(BeanDocument bean) {
		return pluginDocument.getDocumentPlugin(bean.getDocumentType());
	}

	protected <E extends MapElement> MapElementPlugin getPlugin(E bean) {
		return pluginElements.getDocumentPlugin(bean.getClass()
				.getCanonicalName());
	}

	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		ArrayList<PanelMapElementBuilder> builders = new ArrayList<PanelMapElementBuilder>();

		Iterator<MapElementPlugin> itt = pluginElements
				.iteratorDocumentPlugin();
		while (itt.hasNext()) {
			builders.add(itt.next().getBuilder());
		}

		return builders;
	}

	public <E extends MapElement> PanelMapElementEditor getMapElementEditor(
			E bean) {
		PanelMapElementEditor editor = null;
		MapElementPlugin plugin = getPlugin(bean);
		if (plugin != null) {
			editor = plugin.getEditor();
			if (editor != null) {
				editor.setMapElement(bean);
			}
		}

		return editor;
	}
}
