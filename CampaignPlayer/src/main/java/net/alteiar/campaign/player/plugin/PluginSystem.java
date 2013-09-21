package net.alteiar.campaign.player.plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import net.alteiar.beans.map.elements.MapElement;
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
import net.alteiar.campaign.player.tools.Threads;
import net.alteiar.documents.BeanDocument;
import net.alteiar.kryo.MyKryoInit;
import net.alteiar.thread.MyRunnable;

import org.apache.log4j.Logger;

public class PluginSystem {

	private static PluginSystem INSTANCE;// = new PluginSystem();

	public static void buildPluginSystem(PluginInfo path) throws IOException {
		INSTANCE = new PluginSystem(path);
		INSTANCE.initialize();
	}

	public static PluginSystem getInstance() {
		return INSTANCE;
	}

	private ICorePlugin core;
	private IPlugin plugin;
	private final PluginList<DocumentPlugin> pluginDocument;
	private final PluginList<MapElementPlugin> pluginElements;

	private final MyKryoInit kryo;

	private final String pluginPath;

	public String getPluginBeans() {
		return pluginPath;
	}

	public PluginSystem(PluginInfo pluginInfo) throws IOException {
		pluginPath = pluginInfo.getJar().getParentFile().getCanonicalPath()
				+ "/global";

		kryo = new MyKryoInit();

		pluginDocument = new PluginList<DocumentPlugin>();
		pluginElements = new PluginList<MapElementPlugin>();

		try {
			for (File dep : pluginInfo.getDependencies()) {
				ClassLoaderUtil.addFile(dep);
			}

			ClassLoaderUtil.addFile(pluginInfo.getJar());

			core = (ICorePlugin) ClassLoader.getSystemClassLoader()
					.loadClass(pluginInfo.getCorePlugin()).newInstance();

			plugin = (IPlugin) ClassLoader.getSystemClassLoader()
					.loadClass(pluginInfo.getDocumentPlugin()).newInstance();

			for (DocumentPlugin plug : plugin.getDocuments()) {
				this.pluginDocument.addPlugin(plug);
			}

			for (MapElementPlugin plug : plugin.getMapElements()) {
				pluginElements.addPlugin(plug);
			}

			MyKryoInit kryo = new MyKryoInit();
			kryo.addPluginClasses(plugin.getClasses());

		} catch (Exception e) {
			Logger.getLogger(getClass()).error("enable to load plugin ", e);
			ExceptionTool.showError(e);
		}
	}

	public void initialize() {
		Threads.execute(new MyRunnable() {
			@Override
			public void run() {
				try {
					plugin.initialize();
				} catch (Exception ex) {
					Logger.getLogger(getClass()).error(
							"Error while initializing pluggin", ex);
				}
			}

			@Override
			public String getTaskName() {
				return "initialize plugin";
			}
		});
	}

	public MyKryoInit getKryo() {
		return kryo;
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
