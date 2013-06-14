package net.alteiar.map.elements;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.List;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.documents.map.MapBean;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.Scale;

import org.simpleframework.xml.Element;

public abstract class MapElement extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_MAP_ID_PROPERTY = "mapId";
	public static final String PROP_POSITION_PROPERTY = "position";
	public static final String PROP_ANGLE_PROPERTY = "angle";
	public static final String PROP_HIDDEN_FOR_PLAYER_PROPERTY = "hiddenForPlayer";

	@Element
	private UniqueID mapId;

	@Element
	private Point position;
	@Element
	private Double angle;
	@Element
	private Boolean hiddenForPlayer;

	private Point lastPosition;
	private Boolean selected;

	protected MapElement() {
		selected = false;
	}

	public MapElement(Point position) {
		this.position = position;
		this.lastPosition = position;
		this.angle = 0.0;
		this.hiddenForPlayer = true;
		this.selected = false;
	}

	public final void draw(Graphics2D g, double zoomFactor) {
		if (isHiddenForPlayer()) {
			Boolean isMj = CampaignClient.getInstance().getCurrentPlayer()
					.isDm();
			if (isMj) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.5f));
				drawElement(g2, zoomFactor);
				g2.dispose();
			}
		} else {
			drawElement(g, zoomFactor);
		}

		if (getSelected()) {
			drawSelection(g, zoomFactor);
		}
	}

	protected abstract void drawElement(Graphics2D g, double zoomFactor);

	protected void drawSelection(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();

		Point position = getPosition();
		int x = (int) (position.getX() * zoomFactor);
		int y = (int) (position.getY() * zoomFactor);
		int width = Long.valueOf(Math.round(getWidthPixels() * zoomFactor))
				.intValue();
		int height = Long.valueOf(Math.round(getHeightPixels() * zoomFactor))
				.intValue();

		AffineTransform transform = new AffineTransform();
		transform.translate(x, y);

		g2.setColor(CampaignClient.getInstance().getCurrentPlayer().getColor());
		g2.setStroke(new BasicStroke(5.0f));
		g2.transform(transform);
		g2.drawRect(0, 0, width, height);
		g2.dispose();
	}

	public abstract Boolean contain(Point p);

	public final Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(position.getX(), position.getY(),
				getWidthPixels(), getHeightPixels());
	}

	public abstract Double getWidthPixels();

	public abstract Double getHeightPixels();

	protected MapBean getMap() {
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

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * allow to move the map element locally only so the other user are not
	 * notify of the change.
	 * 
	 * to apply the change you need to call @see{applyMove}
	 * 
	 * @param position
	 */
	public void moveTo(Point position) {
		if (lastPosition == null) {
			this.lastPosition = this.position;
		}
		Point oldValue = this.position;
		this.position = position;
		propertyChangeSupport.firePropertyChange(PROP_POSITION_PROPERTY,
				oldValue, position);
	}

	/**
	 * apply the local change to the other player.
	 */
	public void applyMove() {
		if (lastPosition == null) {
			this.lastPosition = this.position;
		}
		Point newPosition = this.position;
		this.position = lastPosition;
		setPosition(newPosition);
	}

	public void undoMove() {
		this.position = lastPosition;
	}

	// ///////////////// BEAN METHODS ///////////////////////
	/**
	 * 
	 * @return
	 */
	public final UniqueID getMapId() {
		return this.mapId;
	}

	public void setMapId(UniqueID mapId) {
		UniqueID oldValue = this.mapId;
		if (notifyRemote(PROP_MAP_ID_PROPERTY, oldValue, mapId)) {
			this.mapId = mapId;
			propertyChangeSupport.firePropertyChange(PROP_MAP_ID_PROPERTY,
					oldValue, mapId);
		}
	}

	public final Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		Point oldValue = this.position;
		if (notifyRemote(PROP_POSITION_PROPERTY, oldValue, position)) {
			this.lastPosition = position;
			this.position = position;
			propertyChangeSupport.firePropertyChange(PROP_POSITION_PROPERTY,
					oldValue, position);
		}
	}

	public final Double getAngle() {
		return angle;
	}

	public final void setAngle(Double angle) {
		Double oldValue = this.angle;
		if (notifyRemote(PROP_ANGLE_PROPERTY, oldValue, angle)) {
			this.angle = angle;
			propertyChangeSupport.firePropertyChange(PROP_ANGLE_PROPERTY,
					oldValue, angle);
		}
	}

	public final Boolean isHiddenForPlayer() {
		return hiddenForPlayer;
	}

	/**
	 * allow to show or hide the map element for all player the element will
	 * always be visible for the mj but non opaque
	 * 
	 * @param hiddenForPlayer
	 */
	public final void setHiddenForPlayer(Boolean hiddenForPlayer) {
		Boolean oldValue = this.hiddenForPlayer;
		if (notifyRemote(PROP_HIDDEN_FOR_PLAYER_PROPERTY, oldValue,
				hiddenForPlayer)) {
			this.hiddenForPlayer = hiddenForPlayer;
			propertyChangeSupport.firePropertyChange(
					PROP_HIDDEN_FOR_PLAYER_PROPERTY, oldValue, hiddenForPlayer);
		}
	}

	/**
	 * 
	 * @return the formated name of the map element
	 */
	public abstract String getNameFormat();

	/**
	 * 
	 * @return all the specific action can the map element done
	 */
	public List<IAction> getActions() {
		return Collections.emptyList();
	}
}
