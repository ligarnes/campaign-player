package net.alteiar.campaign.player.gui.factory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang.StringUtils;

public class ClassLoaderUtil {

	// Parameters
	private static final Class<?>[] parameters = new Class[] { URL.class };

	/**
	 * Add file to CLASSPATH
	 * 
	 * @param path
	 *            File name
	 * @throws IOException
	 *             IOException
	 */
	public static void addFile(String path) throws IOException {
		addFile(new File(path));
	}

	/**
	 * Add file to CLASSPATH
	 * 
	 * @param f
	 *            File object
	 * @throws IOException
	 *             IOException
	 */
	public static void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}

	/**
	 * Add URL to CLASSPATH
	 * 
	 * @param u
	 *            URL
	 * @throws IOException
	 *             IOException
	 */
	public static void addURL(URL u) throws IOException {

		URLClassLoader sysLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		URL urls[] = sysLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {
			if (StringUtils.equalsIgnoreCase(urls[i].toString(), u.toString())) {
				return;
			}
		}
		Class<?> sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}
	}

}
