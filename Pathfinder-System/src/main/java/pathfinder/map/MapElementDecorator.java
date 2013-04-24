package pathfinder.map;

import java.awt.Graphics2D;
import java.awt.Point;

import net.alteiar.map.elements.MapElement;

public class MapElementDecorator extends MapElement {
	private static final long serialVersionUID = 1L;

	private final MapElement decorateur;

	public MapElementDecorator(MapElement decorateur) {
		this.decorateur = decorateur;
	}

	@Override
	public void drawElement(Graphics2D g, double zoomFactor) {
		decorateur.draw(g, zoomFactor);
	}

	protected void drawDecorator() {

	}

	@Override
	public Boolean contain(Point p) {
		return decorateur.contain(p);
	}

	@Override
	public Double getWidthPixels() {
		return decorateur.getWidthPixels();
	}

	@Override
	public Double getHeightPixels() {
		return decorateur.getHeightPixels();
	}

}
