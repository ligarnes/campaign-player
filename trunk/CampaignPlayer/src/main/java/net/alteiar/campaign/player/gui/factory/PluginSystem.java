package net.alteiar.campaign.player.gui.factory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.factory.newPlugin.ICorePlugin;
import net.alteiar.campaign.player.gui.factory.newPlugin.IPlugin;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.documents.character.Character;
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
	public DrawInfo getDrawInfo(MapEditableInfo mapInfo) {
		return core.getDrawInfo(mapInfo);
	}

	@Override
	public <E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(
			E bean) {
		PanelViewDocument<E> found = null;

		Iterator<IPlugin> itt = plugins.iterator();
		while (itt.hasNext() && found == null) {
			found = itt.next().getViewPanel(bean);
		}

		return found;
	}

	@Override
	public <E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean) {

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
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		ArrayList<PanelMapElementBuilder> mapElementBuilders = new ArrayList<PanelMapElementBuilder>();

		for (IPlugin plugin : plugins) {
			ArrayList<PanelMapElementBuilder> mapElementBuilder = plugin
					.getGuiMapElementFactory();
			for (PanelMapElementBuilder builder : mapElementBuilder) {
				builder.refresh();
			}
			mapElementBuilders.addAll(mapElementBuilder);
		}

		return mapElementBuilders;
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

	@Override
	public JPanel buildCompleteCharacterSheet(Character character) {
		return core.buildCompleteCharacterSheet(character);
	}
}
