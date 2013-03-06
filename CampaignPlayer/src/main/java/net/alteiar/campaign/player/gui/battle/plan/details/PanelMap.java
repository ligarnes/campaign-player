package net.alteiar.campaign.player.gui.battle.plan.details;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.alteiar.server.document.map.battle.BattleClient;
import net.alteiar.server.document.map.element.MapElementClient;

public class PanelMap extends PanelBasicMap {
	private static final long serialVersionUID = 1L;

	public PanelMap(BattleClient map) {
		super(map);
	}

	@Override
	protected void drawBackground(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = map.getBackground();
		// if the image do not work we load error image
		if (background == null) {
			g2.setColor(Color.RED);
			g2.drawString("Impossible de charger l'image", 30, 30);
		} else {
			g2.drawImage(background, 0, 0,
					(int) (background.getWidth() * getZoomFactor()),
					(int) (background.getHeight() * getZoomFactor()), null);
		}
		g2.dispose();
	}

	@Override
	protected void drawElements(Graphics2D g2) {
		for (MapElementClient<?> element : map.getElements()) {
			element.draw(g2, getZoomFactor());
		}
	}

	@Override
	protected void drawCharacter(Graphics2D g2) {
		// TODO
		/*
		 * System.out.println("draw characters: " +
		 * map.getAllCharacter().length); for (ICharacterCombatClient element :
		 * map.getAllCharacter()) { if (element.isVisibleForPlayer() ||
		 * CampaignClient.INSTANCE.getCurrentPlayer().isMj()) {
		 * 
		 * Point2D.Double position = convertPointStandardToPanel(element
		 * .getPosition()); Point2D.Double rotationOffset =
		 * convertPointStandardToPanel(element .getCenterOffset());
		 * 
		 * // creating the AffineTransform instance BufferedImage img =
		 * element.getShape(getZoomFactor()); AffineTransform affineTransform =
		 * new AffineTransform(); affineTransform.translate(position.x,
		 * position.y);
		 * 
		 * affineTransform.rotate(Math.toRadians(element.getAngle()),
		 * rotationOffset.x, rotationOffset.y); g2.drawImage(img,
		 * affineTransform, null); } }
		 */
	}

	@Override
	protected void drawGrid(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		Double squareSize = this.map.getScale().getPixels() * getZoomFactor();

		Double width = this.map.getWidth() * getZoomFactor();
		Double height = this.map.getHeight() * getZoomFactor();

		for (double i = 0; i < width; i += squareSize) {
			g2.drawLine((int) i, 0, (int) i, height.intValue());
		}
		for (double i = 0; i < height; i += squareSize) {
			g2.drawLine(0, (int) i, width.intValue(), (int) i);
		}
		g2.dispose();
	}

	@Override
	protected void drawFilter(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage img = this.map.getFilter();
		g2.drawImage(img, 0, 0, (int) (img.getWidth() * getZoomFactor()),
				(int) (img.getHeight() * getZoomFactor()), null);
		g2.dispose();
	}

}
