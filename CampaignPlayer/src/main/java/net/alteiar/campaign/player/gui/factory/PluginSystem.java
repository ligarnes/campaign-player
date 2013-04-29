package net.alteiar.campaign.player.gui.factory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementEditor;
import net.alteiar.documents.AuthorizationBean;
import net.alteiar.map.elements.MapElement;

public class PluginSystem implements IPluginSystemGui {

	private final ArrayList<IPluginSystemGui> plugins;

	private final static PluginSystem INSTANCE = new PluginSystem();

	public static PluginSystem getInstance() {
		return INSTANCE;
	}

	private PluginSystem() {
		plugins = new ArrayList<IPluginSystemGui>();

		plugins.add(getPathfinderPluginSystemGui());
		// plugins.add(getEffectPluginSystemGui());
	}

	@Override
	public DrawInfo getDrawInfo(MapEditableInfo mapInfo) {
		return plugins.get(0).getDrawInfo(mapInfo);
	}

	@Override
	public <E extends AuthorizationBean> PanelViewDocument<E> getViewPanel(
			E bean) {
		return plugins.get(0).getViewPanel(bean);
	}

	@Override
	public <E extends AuthorizationBean> BufferedImage getDocumentIcon(E bean) {
		return plugins.get(0).getDocumentIcon(bean);
	}

	@Override
	public ArrayList<PanelDocumentBuilder> getGuiDocumentFactory() {
		return plugins.get(0).getGuiDocumentFactory();
	}

	@Override
	public PanelCharacterFactory getGuiCharacterFactory() {
		return plugins.get(0).getGuiCharacterFactory();
	}

	@Override
	public ArrayList<PanelMapElementBuilder> getGuiMapElementFactory() {
		ArrayList<PanelMapElementBuilder> mapElementBuilders = new ArrayList<PanelMapElementBuilder>();

		for (IPluginSystemGui plugin : plugins) {
			mapElementBuilders.addAll(plugin.getGuiMapElementFactory());
		}

		return mapElementBuilders;
	}

	@Override
	public <E extends MapElement> PanelMapElementEditor<E> getMapElementEditor(
			E bean) {
		return plugins.get(0).getMapElementEditor(bean);
	}

	private static ClassLoader getClassLoader() {
		try {
			ClassLoaderUtil
					.addFile("./ressources/plugin/Pathfinder-system-1.0-SNAPSHOT.jar");
			ClassLoaderUtil
					.addFile("./ressources/plugin/Effect-System-0.0.1-SNAPSHOT.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ClassLoader.getSystemClassLoader();// loader;
	}

	public static IPluginSystemGui getPathfinderPluginSystemGui() {
		IPluginSystemGui pluginSystemGui = null;
		try {
			Class<?> clazz = Class.forName("plugin.gui.PluginSystemGui", true,
					getClassLoader());

			Class<? extends IPluginSystemGui> runClass = clazz
					.asSubclass(IPluginSystemGui.class);
			// Avoid Class.newInstance, for it is evil.
			Constructor<? extends IPluginSystemGui> ctor = runClass
					.getConstructor();
			pluginSystemGui = ctor.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pluginSystemGui;
	}

	public static IPluginSystemGui getEffectPluginSystemGui() {
		IPluginSystemGui pluginSystemGui = null;
		try {
			Class<?> clazz = Class.forName("plugin.effect.gui.PluginSystemGui",
					true, getClassLoader());

			Class<? extends IPluginSystemGui> runClass = clazz
					.asSubclass(IPluginSystemGui.class);
			// Avoid Class.newInstance, for it is evil.
			Constructor<? extends IPluginSystemGui> ctor = runClass
					.getConstructor();
			pluginSystemGui = ctor.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pluginSystemGui;
	}
}
