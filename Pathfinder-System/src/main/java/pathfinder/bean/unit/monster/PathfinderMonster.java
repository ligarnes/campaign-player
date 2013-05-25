package pathfinder.bean.unit.monster;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class PathfinderMonster extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_TOTAL_HP_PROPERTY = "totalHp";
	public static final String PROP_CURRENT_HP_PROPERTY = "currentHp";
	public static final String PROP_IMAGE_PROPERTY = "image";

	@Element
	private String name;

	@Element
	private UniqueID image;

	@Element
	private Integer totalHp;

	@Element
	private Integer currentHp;

	protected PathfinderMonster() {
		super();
		totalHp = 0;
		currentHp = 0;
	}

	public PathfinderMonster(String name, Integer totalHp, Integer currentHp,
			UniqueID image) {
		this.name = name;
		this.totalHp = totalHp;
		this.currentHp = currentHp;
		this.image = image;
	}

	// ////////////// METHODS /////////////////
	public BufferedImage getMonsterImage() {
		ImageBean image = CampaignClient.getInstance().getBean(this.image);
		if (image == null) {
			return null;
		}
		try {
			return image.getImage().restoreImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}

	// ////////////// BEANS METHODS /////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		if (notifyRemote(PROP_NAME_PROPERTY, oldValue, name)) {
			this.name = name;
			propertyChangeSupport.firePropertyChange(PROP_NAME_PROPERTY,
					oldValue, name);
		}
	}

	public Integer getTotalHp() {
		return totalHp;
	}

	public void setTotalHp(Integer totalHp) {
		Integer oldValue = this.totalHp;
		if (notifyRemote(PROP_TOTAL_HP_PROPERTY, oldValue, totalHp)) {
			this.totalHp = totalHp;
			propertyChangeSupport.firePropertyChange(PROP_TOTAL_HP_PROPERTY,
					oldValue, totalHp);
		}
	}

	public Integer getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(Integer currentHp) {
		Integer oldValue = this.totalHp;
		if (notifyRemote(PROP_CURRENT_HP_PROPERTY, oldValue, currentHp)) {
			this.currentHp = currentHp;
			propertyChangeSupport.firePropertyChange(PROP_CURRENT_HP_PROPERTY,
					oldValue, currentHp);
		}
	}

	public UniqueID getImage() {
		return image;
	}

	public void setImage(UniqueID image) {
		Integer oldValue = this.totalHp;
		if (notifyRemote(PROP_IMAGE_PROPERTY, oldValue, image)) {
			this.image = image;
			propertyChangeSupport.firePropertyChange(PROP_IMAGE_PROPERTY,
					oldValue, image);
		}
	}

	@Override
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}
}
