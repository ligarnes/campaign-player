package net.alteiar.campaign.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * @author Cody Stoutenburg
 * 
 */

public class PropertieBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Properties properties;
	private File currentPath;
	private final String propertiesFile;

	public PropertieBase(String fileName) {
		propertiesFile = fileName;
		properties = new Properties();
	}

	public void load() throws IOException {
		File property = new File(propertiesFile);
		if (property.exists()) {
			currentPath = property.getParentFile();
			FileInputStream input = null;

			IOException ex = null;
			try {
				input = new FileInputStream(propertiesFile);
				properties.load(input);

			} catch (IOException e) {
				ex = e;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						ex = e;
					}
				}
			}
			if (ex != null) {
				throw ex;
			}
		}
	}

	public void save() throws IOException {
		File property = new File(propertiesFile);
		if (!property.exists()) {
			if (!property.createNewFile()) {
				throw new IOException("Impossible de créer le fichier "
						+ property.getCanonicalPath());
			}
		}

		IOException ex = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(property);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh'h'mm");
			String generated = "Property saved at "
					+ dateFormat.format(new Date());

			this.properties.store(out, generated);
		} catch (IOException e) {
			ex = e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					ex = e;
				}
			}
		}
		if (ex != null) {
			throw ex;
		}
	}

	public void setValue(String key, String value) {
		this.properties.setProperty(key, value);
	}

	public String getFilePath() {
		return this.propertiesFile;
	}

	public Integer getIntegerValue(String key, Integer defaultVal) {
		return Integer.valueOf(properties.getProperty(key,
				defaultVal.toString()));
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

	public String getValue(String key, String defaultVal) {
		return properties.getProperty(key, defaultVal);
	}

	public String getLocalPath(String localPath) throws IOException {
		File f = new File(currentPath, localPath);
		localPath = f.getCanonicalPath();
		return localPath;
	}

	public String getPath(String key) throws IOException {
		String path = properties.getProperty(key);
		File f = new File(currentPath, path);
		path = f.getCanonicalPath();
		return path;
	}

	public Boolean getBooleanValue(String key) {
		return Boolean.valueOf(properties.getProperty(key, "false"));
	}

	public Integer getIntegerValue(String key) {
		return Integer.valueOf(properties.getProperty(key, "0"));
	}

	public Float getFloatValue(String key) {
		return Float.valueOf(properties.getProperty(key, "0.0"));
	}

	public Properties getName() {
		return properties;
	}
}
