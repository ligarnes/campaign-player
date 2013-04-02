package pathfinder.character;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.UniqueID;

public class PathfinderCharacter extends CharacterBean {
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer totalHp;
	private Integer currentHp;
	private UniqueID image;

	public PathfinderCharacter() {
		totalHp = 0;
		currentHp = 0;
	}

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

	public UniqueID getImage() {
		return image;
	}

	public void setImage(UniqueID image) {
		this.image = image;
	}
}
