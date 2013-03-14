package net.alteiar.server.document.map.element;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentBuilder;
import net.alteiar.server.document.map.MapClient;

public class DocumentMapElementBuilder extends DocumentBuilder {
	private static final long serialVersionUID = 1L;

	private final Long map;
	private final Point position;

	private final MapElement object;

	public DocumentMapElementBuilder(MapClient<?> map, Point position,
			MapElement object) {
		this(map.getId(), position, object);
	}

	public DocumentMapElementBuilder(Long map, Point position, MapElement object) {
		this.position = position;
		this.map = map;

		this.object = object;
	}

	protected Point getPosition() {
		return position;
	}

	protected Long getMap() {
		return map;
	}

	@Override
	public MapElementRemote buildMainDocument() throws RemoteException {
		return new MapElementRemote(getMap(), getPosition(), object);
	}
}
