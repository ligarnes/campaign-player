package net.alteiar.server.document.map.element;

import java.awt.Point;
import java.rmi.RemoteException;

import net.alteiar.server.document.DocumentClient;

public abstract class MapElementClient<E extends MapElementRemote> extends
		DocumentClient<E> {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	// the position is the position of the upper left corner
	private final Point position;
	private final Double angle;
	private Boolean isHidden;

	public MapElementClient(E remote) throws RemoteException {
		super(remote);

		position = remote.getPosition();
		angle = remote.getAngle();
	}

	public Point getPosition() {
		return position;
	}

	public Double getAngle() {
		return angle;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		try {
			getRemote().setIsHidden(isHidden);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setLocalHidden(Boolean isHidden) {
		this.isHidden = isHidden;
		// TODO notify listeners
	}
}
