package net.alteiar.campaign.player.gui.factory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import net.alteiar.campaign.player.gui.map.element.PanelCreateMapElement;

public class PluginSystem {

	private static ClassLoader loader;

	private static ClassLoader getClassLoader() {
		if (loader == null) {
			try {
				URL pluginJar = new File(
						"./ressources/plugin/Pathfinder-system-1.0-SNAPSHOT.jar")
						.toURI().toURL();
				loader = URLClassLoader.newInstance(new URL[] { pluginJar },
						PanelCreateMapElement.class.getClassLoader());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return loader;
	}

	public static IPluginSystemGui getPluginSystemGui() {
		IPluginSystemGui pluginSystemGui = null;
		try {
			Class<?> clazz = Class.forName(
					"plugin.gui.PathfinderPluginSystemGui", true,
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
