package pathfinder.character;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.CampaignClient;
import net.alteiar.documents.character.Character;
import net.alteiar.image.ImageBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class PathfinderCharacter extends Character {
	private static final long serialVersionUID = 1L;

	public static final String PROP_TOTAL_HP_PROPERTY = "totalHp";
	public static final String PROP_CURRENT_HP_PROPERTY = "currentHp";
	public static final String PROP_IMAGE_PROPERTY = "image";

	@Element
	private UniqueID image;

	@Element
	private Integer totalHp;
	@Element
	private Integer currentHp;

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
