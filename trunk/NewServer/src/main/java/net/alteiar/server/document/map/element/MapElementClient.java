package net.alteiar.server.document.map.element;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.map.Scale;

public abstract class MapElementClient<E extends IMapElementRemote> extends
		DocumentClient<E> {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	private transient IMapElementListenerRemote listener;
	// the position is the position of the upper left corner
	private Point position;
	private Double angle;
	private Boolean isHidden;

	public MapElementClient(E remote) throws RemoteException {
		super(remote);

		position = remote.getPosition();
		angle = remote.getAngle();
		isHidden = remote.getIsHidden();
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

	public void setPosition(Point position) {
		try {
			getRemote().setPosition(position);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAngle(Double angle) {
		try {
			getRemote().setAngle(angle);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIsHidden(Boolean isHidden) {
		if (!isHidden.equals(this.isHidden)) {
			try {
				getRemote().setIsHidden(isHidden);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void setLocalHidden(Boolean isHidden) {
		this.isHidden = isHidden;
		// TODO notify listeners
	}

	protected void setLocalPosition(Point newPosition) {
		this.position = newPosition;
		// TODO notify listeners
	}

	protected void setLocalAngle(Double angle) {
		this.angle = angle;
		// TODO notify listeners
	}

	public abstract void draw(Graphics2D g2, Scale scale, double zoomFactor);

	@Override
	protected void loadDocumentRemote() throws IOException {
		listener = new MapElementListenerRemote(getRemote());
	}

	@Override
	protected void closeDocument() throws RemoteException {
		getRemote().removeMapElementListener(listener);
	}

	/**
	 * this class should be observer
	 * 
	 * @author Cody Stoutenburg
	 * 
	 */
	protected class MapElementListenerRemote extends UnicastRemoteObject
			implements IMapElementListenerRemote {

		private static final long serialVersionUID = 2559145398149500009L;

		/**
		 * @throws RemoteException
		 */
		protected MapElementListenerRemote(IMapElementRemote mapElement)
				throws RemoteException {
			super();
			mapElement.addMapElementListener(this);
		}

		@Override
		public void elementMoved(Point newPosition) throws RemoteException {
			setLocalPosition(newPosition);
		}

		@Override
		public void elementHidden(Boolean isHidden) throws RemoteException {
			setLocalHidden(isHidden);
		}

		@Override
		public void elementRotate(Double angle) throws RemoteException {
			setLocalAngle(angle);
		}
	}
}
