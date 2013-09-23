package shadowrun.bean.unit;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.alteiar.beans.media.ImageBean;
import net.alteiar.campaign.CampaignClient;
import net.alteiar.newversion.shared.bean.BasicBean;
import net.alteiar.shared.UniqueID;

import org.simpleframework.xml.Element;

public class ShadowrunCharacter extends BasicBean {
	private static final long serialVersionUID = 1L;

	public static final String PROP_NAME_PROPERTY = "name";

	public static final String PROP_PHYSICAL_DAMAGE_PROPERTY = "physicalDamage";
	public static final String PROP_STUN_PROPERTY = "stunDamage";

	public static final String PROP_BODY_PROPERTY = "body";
	public static final String PROP_AGILITY_PROPERTY = "agility";
	public static final String PROP_REACTION_PROPERTY = "reaction";
	public static final String PROP_STRENGTH_PROPERTY = "strength";

	public static final String PROP_CHARISMA_PROPERTY = "charisma";
	public static final String PROP_INTUITION_PROPERTY = "intuition";
	public static final String PROP_LOGIC_PROPERTY = "logic";
	public static final String PROP_WILLPOWER_PROPERTY = "willpower";

	public static final String PROP_EDGE_PROPERTY = "edge";
	public static final String PROP_ESSENCE_PROPERTY = "essence";
	public static final String PROP_INITIATIVE_PROPERTY = "initiative";
	public static final String PROP_MAGIC_RESSONNACE_PROPERTY = "magicResonnace";

	public static final String PROP_CURRENT_EDGE_PROPERTY = "currentEdge";
	public static final String PROP_ASTRAL_INIT_PROPERTY = "astralInit";
	public static final String PROP_MATRIX_INIT_PROPERTY = "matrixInit";
	public static final String PROP_INIT_PASSES_PROPERTY = "initPasses";

	public static final String PROP_IMAGE_PROPERTY = "image";

	@Element
	private String name;

	@Element
	private UniqueID image;

	@Element
	private Integer physicalDamage;

	@Element
	private Integer stunDamage;

	@Element
	private Integer body;

	@Element
	private Integer agility;

	@Element
	private Integer reaction;

	@Element
	private Integer strength;

	@Element
	private Integer charisma;

	@Element
	private Integer intuition;

	@Element
	private Integer logic;

	@Element
	private Integer willpower;

	@Element
	private Integer edge;

	@Element
	private Integer essence;

	@Element
	private Integer initiative;

	@Element
	private Integer magicResonnace;

	@Element
	private Integer currentEdge;

	@Element
	private Integer astralInit;

	@Element
	private Integer matrixInit;

	@Element
	private Integer initPasses;

	protected ShadowrunCharacter() {
		super();
	}

