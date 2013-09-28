package pathfinder.gui.mapElement;

import generic.actions.DoDamage;
import generic.actions.DoHeal;
import generic.gui.mapElement.BarElement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import net.alteiar.WaitBeanListener;
import net.alteiar.beans.map.elements.IAction;
import net.alteiar.beans.map.elements.MapElement;
import net.alteiar.beans.map.size.MapElementSize;
import net.alteiar.beans.map.size.MapElementSizeSquare;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

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

	private transient BarElement damageBar;

	public PathfinderMonsterElement() {
		damageBar = new BarElement(Color.RED, Color.GREEN);
	}

	public PathfinderMonsterElement(Point point, PathfinderMonster character) {
		this(point, character.getId());
	}

	public PathfinderMonsterElement(Point point, UniqueID characterId) {
		super(point);

		this.monsterId = characterId;
		width = new MapElementSizeSquare(1);
		height = new MapElementSizeSquare(1);
		damageBar = new BarElement(Color.RED, Color.GREEN);
	}

	public PathfinderMonster getMonster() {
		return CampaignClient.getInstance().getBean(monsterId);
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
		PathfinderMonster monster = getMonster();

		if (monster == null) {
			CampaignClient.getInstance().addWaitBeanListener(
					new WaitBeanListener() {
						@Override
						public UniqueID getBeanId() {
							return monsterId;
						}

						@Override
						public void beanReceived(BasicBean bean) {
							bean.addPropertyChangeListener(listener);
						}
					});
		} else {
			getMonster().addPropertyChangeListener(listener);
		}
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		getMonster().removePropertyChangeListener(listener);
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
		BufferedImage background = getMonster().getMonsterImage();

		Point position = getPosition();
		int x = (int) position.getX();
		int y = (int) position.getY();
		int width = getWidthPixels().intValue();
		int height = getHeightPixels().intValue();

		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		} else {
			g2.fillRect(x, y, width, height);
		}

		// Draw life bar
		// add 0.5 because cast round to lower bound
		int squareCount = Math.min(1,
				(int) ((width / getScale().getPixels()) + 0.5));

		int heightLife = Math.max(5 * squareCount, 5);
		int yLife = y + height - heightLife;
		int widthLife = width;

		Integer currentHp = getMonster().getCurrentHp();
		Integer totalHp = getMonster().getTotalHp();

		Float ratio = Math.min(1.0f, currentHp / (float) totalHp);

		damageBar.drawBar(g2, x, yLife, heightLife, widthLife, ratio);
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
		actions.add(new DoDamage(getMonster()));
		actions.add(new DoHeal(getMonster()));
		return actions;
	}

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
