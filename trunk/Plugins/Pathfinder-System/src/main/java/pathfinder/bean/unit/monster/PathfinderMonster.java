package pathfinder.bean.unit.monster;

import generic.bean.unit.Unit;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class PathfinderMonster extends BasicBean implements Unit {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";
	public static final String PROP_TOTAL_HP_PROPERTY = "totalHp";
	public static final String PROP_CURRENT_HP_PROPERTY = "currentHp";
	public static final String PROP_IMAGE_PROPERTY = "image";

	public static final String PROP_INIT_MOD_PROPERTY = "initMod";

	@Element
	private String name;

	@Element
	private UniqueID image;

	@Element
	private Integer totalHp;

	@Element
	private Integer currentHp;

	@Element
	private Integer challengeRating;

	@Element
	private Integer ac;

	@Element
	private Integer acFlatFooted;

	@Element
	private Integer acTouch;

	@Element
	private Integer reflex;

	@Element
	private Integer fortitude;

	@Element
	private Integer will;

	private Integer initMod;

	protected PathfinderMonster() {
		super();
		totalHp = 0;
		currentHp = 0;
	}

	public PathfinderMonster(String name, Integer totalHp, Integer currentHp,
			Integer challengeRating, Integer ac, Integer acFlatFooted,
			Integer acTouch, Integer reflex, Integer fortitude, Integer will,
			UniqueID image) {
		this.name = name;
		this.totalHp = totalHp;
		this.currentHp = currentHp;

		this.challengeRating = challengeRating;

		this.ac = ac;
		this.acFlatFooted = acFlatFooted;
		this.acTouch = acTouch;

		this.reflex = reflex;
		this.fortitude = fortitude;
		this.will = will;

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
			notifyLocal(PROP_NAME_PROPERTY, oldValue, name);
		}
	}

	@Override
	public Integer getTotalHp() {
		return totalHp;
	}

	@Override
	public void setTotalHp(Integer totalHp) {
		Integer oldValue = this.totalHp;
		if (notifyRemote(PROP_TOTAL_HP_PROPERTY, oldValue, totalHp)) {
			this.totalHp = totalHp;
			notifyLocal(PROP_TOTAL_HP_PROPERTY, oldValue, totalHp);
		}
	}

	@Override
	public Integer getCurrentHp() {
		return currentHp;
	}

	@Override
	public void setCurrentHp(Integer currentHp) {
		Integer oldValue = this.currentHp;
		if (notifyRemote(PROP_CURRENT_HP_PROPERTY, oldValue, currentHp)) {
			this.currentHp = currentHp;
			notifyLocal(PROP_CURRENT_HP_PROPERTY, oldValue, currentHp);
		}
	}

	public UniqueID getImage() {
		return image;
	}

	public void setImage(UniqueID image) {
		UniqueID oldValue = this.image;
		if (notifyRemote(PROP_IMAGE_PROPERTY, oldValue, image)) {
			this.image = image;
			notifyLocal(PROP_IMAGE_PROPERTY, oldValue, image);
		}
	}

	@Override
	public Integer getInitMod() {
		return initMod;
	}

	@Override
	public void setInitMod(Integer initMod) {
		Integer oldValue = this.initMod;
		if (notifyRemote(PROP_INIT_MOD_PROPERTY, oldValue, initMod)) {
			this.initMod = initMod;
			notifyLocal(PROP_INIT_MOD_PROPERTY, oldValue, initMod);
		}
	}

	@Override
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}
}
