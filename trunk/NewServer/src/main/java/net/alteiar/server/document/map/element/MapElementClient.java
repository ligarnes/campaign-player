package net.alteiar.server.document.map.element;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.Scale;

public abstract class MapElementClient<E extends IMapElementRemote> extends
		DocumentClient<E> {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	private transient IMapElementListenerRemote listener;

	private final Long map;
	// the position is the position of the upper left corner
	private Point position;
	private Double angle;
	private Boolean isHidden;

	public MapElementClient(E remote) throws RemoteException {
		super(remote);

		position = remote.getPosition();
		angle = remote.getAngle();
		isHidden = remote.getIsHidden();

		map = remote.getMap();
	}

	protected MapClient<?> getMap() {
		return (MapClient<?>) CampaignClient.getInstance().getDocument(map);
	}

	protected Scale getScale() {
		return getMap().getScale();
	}

	/**
	 * 
	 * @return the width in pixel of the element
	 */
	public abstract Double getWidth();

	/**
	 * 
	 * @return the height in pixel
	 */
	public abstract Double getHeight();

	/**
	 * 
	 * @return the x coordinate in pixel
	 */
	public Integer getX() {
		return position.x;
	}

	/**
	 * 
	 * @return the y coordinate in pixel
	 */
	public Integer getY() {
		return position.y;
	}

	/**
	 * 
	 * @return the center in pixel
	 */
	public Point getCenterPosition() {
		int x = (int) (getX() + (getWidth() / 2));
		int y = (int) (getY() + (getHeight() / 2));
		return new Point(x, y);
	}

	/**
	 * 
	 * @return the relative position (independent of map scale)
	 */
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

	public final void draw(Graphics2D g2) {
		draw(g2, 1.0);
	}

	public abstract void draw(Graphics2D g2, double zoomFactor);

	public abstract Boolean contain(Point p);

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
