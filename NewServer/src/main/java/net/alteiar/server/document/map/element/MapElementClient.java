package net.alteiar.server.document.map.element;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.DocumentClient;
import net.alteiar.server.document.map.MapClient;
import net.alteiar.server.document.map.Scale;

public class MapElementClient extends DocumentClient<IMapElementRemote> {
	private static final long serialVersionUID = 1L;

	protected static final Integer STROKE_SIZE_LARGE = 4;
	protected static final Integer STROKE_SIZE_SMALL = 2;

	// Remote listener
	private transient IMapElementListenerRemote listener;

	// Plugin object
	private final MapElement object;

	// id of the map link
	private final Long map;

	// the position is the position of the upper left corner
	private Point position;
	private Double angle;
	private Boolean isHidden;

	public MapElementClient(IMapElementRemote remote) throws RemoteException {
		super(remote);

		position = remote.getPosition();
		angle = remote.getAngle();
		isHidden = remote.getIsHidden();

		map = remote.getMap();

		this.object = remote.getObject();
	}

	protected MapClient<?> getMap() {
		return (MapClient<?>) CampaignClient.getInstance().getDocument(map);
	}

	public Scale getScale() {
		return getMap().getScale();
	}

	/**
	 * 
	 * @return the width in pixel of the element
	 */
	public Double getWidth() {
		return object.getWidth();
	}

	/**
	 * 
	 * @return the height in pixel
	 */
	public Double getHeight() {
		return object.getHeight();
	}

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

	public MapElement getElement() {
		return object;
	}

	public List<IAction> getActions() {
		return object.getActions();
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
		notifyMapElementChanged();
	}

	protected void setLocalPosition(Point newPosition) {
		this.position = newPosition;
		notifyMapElementChanged();
	}

	protected void setLocalAngle(Double angle) {
		this.angle = angle;
		notifyMapElementChanged();
	}

	public final void draw(Graphics2D g2) {
		draw(g2, 1.0);
	}

	public void draw(Graphics2D g2, double zoomFactor) {
		object.draw(g2, zoomFactor);
	}

	public Boolean contain(Point p) {
		return object.contain(p);
	}

	@Override
	protected void loadDocumentRemote() throws IOException {
		listener = new MapElementListenerRemote(getRemote());

		// initialize the plugin object
		this.object.loadMapElement(this);
	}

	@Override
	protected void loadDocumentLocal(File f) throws IOException {
	}

	@Override
	protected void closeDocument() throws RemoteException {
		getRemote().removeMapElementListener(listener);
	}

	// /////////LISTENERS///////////
	/**
	 * 
	 * @param listener
	 */
	public void addMapElementListener(IMapElementListener listener) {
		this.addListener(IMapElementListener.class, listener);
	}

	public void removeMapElementListener(IMapElementListener listener) {
		this.addListener(IMapElementListener.class, listener);
	}

	protected void notifyMapElementChanged() {
		for (IMapElementListener listener : getListener(IMapElementListener.class)) {
			listener.elementChanged();
		}
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
