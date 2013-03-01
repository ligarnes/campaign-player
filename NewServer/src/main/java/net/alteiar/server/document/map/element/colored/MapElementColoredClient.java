package net.alteiar.server.document.map.element.colored;

import java.awt.Color;
import java.rmi.RemoteException;

import net.alteiar.server.document.map.element.MapElementClient;

public abstract class MapElementColoredClient<E extends MapElementColoredRemote>
		extends MapElementClient<E> {
	private static final long serialVersionUID = 1L;

	// the position is the position of the upper left corner
	private final Color color;

	public MapElementColoredClient(E remote) throws RemoteException {
		super(remote);

		color = remote.getColor();
	}

	public Color getColor() {
		return color;
	}
}
