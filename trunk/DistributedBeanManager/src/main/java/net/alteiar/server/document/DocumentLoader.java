package net.alteiar.server.document;

import java.io.File;
import java.util.Arrays;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DocumentLoader {

	public static BeanEncapsulator loadDocumentLocal(File f) throws Exception {
		BasicBeans bBean = loadBeanLocal(f);
		BeanEncapsulator bean = new BeanEncapsulator(bBean);
		return bean;
	}

	public static BasicBeans loadBeanLocal(File f) throws Exception {
		String path = f.getPath();

		// String[] chaines = path.split("\\.?\\");
		String[] chaines = path.split("\\\\");
		String classe = chaines[chaines.length - 1].substring(1,
				chaines[chaines.length - 1].length() - 2);

		System.out.println(Arrays.toString(chaines));

		Class<? extends BasicBeans> c = (Class<? extends BasicBeans>) Class
				.forName(classe);
		Serializer serializer = new Persister();
		BasicBeans bean = serializer.read(c, f);
		return bean;
	}

	public static File SaveDocument(BasicBeans objet, String path, String name)
			throws Exception {
		File dir = new File(path, objet.getClass().getSimpleName());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File f = new File(dir, name);
		// create file only if exist
		f.createNewFile();

		System.out.println("create file: " + f.getCanonicalPath());

		Serializer serializer = new Persister();
		serializer.write(objet, f);
		return f;
	}
}
