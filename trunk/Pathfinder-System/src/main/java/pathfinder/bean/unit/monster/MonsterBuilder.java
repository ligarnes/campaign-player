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

	protected MonsterBuilder() {
		super();
		totalHp = 0;
	}

	public MonsterBuilder(String name, Integer totalHp, UniqueID image) {
		this.name = name;
		this.totalHp = totalHp;
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

	@Override
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}

	public PathfinderMonster createMonster() {
		return new PathfinderMonster(name, totalHp, totalHp, image);
	}
}
