package net.alteiar.map.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyVetoException;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBeans;
import net.alteiar.map.Map;
import net.alteiar.utils.map.Scale;

public abstract class MapElement extends BasicBeans {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MAP_ID_PROPERTY = "mapId";
	public static final String PROP_POSITION_PROPERTY = "position";
	public static final String PROP_ANGLE_PROPERTY = "angle";
	public static final String PROP_HIDDEN_FOR_PLAYER_PROPERTY = "hiddenForPlayer";

	private Long mapId;

	private Point position;
	private Double angle;
	private Boolean hiddenForPlayer;

	public MapElement(Long mapId, Point position) {
		this.mapId = mapId;

		this.position = position;
		this.angle = 0.0;
		this.hiddenForPlayer = true;
	}

	public abstract void draw(Graphics2D g, double zoomFactor);

	public abstract Boolean contain(Point p);

	public abstract Double getWidthPixels();

	public abstract Double getHeightPixels();

	protected Map getMap() {
		return CampaignClient.getInstance().getBean(mapId);
	}

	protected Scale getScale() {
		return getMap().getScale();
	}

	/**
	 * 
	 * @return the center in pixel
	 */
	public Point getCenterPosition() {
		Point p = getPosition();
		int x = (int) (p.getX() + (getWidthPixels() / 2));
		int y = (int) (p.getY() + (getHeightPixels() / 2));
		return new Point(x, y);
	}

	// ///////////////// BEAN METHODS ///////////////////////
	/**
	 * 
	 * @return
	 */
	public final Long getMapId() {
		return this.mapId;
	}

	public final void setMapId(Long mapId) {
		Long oldValue = this.mapId;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_MAP_ID_PROPERTY, oldValue, mapId);
			this.mapId = mapId;
			propertyChangeSupport.firePropertyChange(PROP_MAP_ID_PROPERTY,
					oldValue, mapId);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public final Point getPosition() {
		return position;
	}

	public final void setPosition(Point position) {
		Point oldValue = this.position;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_POSITION_PROPERTY, oldValue, position);
			this.position = position;
			propertyChangeSupport.firePropertyChange(PROP_POSITION_PROPERTY,
					oldValue, position);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public final Double getAngle() {
		return angle;
	}

	public final void setAngle(Double angle) {
		Double oldValue = this.angle;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(PROP_ANGLE_PROPERTY,
					oldValue, angle);
			this.angle = angle;
			propertyChangeSupport.firePropertyChange(PROP_ANGLE_PROPERTY,
					oldValue, angle);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}

	public final Boolean isHiddenForPlayer() {
		return hiddenForPlayer;
	}

	public final void setHiddenForPlayer(Boolean hiddenForPlayer) {
		Boolean oldValue = this.hiddenForPlayer;
		try {
			vetoableRemoteChangeSupport.fireVetoableChange(
					PROP_HIDDEN_FOR_PLAYER_PROPERTY, oldValue, hiddenForPlayer);
			this.hiddenForPlayer = hiddenForPlayer;
			propertyChangeSupport.firePropertyChange(
					PROP_HIDDEN_FOR_PLAYER_PROPERTY, oldValue, hiddenForPlayer);
		} catch (PropertyVetoException e) {
			// TODO
			// e.printStackTrace();
		}
	}
}
