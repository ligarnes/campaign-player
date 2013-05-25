package net.alteiar.server.document;

import java.rmi.RemoteException;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentLocal implements IDocumentClient {

	private final BeanEncapsulator bean;
	private final String filename;

	public DocumentLocal(BasicBean bean) {
		this.bean = new BeanEncapsulator(bean);
		filename = DocumentIO.validateFilename(this.bean.getId().toString());
	}

	@Override
	public UniqueID getId() {
		return bean.getId();
	}

	@Override
	public BeanEncapsulator getBeanEncapsulator() {
		return bean;
	}

	@Override
	public void loadDocument(BasicBean bean) throws RemoteException {
		// do nothing, the bean should be given at construction
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void save(String path) throws Exception {
		DocumentIO.saveDocument(bean.getBean(), path, getFilename());
	}
}
