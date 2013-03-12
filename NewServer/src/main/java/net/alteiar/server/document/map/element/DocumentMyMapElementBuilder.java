package net.alteiar.server.document.map.element;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.map.MapClient;

public class DocumentMyMapElementBuilder extends DocumentMapElementBuilder {
	private static final long serialVersionUID = 1L;

	private final MapElement object;

	public DocumentMyMapElementBuilder(MapClient<?> map, Point position,
			MapElement object) {
		this(map.getId(), position, object);
	}

	public DocumentMyMapElementBuilder(Long map, Point position,
			MapElement object) {
		super(map, position);

		this.object = object;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new MyMapElementRemote(getMap(), getPosition(), object);
	}
}
