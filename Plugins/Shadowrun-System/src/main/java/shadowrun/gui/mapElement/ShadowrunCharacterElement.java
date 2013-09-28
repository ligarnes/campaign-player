package shadowrun.gui.mapElement;

import generic.gui.mapElement.BarElement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.SuppressBeanListener;
import net.alteiar.beans.map.elements.IAction;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.beans.map.size.MapElementSize;
import net.alteiar.beans.map.size.MapElementSizeSquare;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.documents.BeanDocument;
import net.alteiar.factory.MapElementFactory;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

import shadowrun.actions.AddToCombatTracker;
import shadowrun.actions.DoDamage;
import shadowrun.actions.DoHeal;
import shadowrun.actions.ShowCharacter;
import shadowrun.bean.unit.ShadowrunCharacter;

public class ShadowrunCharacterElement extends MapElement {

	private static final long serialVersionUID = 1L;

	public static final String PROP_WIDTH_PROPERTY = "width";
	public static final String PROP_HEIGHT_PROPERTY = "height";

	@Element
	private UniqueID charactedId;
	@Element
	private MapElementSize width;
	@Element
	private MapElementSize height;

	private transient BarElement physicalBar;
	private transient BarElement stunBar;

	public ShadowrunCharacterElement() {
		physicalBar = new BarElement(Color.RED, Color.GREEN);
		stunBar = new BarElement(Color.RED, Color.BLUE);
	}

	public ShadowrunCharacterElement(Point point, BeanDocument character) {
		this(point, character.getId());
	}

	public ShadowrunCharacterElement(Point point, final UniqueID characterId) {
		super(point);

		CampaignClient.getInstance().addSuppressBeanListener(
				new SuppressBeanListener() {
					@Override
					public UniqueID getBeanId() {
						return characterId;
					}

					@Override
					public void beanRemoved(BasicBean bean) {
						MapElementFactory.removeMapElement(
								ShadowrunCharacterElement.this, getMap());
					}
				});

		this.charactedId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);

		physicalBar = new BarElement(Color.RED, Color.GREEN);
		stunBar = new BarElement(Color.RED, Color.BLUE);
	}

	public BeanDocument getDocumentCharacter() {
		return CampaignClient.getInstance().getBean(charactedId);
	}

	public ShadowrunCharacter getCharacter() {
		return CampaignClient.getInstance().getBean(
				getDocumentCharacter().getBeanId());
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
		getDocumentCharacter().getBean().addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);

		if (getDocumentCharacter().getBean() != null) {
			getDocumentCharacter().getBean().removePropertyChangeListener(
					listener);
		}
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
	protected void drawElement(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g.create();
		ShadowrunCharacter character = getCharacter();
		BufferedImage background = character.getCharacterImage();

		Point position = getPosition();
		int x = (int) (position.getX());
		int y = (int) (position.getY());
		int width = getWidthPixels().intValue();
		int height = getHeightPixels().intValue();

		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		} else {
			g2.fillRect(x, y, width, height);
		}

		// add 0.5 because cast round to lower bound
		int squareCount = Math.min(1,
				(int) ((width / (getScale().getPixels())) + 0.5));

		// 5% of the character height
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.7f));
		int heightLife = Math.max(5 * squareCount, 10);
		int yLife = y + height - heightLife;
		int widthLife = width;

		float physical = getCharacter().getPhysicalPoint().floatValue();
		float physicalDamage = getCharacter().getPhysicalDamage().floatValue();
		float probPhysical = 1 - (physicalDamage / physical);

		physicalBar.drawBar(g2, x, yLife, heightLife, widthLife, probPhysical);

		float stun = getCharacter().getStunPoint().floatValue();
		float stunDamage = getCharacter().getStunDamage().floatValue();
		float probStun = 1 - (stunDamage / stun);

		stunBar.drawBar(g2, x, yLife - heightLife, heightLife, widthLife,
				probStun);

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
		ShadowrunCharacter character = getCharacter();
		actions.add(new DoDamage(character));
		actions.add(new DoHeal(character));

		actions.add(new AddToCombatTracker(character));

		actions.add(new ShowCharacter(getDocumentCharacter()));
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
