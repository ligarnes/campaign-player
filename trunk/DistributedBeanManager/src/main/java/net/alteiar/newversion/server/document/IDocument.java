package net.alteiar.newversion.server.document;

import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.newversion.shared.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public interface IDocument {
	UniqueID getId();

	BeanEncapsulator getBeanEncapsulator();

	String getFilename();

	void loadDocument(BasicBean bean);

	void remoteValueChanged(String propertyName, Object newValue, Long timestamp);

	void remoteCloseDocument();

	void save(String path) throws Exception;
}
