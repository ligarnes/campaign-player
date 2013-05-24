package pathfinder.gui.mapElement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import net.alteiar.CampaignClient;
import net.alteiar.map.elements.MapElement;
import net.alteiar.shared.UniqueID;
import net.alteiar.utils.map.element.MapElementSize;
import net.alteiar.utils.map.element.MapElementSizeSquare;

import org.simpleframework.xml.Element;

import pathfinder.bean.unit.monster.PathfinderMonster;

public class PathfinderMonsterElement extends MapElement {

	private static final long serialVersionUID = 1L;

	public static final String PROP_WIDTH_PROPERTY = "width";
	public static final String PROP_HEIGHT_PROPERTY = "height";

	@Element
	private UniqueID monsterId;
	@Element
	private MapElementSize width;
	@Element
	private MapElementSize height;

	public PathfinderMonsterElement() {
	}

	public PathfinderMonsterElement(Point point, PathfinderMonster character) {
		this(point, character.getId());
	}

	public PathfinderMonsterElement(Point point, UniqueID characterId) {
		super(point);

		this.monsterId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);
	}

	public PathfinderMonster getMonster() {
		return CampaignClient.getInstance().getBean(monsterId);
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
	protected void drawElement(Graphics2D g, double zoomFactor) {
		Graphics2D g2 = (Graphics2D) g.create();
		BufferedImage background = getMonster().getMonsterImage();

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

		Integer currentHp = getMonster().getCurrentHp();
		Integer totalHp = getMonster().getTotalHp();

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

	public UniqueID getCharactedId() {
		return monsterId;
	}

	public void setCharactedId(UniqueID charactedId) {
		this.monsterId = charactedId;
	}

	public MapElementSize getWidth() {
		return width;
	}

	public void setWidth(MapElementSize width) {
		MapElementSize oldValue = this.width;
		if (notifyRemote(PROP_WIDTH_PROPERTY, oldValue, width)) {
			this.width = width;
			this.propertyChangeSupport.firePropertyChange(PROP_WIDTH_PROPERTY,
					oldValue, width);
		}
	}

	public MapElementSize getHeight() {
		return height;
	}

	public void setHeight(MapElementSize height) {
		MapElementSize oldValue = this.height;
		if (notifyRemote(PROP_HEIGHT_PROPERTY, oldValue, height)) {
			this.height = height;
			this.propertyChangeSupport.firePropertyChange(PROP_HEIGHT_PROPERTY,
					oldValue, height);
		}
	}

	@Override
	public String getNameFormat() {
		if (getWidth().getShortUnitFormat().equals(
				getHeight().getShortUnitFormat())) {
			return getMonster().getName() + " " + getWidth().getValue() + "x"
					+ getHeight().getValue() + " "
					+ getWidth().getShortUnitFormat();
		}
		return getMonster().getName() + " " + getWidth().getValue()
				+ getWidth().getShortUnitFormat() + "x"
				+ getHeight().getValue() + " "
				+ getHeight().getShortUnitFormat();
	}
}
