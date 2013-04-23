package net.alteiar.campaign.player.gui.factory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import net.alteiar.campaign.player.gui.documents.PanelDocumentBuilder;
import net.alteiar.campaign.player.gui.documents.PanelViewDocument;
import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import net.alteiar.campaign.player.gui.map.element.PanelMapElementBuilder;
import net.alteiar.documents.AuthorizationBean;

public class PluginSystem implements IPluginSystemGui {

	private final ArrayList<IPluginSystemGui> plugins;

	private final static PluginSystem INSTANCE = new PluginSystem();

	public static PluginSystem getInstance() {
		return INSTANCE;
	}

	private PluginSystem() {
		plugins = new ArrayList<IPluginSystemGui>();

		plugins.add(getPluginSystemGui());
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
	public <E extends AuthorizationBean> ImageIcon getDocumentIcon(E bean) {
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

	public static IPluginSystemGui getPluginSystemGui() {
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
}
