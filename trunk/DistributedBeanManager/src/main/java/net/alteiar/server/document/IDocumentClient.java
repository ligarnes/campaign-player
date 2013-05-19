package net.alteiar.server.document;

import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public interface IDocumentClient {
	public UniqueID getId();

	public BeanEncapsulator getBeanEncapsulator();

	public void loadDocument() throws Exception;

	public void saveLocal() throws Exception;
}
