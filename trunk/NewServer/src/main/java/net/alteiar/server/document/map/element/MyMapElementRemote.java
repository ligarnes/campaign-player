package net.alteiar.server.document.map.element;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;

public class MyMapElementRemote extends MapElementRemote implements
		IMyMapElementRemote {
	private static final long serialVersionUID = 1L;

	private final MapElement object;

	public MyMapElementRemote(Long map, Point position, MapElement object)
			throws RemoteException {
		super(map, position);
		this.object = object;
	}

	@Override
	public DocumentClient<?> buildProxy() throws RemoteException {
		return new MyMapElement(this);
	}

	@Override
	public MapElement getObject() throws RemoteException {
		return object;
	}

}
