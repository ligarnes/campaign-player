package net.alteiar.server.document;

import java.rmi.RemoteException;

import net.alteiar.client.bean.BasicBean;
import net.alteiar.client.bean.BeanEncapsulator;
import net.alteiar.shared.UniqueID;

public interface IDocumentClient {
	UniqueID getId();

	BeanEncapsulator getBeanEncapsulator();

	String getFilename();

	void loadDocument(BasicBean bean) throws RemoteException;

	void save(String path) throws Exception;
}
