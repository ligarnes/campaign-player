package pathfinder.mapElement.character;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.map.elements.MapElement;
import net.alteiar.utils.map.element.MapElementSizeSquare;
import pathfinder.character.PathfinderCharacter;

public class PathfinderCharacterElement extends MapElement {

	private static final long serialVersionUID = 1L;

	private final Long charactedId;
	private final MapElementSizeSquare width;
	private final MapElementSizeSquare height;

	public PathfinderCharacterElement(Point point, PathfinderCharacter character) {
		this(point, character.getId());
	}

	public PathfinderCharacterElement(Point point, Long characterId) {
		super(point);

		this.charactedId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);

	}

	public PathfinderCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(charactedId);
	}

	@Override
	public Double getWidthPixels() {
		return width.getPixels(getScale());
	}

	@Override
	public Double getHeightPixels() {
		return height.getPixels(getScale());
	}

	@Override
	public void draw(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = null;
		try {
			background = getCharacter().getCharacterImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Point position = getPosition();
		int x = (int) (position.getX() * zoomFactor);
		int y = (int) (position.getY() * zoomFactor);
		int width = (int) (getWidthPixels() * zoomFactor);
		int height = (int) (getHeightPixels() * zoomFactor);

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
		int yLife = y + height - heightLife;
		int widthLife = width;

		Integer currentHp = getCharacter().getCurrentHp();
		Integer totalHp = getCharacter().getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(x, yLife, (int) (widthLife * ratio), heightLife);
		}

		g2.setColor(Color.BLACK);
		g2.drawRect(x, yLife, widthLife - 1, heightLife - 1);

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
				getWidthPixels(), getHeightPixels()).contains(p);
	}

	/*
	 * @Override public List<IAction> getActions() { List<IAction> actions = new
	 * ArrayList<IAction>(); actions.add(new DoDamage(getCharacter()));
	 * actions.add(new DoHeal(getCharacter())); return actions; }
	 */
}