	public ShadowrunCharacter(String name, UniqueID image) {
		this.name = name;
		this.image = image;

		physicalDamage = 0;
		stunDamage = 0;

		this.body = 0;
		this.agility = 0;
		this.reaction = 0;
		this.strength = 0;

		this.charisma = 0;
		this.intuition = 0;
		this.logic = 0;
		this.willpower = 0;

		this.edge = 0;
		this.essence = 0;
		this.initiative = 0;
		this.magicResonnace = 0;

		this.currentEdge = 0;
		this.astralInit = 0;
		this.matrixInit = 0;
		this.initPasses = 0;
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

	public void setPhysicalDamage(Integer newValue) {
		Integer oldValue = this.physicalDamage;
		if (notifyRemote(PROP_PHYSICAL_DAMAGE_PROPERTY, oldValue, newValue)) {
			this.physicalDamage = newValue;
			notifyLocal(PROP_PHYSICAL_DAMAGE_PROPERTY, oldValue, newValue);
		}
	}

	public void setStunDamage(Integer newValue) {
		Integer oldValue = this.stunDamage;
		if (notifyRemote(PROP_STUN_PROPERTY, oldValue, newValue)) {
			this.stunDamage = newValue;
			notifyLocal(PROP_STUN_PROPERTY, oldValue, newValue);
		}
	}

	public void setBody(Integer newValue) {
		Integer oldValue = this.body;
		if (notifyRemote(PROP_BODY_PROPERTY, oldValue, newValue)) {
			this.body = newValue;
			notifyLocal(PROP_BODY_PROPERTY, oldValue, newValue);
		}
	}

	public void setAgility(Integer newValue) {
		Integer oldValue = this.agility;
		if (notifyRemote(PROP_AGILITY_PROPERTY, oldValue, newValue)) {
			this.agility = newValue;
			notifyLocal(PROP_AGILITY_PROPERTY, oldValue, newValue);
		}
	}

	public void setReaction(Integer newValue) {
		Integer oldValue = this.reaction;
		if (notifyRemote(PROP_REACTION_PROPERTY, oldValue, newValue)) {
			this.reaction = newValue;
			notifyLocal(PROP_REACTION_PROPERTY, oldValue, newValue);
		}
	}

	public void setStrenght(Integer newValue) {
		Integer oldValue = this.strength;
		if (notifyRemote(PROP_STRENGTH_PROPERTY, oldValue, newValue)) {
			this.strength = newValue;
			notifyLocal(PROP_STRENGTH_PROPERTY, oldValue, newValue);
		}
	}

	public void setCharisma(Integer newValue) {
		Integer oldValue = this.charisma;
		if (notifyRemote(PROP_CHARISMA_PROPERTY, oldValue, newValue)) {
			this.charisma = newValue;
			notifyLocal(PROP_CHARISMA_PROPERTY, oldValue, newValue);
		}
	}

	public void setIntuition(Integer newValue) {
		Integer oldValue = this.intuition;
		if (notifyRemote(PROP_INTUITION_PROPERTY, oldValue, newValue)) {
			this.intuition = newValue;
			notifyLocal(PROP_INTUITION_PROPERTY, oldValue, newValue);
		}
	}

	public void setLogic(Integer newValue) {
		Integer oldValue = this.logic;
		if (notifyRemote(PROP_LOGIC_PROPERTY, oldValue, newValue)) {
			this.logic = newValue;
			notifyLocal(PROP_LOGIC_PROPERTY, oldValue, newValue);
		}
	}

	public void setWillpower(Integer newValue) {
		Integer oldValue = this.willpower;
		if (notifyRemote(PROP_WILLPOWER_PROPERTY, oldValue, newValue)) {
			this.willpower = newValue;
			notifyLocal(PROP_WILLPOWER_PROPERTY, oldValue, newValue);
		}
	}

	public void setEdge(Integer newValue) {
		Integer oldValue = this.edge;
		if (notifyRemote(PROP_EDGE_PROPERTY, oldValue, newValue)) {
			this.edge = newValue;
			notifyLocal(PROP_EDGE_PROPERTY, oldValue, newValue);
		}
	}

	public void setEssence(Integer newValue) {
		Integer oldValue = this.essence;
		if (notifyRemote(PROP_ESSENCE_PROPERTY, oldValue, newValue)) {
			this.essence = newValue;
			notifyLocal(PROP_ESSENCE_PROPERTY, oldValue, newValue);
		}
	}

	public void setInitiative(Integer newValue) {
		Integer oldValue = this.initiative;
		if (notifyRemote(PROP_INITIATIVE_PROPERTY, oldValue, newValue)) {
			this.initiative = newValue;
			notifyLocal(PROP_INITIATIVE_PROPERTY, oldValue, newValue);
		}
	}

	public void setMagicResonnace(Integer newValue) {
		Integer oldValue = this.magicResonnace;
		if (notifyRemote(PROP_MAGIC_RESSONNACE_PROPERTY, oldValue, newValue)) {
			this.magicResonnace = newValue;
			notifyLocal(PROP_MAGIC_RESSONNACE_PROPERTY, oldValue, newValue);
		}
	}

	public void setCurrentEdge(Integer newValue) {
		Integer oldValue = this.currentEdge;
		if (notifyRemote(PROP_CURRENT_EDGE_PROPERTY, oldValue, newValue)) {
			this.currentEdge = newValue;
			notifyLocal(PROP_CURRENT_EDGE_PROPERTY, oldValue, newValue);
		}
	}

	public void setAstralInit(Integer newValue) {
		Integer oldValue = this.astralInit;
		if (notifyRemote(PROP_ASTRAL_INIT_PROPERTY, oldValue, newValue)) {
			this.astralInit = newValue;
			notifyLocal(PROP_ASTRAL_INIT_PROPERTY, oldValue, newValue);
		}
	}

	public void setMatrixInit(Integer newValue) {
		Integer oldValue = this.matrixInit;
		if (notifyRemote(PROP_MATRIX_INIT_PROPERTY, oldValue, newValue)) {
			this.matrixInit = newValue;
			notifyLocal(PROP_MATRIX_INIT_PROPERTY, oldValue, newValue);
		}
	}

	public void setInitPasses(Integer newValue) {
		Integer oldValue = this.initPasses;
		if (notifyRemote(PROP_INIT_PASSES_PROPERTY, oldValue, newValue)) {
			this.initPasses = newValue;
			notifyLocal(PROP_INIT_PASSES_PROPERTY, oldValue, newValue);
		}
	}

	public Integer getPhysicalDamage() {
		return physicalDamage;
	}

	public Integer getStunDamage() {
		return stunDamage;
	}

	public Integer getBody() {
		return body;
	}

	public Integer getAgility() {
		return agility;
	}

	public Integer getReaction() {
		return reaction;
	}

	public Integer getStrength() {
		return strength;
	}

	public Integer getCharisma() {
		return charisma;
	}

	public Integer getIntuition() {
		return intuition;
	}

	public Integer getLogic() {
		return logic;
	}

	public Integer getWillpower() {
		return willpower;
	}

	public Integer getEdge() {
		return edge;
	}

	public Integer getEssence() {
		return essence;
	}

	public Integer getInitiative() {
		return initiative;
	}

	public Integer getMagicResonnace() {
		return magicResonnace;
	}

	public Integer getCurrentEdge() {
		return currentEdge;
	}

	public Integer getAstralInit() {
		return astralInit;
	}

	public Integer getMatrixInit() {
		return matrixInit;
	}

	public Integer getInitPasses() {
		return initPasses;
	}

	@Override
	public void beanRemoved() {
		CampaignClient.getInstance().removeBean(image);
	}

	public Integer getPhysicalPoint() {
		return 8 + (int) Math.ceil(getBody() / 2.0);
	}

	public Integer getStunPoint() {
		return 8 + (int) Math.ceil(getWillpower() / 2.0);
	}
}
