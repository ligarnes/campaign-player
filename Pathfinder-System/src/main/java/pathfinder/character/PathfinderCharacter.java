package pathfinder.character;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.documents.character.CharacterBean;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class PathfinderCharacter extends CharacterBean {
	private static final long serialVersionUID = 1L;

	@Element
	private Integer totalHp;
	@Element
	private Integer currentHp;
	@Element
	private UniqueID image;

	protected PathfinderCharacter() {
		super();
		totalHp = 0;
		currentHp = 0;
	}

	public PathfinderCharacter(String name, Integer totalHp, Integer currentHp,
			UniqueID image) {
		super(name);
		this.totalHp = totalHp;
		this.currentHp = currentHp;
		this.image = image;
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
		return getDocumentName();
	}

	public void setName(String name) {
		this.setDocumentName(name);
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

	@Override
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}
}
