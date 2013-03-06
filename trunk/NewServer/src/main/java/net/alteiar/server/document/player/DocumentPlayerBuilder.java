package net.alteiar.server.document.player;

import java.awt.Color;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.shared.Randomizer;

public class DocumentPlayerBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final String name;
	private final Boolean isMj;
	private final Color color;

	public DocumentPlayerBuilder(String name, Boolean isMj) {
		this.name = name;
		this.isMj = isMj;
		this.color = new Color(Randomizer.random(0, 255), Randomizer.random(0,
				255), Randomizer.random(0, 255));
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new PlayerRemote(name, isMj, color);
	}
}
