package net.alteiar.server.document;

import java.io.File;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DocumentIO {

	public static BasicBean loadBeanLocal(File f) throws Exception {
		String path = f.getPath();

		String[] chaines = path.split("\\\\");
		String classe = chaines[chaines.length - 2];

		Class<? extends BasicBean> c = (Class<? extends BasicBean>) Class
				.forName(classe);
		Serializer serializer = new Persister();
		BasicBean bean = serializer.read(c, f);
		return bean;
	}

	public static File saveDocument(BasicBean objet, String path, String name)
			throws Exception {
		File dir = new File(path, objet.getClass().getCanonicalName());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File f = new File(dir, name);
		// create file only if exist
		f.createNewFile();

		Serializer serializer = new Persister();
		serializer.write(objet, f);
		return f;
	}

	public static String INVALID_FILE_CHARACTER_REPLACEMENT = "---";

	public static String validateFilename(String name) {
		String validFileName = name;

		validFileName = validFileName.replaceAll(":",
				INVALID_FILE_CHARACTER_REPLACEMENT);
		validFileName = validFileName.replaceAll("<",
				INVALID_FILE_CHARACTER_REPLACEMENT);
		validFileName = validFileName.replaceAll(">",
				INVALID_FILE_CHARACTER_REPLACEMENT);

		return validFileName + ".xml";
	}
}
