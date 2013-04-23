package pathfinder.map.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import net.alteiar.campaign.player.gui.map.battle.MapEditableInfo;
import net.alteiar.campaign.player.gui.map.drawable.DrawInfo;
import pathfinder.gui.drawable.CharactersDrawable;

public class PathfinderDrawInfo extends DrawInfo {

	public PathfinderDrawInfo(MapEditableInfo mapInfo) {
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
