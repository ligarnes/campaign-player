package net.alteiar.server.document.map.element;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import net.alteiar.server.document.map.Scale;

public abstract class MapElement implements Serializable {
	private static final long serialVersionUID = 1L;

	private MapElementClient mapElement;

	public MapElement() {
	}

	public final void loadMapElement(MapElementClient mapElement) {
		this.mapElement = mapElement;
		load();
	}

	protected abstract void load();

	public abstract void draw(Graphics2D g, double zoomFactor);

	public abstract Boolean contain(Point p);

	public abstract List<IAction> getActions();

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

	protected Scale getScale() {
		return mapElement.getScale();
	}

	/**
	 * 
	 * @return the center in pixel
	 */
	public Point getCenterPosition() {
		Point p = getPosition();
		int x = (int) (p.getX() + (getWidth() / 2));
		int y = (int) (p.getY() + (getHeight() / 2));
		return new Point(x, y);
	}

	/**
	 * 
	 * @return the position
	 */
	public final Point getPosition() {
		return mapElement.getPosition();
	}

	public final Double getAngle() {
		return mapElement.getAngle();
	}

	public final Boolean getIsHidden() {
		return mapElement.getIsHidden();
	}

	protected final void notifyMapElementChanged() {
		mapElement.notifyMapElementChanged();
	}
}
