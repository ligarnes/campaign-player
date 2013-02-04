package net.alteiar.server.shared.campaign.battle.map.element;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

public abstract class MapElementColoredRemote extends MapElementRemote {
	private static final long serialVersionUID = 1L;

	private final Color color;

	public MapElementColoredRemote(Point position, Color color)
			throws RemoteException {
		super(position);
		this.color = color;
	}

	public Color getColor() throws RemoteException {
		return this.color;
	}
}
