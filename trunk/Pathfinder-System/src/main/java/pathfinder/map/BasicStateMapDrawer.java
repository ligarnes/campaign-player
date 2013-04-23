package pathfinder.map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.documents.map.Map;
import net.alteiar.image.ImageBean;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.filter.MapFilter;
import net.alteiar.shared.UniqueID;

public class BasicStateMapDrawer {

	private final Map map;
	private Boolean showGrid;

	public BasicStateMapDrawer(Map map) {
		this(map, true);
	}

	public BasicStateMapDrawer(Map map, Boolean showGrid) {
		this.map = map;
		this.showGrid = showGrid;
	}

	public void setShowGrid(Boolean showGrid) {
		this.showGrid = showGrid;
	}

	public Boolean getShowGrid() {
		return showGrid;
	}

	protected Map getMap() {
		return this.map;
	}

	public final void draw(Graphics2D g2, double zoomFactor) {
		drawBackground(g2, zoomFactor);
		drawElements(g2, zoomFactor);

		if (showGrid) {
			drawGrid(g2, zoomFactor);
		}
		drawFilter(g2, zoomFactor);

	}

	protected void drawBackground(Graphics2D g2, double zoomFactor) {
		Graphics2D g = (Graphics2D) g2.create();
		AffineTransform transform = new AffineTransform();
		transform.scale(zoomFactor, zoomFactor);

		BufferedImage backgroundImage = null;
		try {
			backgroundImage = ImageBean.getImage(map.getBackground());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, transform, null);
		}// else draw other stuff
		g.dispose();
	}

	protected void drawElements(Graphics2D g2, double zoomFactor) {
		for (UniqueID mapElementId : map.getElements()) {
			MapElement mapElement = CampaignClient.getInstance().getBean(
					mapElementId);
			mapElement.draw(g2, zoomFactor);
		}
	}

	protected void drawFilter(Graphics2D g2, double zoomFactor) {
		MapFilter filter = CampaignClient.getInstance()
				.getBean(map.getFilter());

		if (filter != null) {
			filter.draw(g2, zoomFactor);
		}
	}

	protected void drawGrid(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		Double squareSize = map.getScale().getPixels() * zoomFactor;

		Double width = map.getWidth() * zoomFactor;
		Double height = map.getHeight() * zoomFactor;

		for (double i = 0; i < width; i += squareSize) {
			g2.drawLine((int) i, 0, (int) i, height.intValue());
		}
		for (double i = 0; i < height; i += squareSize) {
			g2.drawLine(0, (int) i, width.intValue(), (int) i);
		}
		g2.dispose();
	}

}
