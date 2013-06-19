package net.alteiar.campaign.player.gui.factory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.centerViews.map.element.PanelMapElementEditor;
import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.newPlugin.ICorePlugin;
import net.alteiar.campaign.player.gui.factory.newPlugin.IPlugin;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.MapBean;
import net.alteiar.map.elements.MapElement;

public class PluginSystem implements IPluginSystemGui {

	private final static PluginSystem INSTANCE = new PluginSystem();

	private final ICorePlugin core;

	private final ArrayList<IPlugin> plugins;

	public static PluginSystem getInstance() {
		return INSTANCE;
	}

	private PluginSystem() {
		plugins = new ArrayList<IPlugin>();
		plugins.add(getPathfinderGeneralPlugin());

		core = getPathfinderCorePlugin();
	}

	@Override
	public DrawFilter getDrawInfo(MapEditableInfo mapInfo) {
		return core.getDrawInfo(mapInfo);
	}

	@Override
	public PanelViewDocument getViewPanel(BeanDocument bean) {
		PanelViewDocument found = null;

		Iterator<IPlugin> itt = plugins.iterator();
		while (itt.hasNext() && found == null) {
			found = itt.next().getViewPanel(bean);
		}

		return found;
	}

	@Override
	public BufferedImage getDocumentIcon(BeanDocument bean) {

		BufferedImage found = null;
		Iterator<IPlugin> itt = plugins.iterator();
		while (itt.hasNext() && found == null) {
			found = itt.next().getDocumentIcon(bean);
		}

		return found;
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		ArrayList<PanelDocumentBuilder> builders = new ArrayList<PanelDocumentBuilder>();
		for (IPlugin plugin : plugins) {
			builders.addAll(plugin.getGuiDocumentFactory());
		}

		return builders;
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory(MapBean map) {
		ArrayList<PanelMapElementBuilder> mapElementBuilders = new ArrayList<PanelMapElementBuilder>();

		for (IPlugin plugin : plugins) {
			ArrayList<PanelMapElementBuilder> mapElementBuilder = plugin
					.getGuiMapElementFactory();
			for (PanelMapElementBuilder builder : mapElementBuilder) {
				builder.refresh(map);
			}
			mapElementBuilders.addAll(mapElementBuilder);
		}

		return mapElementBuilders;
	}

	public Integer getGuiMapElementFactoryCount() {
		Integer total = 0;
		for (IPlugin plugin : plugins) {
			total += plugin.getGuiMapElementFactory().size();
		}

		return total;
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		PanelMapElementEditor<E> found = null;
		Iterator<IPlugin> itt = plugins.iterator();
		while (itt.hasNext() && found == null) {
			found = itt.next().getMapElementEditor(bean);
		}

		return found;
	}

	private static ClassLoader getClassLoader() {
		try {
			ClassLoaderUtil
					.addFile("./ressources/plugin/Pathfinder-system-1.0-SNAPSHOT.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ClassLoader.getSystemClassLoader();// loader;
	}

	private static ICorePlugin getPathfinderCorePlugin() {
		ICorePlugin pluginSystemGui = null;
		try {
			Class<?> clazz = Class.forName("plugin.gui.PathfinderCorePlugin",
					true, getClassLoader());

			Class<? extends ICorePlugin> runClass = clazz
					.asSubclass(ICorePlugin.class);
			// Avoid Class.newInstance, for it is evil.
			Constructor<? extends ICorePlugin> ctor = runClass.getConstructor();
			pluginSystemGui = ctor.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pluginSystemGui;
	}

	private static IPlugin getPathfinderGeneralPlugin() {
		IPlugin pluginSystemGui = null;
		try {
			Class<?> clazz = Class.forName(
					"plugin.gui.PathfinderGeneralPlugin", true,
					getClassLoader());

			Class<? extends IPlugin> runClass = clazz.asSubclass(IPlugin.class);
			// Avoid Class.newInstance, for it is evil.
			Constructor<? extends IPlugin> ctor = runClass.getConstructor();
			pluginSystemGui = ctor.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pluginSystemGui;
	}

	@Override
	public JPanel buildSmallCharacterSheet() {
		return core.buildSmallCharacterSheet();
	}

}
