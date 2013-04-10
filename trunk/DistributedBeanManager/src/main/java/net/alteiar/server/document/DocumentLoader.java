package net.alteiar.server.document;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import net.alteiar.client.bean.BasicBeans;
import net.alteiar.client.bean.BeanEncapsulator;

public class DocumentLoader {
	
	
	static public BeanEncapsulator loadDocumentLocal(File f) throws Exception {
		BasicBeans bBean= loadBeanLocal(f);
		BeanEncapsulator bean=new BeanEncapsulator(bBean);
		return bean;
	}
	
	static public BasicBeans loadBeanLocal(File f) throws Exception {
		String path=f.getPath();
		String[] chaines=path.split("\\.?\\");
		String classe=chaines[chaines.length-1].substring(1, chaines[chaines.length-1].length()-2);
		Class<? extends BasicBeans> c=(Class<? extends BasicBeans>) Class.forName(classe); 
		Serializer serializer = new Persister();
		BasicBeans bean= serializer.read(c,f);
		return bean;
	}
	
	static public String SaveDocument(BasicBeans objet,String path,String name) throws Exception {
			String tempPath=path+"/"+objet.getClass().getSimpleName();
			File dir=new File(tempPath);
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			File f=new File(tempPath+"/"+name);
			Serializer serializer = new Persister();
			serializer.write(objet, f);
			return tempPath;
	}
}
