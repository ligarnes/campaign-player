package net.alteiar.server.document.player;

import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;

public class DocumentPlayerBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final String name;
	private final Boolean isMj;

	public DocumentPlayerBuilder(String name, Boolean isMj) {
		this.name = name;
		this.isMj = isMj;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new PlayerRemote(name, isMj);
	}
}
