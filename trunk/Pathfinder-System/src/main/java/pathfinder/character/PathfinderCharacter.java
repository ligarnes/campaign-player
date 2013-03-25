package pathfinder.character;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.character.CharacterBean;
import net.alteiar.image.ImageBean;

public class PathfinderCharacter extends CharacterBean {
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer totalHp;
	private Integer currentHp;
	private Long image;

	// ////////////// METHODS /////////////////
	public BufferedImage getCharacterImage() throws IOException {
		ImageBean image = CampaignClient.getInstance().getBean(this.image);
		if (image == null) {
			return null;
		}
		return image.getImage().restoreImage();
	}

	@Override
	public String getVisibleName() {
		return getName();
	}

	// ////////////// BEANS METHODS /////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalHp() {
		return totalHp;
	}

	public void setTotalHp(Integer totalHp) {
		this.totalHp = totalHp;
	}

	public Integer getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(Integer currentHp) {
		this.currentHp = currentHp;
	}

	public Long getImage() {
		return image;
	}

	public void setImage(Long image) {
		this.image = image;
	}
}
