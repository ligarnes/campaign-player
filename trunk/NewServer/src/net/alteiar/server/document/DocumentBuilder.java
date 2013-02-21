package net.alteiar.server.document;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class DocumentBuilder implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract IDocumentRemote buildDocument() throws RemoteException;
}