package net.alteiar.server.document;

import java.rmi.RemoteException;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentLocal implements IDocumentClient {

	private final BeanEncapsulator bean;

	public DocumentLocal(BasicBean bean) {
		this.bean = new BeanEncapsulator(bean);
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
		return null;
	}

	@Override
	public void save(String path) throws Exception {
		// do nothing, the bean should be given at construction
	}
}
