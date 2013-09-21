package pathfinder.bean.unit;

import generic.bean.unit.Unit;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class PathfinderCharacter extends BasicBean implements Unit {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";

	public static final String PROP_TOTAL_HP_PROPERTY = "totalHp";
	public static final String PROP_CURRENT_HP_PROPERTY = "currentHp";
	public static final String PROP_AC_PROPERTY = "ac";
	public static final String PROP_AC_TOUCH_PROPERTY = "acTouch";
	public static final String PROP_AC_FLAT_FOOTED_PROPERTY = "acFlatFooted";

	public static final String PROP_INIT_MOD_PROPERTY = "initMod";

	public static final String PROP_IMAGE_PROPERTY = "image";

	@Element
	private String name;

	@Element
	private UniqueID image;

	@Element
	private Integer totalHp;
	@Element
	private Integer currentHp;

	@Element
	private Integer ac;

	@Element
	private Integer acTouch;

	@Element
	private Integer acFlatFooted;

	@Element
	private Integer initMod;

	protected PathfinderCharacter() {
		super();
		totalHp = 0;
		currentHp = 0;
		initMod = 0;
	}

	public PathfinderCharacter(String name, Integer totalHp, Integer currentHp,
			Integer ac, Integer acTouch, Integer acFlatFooted, Integer initMod,
			UniqueID image) {
		this.name = name;
		this.totalHp = totalHp;
		this.currentHp = currentHp;
		this.initMod = initMod;
		this.image = image;

		this.ac = ac;
		this.acFlatFooted = acFlatFooted;
		this.acTouch = acTouch;
	}

	// ////////////// METHODS /////////////////
	public BufferedImage getCharacterImage() {
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
		return this.name;
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
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}

	public Integer getAc() {
		return ac;
	}

	public void setAc(Integer ac) {
		Integer oldValue = this.ac;
		if (notifyRemote(PROP_AC_PROPERTY, oldValue, ac)) {
			this.ac = ac;
			notifyLocal(PROP_AC_PROPERTY, oldValue, ac);
		}
	}

	public Integer getAcTouch() {
		return acTouch;
	}

	public void setAcTouch(Integer acTouch) {
		Integer oldValue = this.acTouch;
		if (notifyRemote(PROP_AC_TOUCH_PROPERTY, oldValue, acTouch)) {
			this.acTouch = acTouch;
			notifyLocal(PROP_AC_TOUCH_PROPERTY, oldValue, acTouch);
		}
	}

	public Integer getAcFlatFooted() {
		return acFlatFooted;
	}

	public void setAcFlatFooted(Integer acFlatFooted) {
		Integer oldValue = this.acFlatFooted;
		if (notifyRemote(PROP_AC_FLAT_FOOTED_PROPERTY, oldValue, acFlatFooted)) {
			this.acFlatFooted = acFlatFooted;
			notifyLocal(PROP_AC_FLAT_FOOTED_PROPERTY, oldValue, acFlatFooted);
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

}
