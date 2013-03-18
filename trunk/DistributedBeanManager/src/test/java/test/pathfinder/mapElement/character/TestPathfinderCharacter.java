package test.pathfinder.mapElement.character;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.client.CampaignClient;
import net.alteiar.server.document.character.CharacterClient;
import net.alteiar.server.document.map.element.IAction;
import net.alteiar.server.document.map.element.MapElement;
import net.alteiar.server.document.map.element.size.MapElementSizeSquare;

public class TestPathfinderCharacter extends MapElement {

	private static final long serialVersionUID = 1L;

	private final Long charactedId;
	private final MapElementSizeSquare width;
	private final MapElementSizeSquare height;

	public TestPathfinderCharacter(CharacterClient character) {
		this(character.getId());
	}

	public TestPathfinderCharacter(Long characterId) {
		super();

		this.charactedId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);
	}

	@Override
	protected void load() {

	}

	private CharacterClient getCharacter() {
		return (CharacterClient) CampaignClient.getInstance().getDocument(
				charactedId);
	}

	@Override
	public Double getWidth() {
		return width.getPixels(getScale());
	}

	@Override
	public Double getHeight() {
		return height.getPixels(getScale());
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = getCharacter().getImage();

		Point position = getPosition();
		int x = (int) (position.getX() * zoomFactor);
		int y = (int) (position.getY() * zoomFactor);
		int width = (int) (getWidth() * zoomFactor);
		int height = (int) (getHeight() * zoomFactor);

		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		} else {
			g2.fillRect(x, y, width, height);
		}

		// Draw life bar
		// add 0.5 because cast round to lower bound
		int squareCount = Math.min(1,
				(int) ((width / (getScale().getPixels() * zoomFactor)) + 0.5));

		// 5% of the character height
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		int heightLife = Math.max((int) (5 * squareCount * zoomFactor), 5);
		int xLife = x;
		int yLife = y + height - heightLife;
		int widthLife = width;

		Integer currentHp = getCharacter().getCurrentHp();
		Integer totalHp = getCharacter().getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(xLife, yLife, (int) (widthLife * ratio), heightLife);
			g2.setColor(Color.BLACK);

			g2.setColor(Color.BLACK);
			g2.drawRect(xLife, yLife, widthLife - 1, heightLife - 1);
		}

		// Highlight
		/*
		 * if (isHighlighted()) {
		 * g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		 * 1f)); g2.setColor(Color.BLUE); Stroke org = g2.getStroke();
		 * g2.setStroke(new BasicStroke(8)); g2.drawRect(0, 0, width.intValue(),
		 * height.intValue()); g2.setStroke(org); }
		 */
		g2.dispose();
	}

	@Override
	public Boolean contain(Point p) {
		Point position = getPosition();
		return new Rectangle2D.Double(position.getX(), position.getY(),
				getWidth(), getHeight()).contains(p);
	}

	@Override
	public List<IAction> getActions() {
		return new ArrayList<IAction>();
	}
}
