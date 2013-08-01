package net.alteiar.server.document;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

import net.alteiar.client.bean.BasicBean;

import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DocumentIO {

	public static Boolean isFileValid(File f) {
		Boolean found = false;
		String path = f.getPath();

		String[] chaines = path.split(Pattern.quote(File.separator));

		String classe = chaines[chaines.length - 2];
		try {
			@SuppressWarnings("unchecked")
			Class<? extends BasicBean> c = (Class<? extends BasicBean>) Class
					.forName(classe);
			found = c != null;
		} catch (ClassNotFoundException ex) {
			// do not care as default we say the file is not valid<
			Logger.getLogger(DocumentIO.class).warn(
					"The file " + path
							+ " is not a valid bean | the class should be "
							+ classe, ex);
		}
		return found;
	}

	public static BasicBean loadBeanLocal(File f) throws Exception {
		String path = f.getPath();

		String[] chaines = path.split(Pattern.quote(File.separator));

		String classe = chaines[chaines.length - 2];

		BasicBean bean = null;
		try {
			@SuppressWarnings("unchecked")
			Class<? extends BasicBean> c = (Class<? extends BasicBean>) Class
					.forName(classe);
			Serializer serializer = new Persister();
			bean = serializer.read(c, f);
		} catch (ClassNotFoundException ex) {
			System.out.println("cannot load " + classe);
			System.out.println(Arrays.toString(chaines));
		}
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
