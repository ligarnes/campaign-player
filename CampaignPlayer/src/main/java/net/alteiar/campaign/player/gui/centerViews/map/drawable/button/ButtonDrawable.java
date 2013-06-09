package net.alteiar.campaign.player.gui.centerViews.map.drawable.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.Drawable;

public class ButtonDrawable extends Drawable {

	private final MapEditableInfo info;
	private int x;
	private int y;
	private final BufferedImage icon;

	public ButtonDrawable(MapEditableInfo info, BufferedImage icon) {
		this(info, new Point(0, 0), icon);
	}

	public ButtonDrawable(MapEditableInfo info, Point position,
			BufferedImage icon) {
		this.info = info;
		this.x = position.x;
		this.y = position.y;
		this.icon = icon;
	}

	public void setPosition(Point position) {
		this.x = position.x;
		this.y = position.y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return icon.getWidth();
	}

	public int getHeight() {
		return icon.getHeight();
	}

	@Override
	public void draw(Graphics2D g2, double zoomFactor) {
		Graphics2D g = (Graphics2D) g2.create();

		drawOriginal(g);

		Point org = info.getViewPosition();

		AffineTransform orinalTranslate = new AffineTransform();
		orinalTranslate.setToTranslation(org.x, org.y);

		g.transform(orinalTranslate);
		// draw
		drawOnVision(g);
	}

	protected void drawOriginal(Graphics2D g2) {
	}

	protected void drawOnVision(Graphics2D g2) {
		g2.drawImage(icon, x, y, null);
	}

	@Override
	public boolean contain(Point p) {

		Point org = info.getViewPosition();

		Rectangle rect = new Rectangle(x, y, icon.getWidth(), icon.getHeight());
		rect.translate(org.x, org.y);

		return rect.contains(p);
	}
}
