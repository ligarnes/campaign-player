package net.alteiar.server.document;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class DocumentBuilder implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<IDocumentRemote> buildDocuments() throws RemoteException {
		return new ArrayList<IDocumentRemote>();
	}

	public abstract IDocumentRemote buildMainDocument() throws RemoteException;
}