package net.alteiar.server.document.map.element.colored.circle;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.IDocumentRemote;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.element.colored.DocumentMapElementColoredBuilder;
import net.alteiar.server.document.map.element.size.MapElementSize;

public class DocumentCircleBuilder extends DocumentMapElementColoredBuilder {
	private static final long serialVersionUID = 1L;

	protected final MapElementSize radius;

	public DocumentCircleBuilder(MapClient<?> map, Point position, Color color,
			MapElementSize radius) {
		this(map.getId(), position, color, radius);
	}

	public DocumentCircleBuilder(Long map, Point position, Color color,
			MapElementSize radius) {
		super(map, position, color);
		this.radius = radius;
	}

	@Override
	public IDocumentRemote buildMainDocument() throws RemoteException {
		return new MapElementCircleRemote(getMap(), getPosition(), color,
				radius);
	}
}
