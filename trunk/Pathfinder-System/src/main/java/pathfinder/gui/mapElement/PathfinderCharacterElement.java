package pathfinder.gui.mapElement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.map.elements.IAction;
import net.alteiar.map.elements.MapElement;
import net.alteiar.map.size.MapElementSize;
import net.alteiar.map.size.MapElementSizeSquare;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

import pathfinder.actions.AddToCombatTracker;
import pathfinder.actions.DoDamage;
import pathfinder.actions.DoHeal;
import pathfinder.bean.unit.PathfinderCharacter;

public class PathfinderCharacterElement extends MapElement {

	private static final long serialVersionUID = 1L;

	public static final String PROP_WIDTH_PROPERTY = "width";
	public static final String PROP_HEIGHT_PROPERTY = "height";

	@Element
	private UniqueID charactedId;
	@Element
	private MapElementSize width;
	@Element
	private MapElementSize height;

	public PathfinderCharacterElement() {
	}

	public PathfinderCharacterElement(Point point, BeanDocument character) {
		this(point, character.getId());
	}

	public PathfinderCharacterElement(Point point, UniqueID characterId) {
		super(point);

		this.charactedId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);
	}

	public BeanDocument getDocumentCharacter() {
		return CampaignClient.getInstance().getBean(charactedId);
	}

	public PathfinderCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(
				getDocumentCharacter().getBeanId());
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
		getDocumentCharacter().addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		getDocumentCharacter().removePropertyChangeListener(listener);
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
		PathfinderCharacter character = getCharacter();
		BufferedImage background = character.getCharacterImage();

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

		Integer currentHp = character.getCurrentHp();
		Integer totalHp = character.getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);
		if (currentHp > 0) {
			Color hp = new Color(1.0f - ratio, ratio, 0);
			g2.setColor(hp);
			g2.fillRect(x, yLife, (int) (widthLife * ratio), heightLife);
		}

		g2.setColor(Color.BLACK);
		g2.drawRect(x, yLife, widthLife - 1, heightLife - 1);

		g2.dispose();
	}

	@Override
	public Boolean contain(Point p) {
		Point position = getPosition();
		return new Rectangle2D.Double(position.getX(), position.getY(),
				getWidthPixels(), getHeightPixels()).contains(p);
	}

	@Override
	public List<IAction> getActions() {
		ArrayList<IAction> actions = new ArrayList<IAction>();
		PathfinderCharacter character = getCharacter();
		actions.add(new DoDamage(character));
		actions.add(new DoHeal(character));

		actions.add(new AddToCombatTracker(character));
		return actions;
	}

	public UniqueID getCharactedId() {
		return charactedId;
	}

	public void setCharactedId(UniqueID charactedId) {
		this.charactedId = charactedId;
	}

	public MapElementSize getWidth() {
		return width;
	}

	public void setWidth(MapElementSize width) {
		MapElementSize oldValue = this.width;
		if (notifyRemote(PROP_WIDTH_PROPERTY, oldValue, width)) {
			this.width = width;
			notifyLocal(PROP_WIDTH_PROPERTY, oldValue, width);
		}
	}

	public MapElementSize getHeight() {
		return height;
	}

	public void setHeight(MapElementSize height) {
		MapElementSize oldValue = this.height;
		if (notifyRemote(PROP_HEIGHT_PROPERTY, oldValue, height)) {
			this.height = height;
			notifyLocal(PROP_HEIGHT_PROPERTY, oldValue, height);
		}
	}

	@Override
	public String getNameFormat() {
		if (getWidth().getShortUnitFormat().equals(
				getHeight().getShortUnitFormat())) {
			return getDocumentCharacter().getDocumentName() + " "
					+ getWidth().getValue() + "x" + getHeight().getValue()
					+ " " + getWidth().getShortUnitFormat();
		}
		return getDocumentCharacter().getDocumentName() + " "
				+ getWidth().getValue() + getWidth().getShortUnitFormat() + "x"
				+ getHeight().getValue() + " "
				+ getHeight().getShortUnitFormat();
	}

}
