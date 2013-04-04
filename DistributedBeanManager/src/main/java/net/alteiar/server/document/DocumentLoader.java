package net.alteiar.server.document;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;

public class DocumentLoader {
	
	
	static public BeanEncapsulator loadDocumentLocal(File f) throws Exception {
		String path=f.getPath();
		String[] chaines=path.split("\\.?\\");
		String classe=chaines[chaines.length-1].substring(1, chaines[chaines.length-1].length()-2);
		Class<? extends BasicBeans> c=(Class<? extends BasicBeans>) Class.forName(classe); 
		Serializer serializer = new Persister();
		BasicBeans bBean= serializer.read(c,f);
		BeanEncapsulator bean=new BeanEncapsulator(bBean);
		return bean;
	}
	
	static public void SaveDocument(BasicBeans objet,String path,String name,String extension) throws Exception {
			File f=new File(path+"/"+objet.getClass().getSimpleName()+"/"+name+extension);
			Serializer serializer = new Persister();
			serializer.write(objet, f);
	}
}
