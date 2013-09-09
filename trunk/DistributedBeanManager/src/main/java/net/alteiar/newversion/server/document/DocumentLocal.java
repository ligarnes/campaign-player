package net.alteiar.newversion.server.document;

import java.rmi.RemoteException;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentLocal {

	private final BeanEncapsulator bean;
	private final String filename;

	public DocumentLocal(BasicBean bean) {
		this.bean = new BeanEncapsulator(bean);
		filename = DocumentIO.validateFilename(this.bean.getId().toString());
	}

	public UniqueID getId() {
		return bean.getId();
	}

	public BeanEncapsulator getBeanEncapsulator() {
		return bean;
	}

	public void loadDocument(BasicBean bean) throws RemoteException {
		// do nothing, the bean should be given at construction
	}

	public String getFilename() {
		return filename;
	}

	public void save(String path) throws Exception {
		DocumentIO.saveDocument(bean.getBean(), path, getFilename());
	}
}
