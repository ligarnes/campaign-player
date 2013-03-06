package net.alteiar.server.document.map.element.colored;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.map.element.MapElementRemote;

public abstract class MapElementColoredRemote extends MapElementRemote
		implements IMapElementColoredRemote {
	private static final long serialVersionUID = 1L;

	private final Color color;

	public MapElementColoredRemote(Long map, Point position, Color color)
			throws RemoteException {
		super(map, position);
		this.color = color;
	}

	@Override
	public Color getColor() throws RemoteException {
		return this.color;
	}
}
