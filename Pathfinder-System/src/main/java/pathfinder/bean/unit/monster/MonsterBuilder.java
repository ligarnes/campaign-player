package pathfinder.bean.unit.monster;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.client.bean.BasicBean;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class MonsterBuilder extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element
	private String name;

	@Element
	private UniqueID image;

	@Element
	private Integer totalHp;

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

	protected MonsterBuilder() {
		super();
	}

	public MonsterBuilder(String name, UniqueID image, Integer totalHp,
			Integer challengeRating, Integer ac, Integer acFlatFooted,
			Integer caTouch, Integer reflex, Integer fortitude, Integer will) {
		super();
		this.name = name;
		this.image = image;
		this.totalHp = totalHp;
		this.challengeRating = challengeRating;
		this.ac = ac;
		this.acFlatFooted = acFlatFooted;
		this.acTouch = caTouch;
		this.reflex = reflex;
		this.fortitude = fortitude;
		this.will = will;
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

	protected void setName(String name) {
		this.name = name;
	}

	public Integer getTotalHp() {
		return totalHp;
	}

	protected void setTotalHp(Integer totalHp) {
		this.totalHp = totalHp;
	}

	public UniqueID getImage() {
		return image;
	}

	protected void setImage(UniqueID image) {
		this.image = image;
	}

	public Integer getChallengeRating() {
		return challengeRating;
	}

	public void setChallengeRating(Integer challengeRating) {
		this.challengeRating = challengeRating;
	}

	public Integer getAc() {
		return ac;
	}

	public void setAc(Integer ac) {
		this.ac = ac;
	}

	public Integer getAcFlatFooted() {
		return acFlatFooted;
	}

	public void setAcFlatFooted(Integer acFlatFooted) {
		this.acFlatFooted = acFlatFooted;
	}

	public Integer getAcTouch() {
		return acTouch;
	}

	public void setAcTouch(Integer caTouch) {
		this.acTouch = caTouch;
	}

	public Integer getReflex() {
		return reflex;
	}

	public void setReflex(Integer reflex) {
		this.reflex = reflex;
	}

	public Integer getFortitude() {
		return fortitude;
	}

	public void setFortitude(Integer fortitude) {
		this.fortitude = fortitude;
	}

	public Integer getWill() {
		return will;
	}

	public void setWill(Integer will) {
		this.will = will;
	}

	public PathfinderMonster createMonster() {
		return new PathfinderMonster(name, totalHp, totalHp, challengeRating,
				ac, acFlatFooted, acTouch, reflex, fortitude, will, image);
	}
}
