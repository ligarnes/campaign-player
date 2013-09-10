package net.alteiar.newversion.server.document;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public class DocumentLocal implements IDocument {

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
	public void loadDocument(BasicBean bean) {
		// do nothing, the bean should be given at construction
	}

	@Override
	public void remoteValueChanged(String propertyName, Object newValue,
			Long timestamp) {
		// do nothing
	}

	@Override
	public void remoteCloseDocument() {
		// do nothing
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
