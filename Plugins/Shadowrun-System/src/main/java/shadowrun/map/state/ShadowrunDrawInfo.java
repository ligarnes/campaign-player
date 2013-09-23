package shadowrun.map.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import net.alteiar.campaign.player.gui.centerViews.map.MapEditableInfo;
import net.alteiar.campaign.player.gui.centerViews.map.drawable.DrawFilter;
import shadowrun.gui.drawable.CharactersDrawable;

public class ShadowrunDrawInfo extends DrawFilter {

	public ShadowrunDrawInfo(MapEditableInfo mapInfo) {
		super(mapInfo);
	}

	@Override
	public void draw(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2.create();

		drawOriginal(g);

		Point org = getMapInfo().getViewPosition();

		AffineTransform orinalTranslate = new AffineTransform();
		orinalTranslate.setToTranslation(org.x, org.y);

		g.transform(orinalTranslate);
		// draw
		drawOnVision(g);
	}

	protected void drawOriginal(Graphics2D g2) {

	}

	protected void drawOnVision(Graphics2D g2) {
		CharactersDrawable draw = new CharactersDrawable();
		draw.draw(g2);
	}
}
