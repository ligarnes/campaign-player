package pathfinder.bean.spell;

import java.util.HashMap;

import net.alteiar.client.bean.BasicBean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

public class Spell extends BasicBean {
	private static final long serialVersionUID = 1L;

	@Element
	private String name;
	@Element
	private String school;

	@Element
	private UnitValue range;
	@Element
	private UnitValue castingTime;

	@Element
	private String description;

	@Element
	private String composant;

	@Element
	private String source;

	@Element
	private Reference reference;

	@ElementMap
	private HashMap<String, Integer> classesLevel;

	public Spell() {
	}

	public Spell(String name, String school, UnitValue range,
			UnitValue castingTime, String description, String composant,
			String source, Reference reference,
			HashMap<String, Integer> classesLevel) {
		super();
		this.name = name;
		this.school = school;
		this.range = range;
		this.castingTime = castingTime;
		this.description = description;
		this.composant = composant;
		this.source = source;
		this.reference = reference;
		this.classesLevel = classesLevel;
	}

	/**
	 * find the level of the spell for a specific class, or null if it does not
	 * belong to the class
	 * 
	 * @param classe
	 *            , the classe for which you want to know the spell level
	 * @return the level of the spell for the class or null
	 */
	public Integer getLevel(String classe) {
		return classesLevel.get(classe);
	}

	/**
	 * check if the spell belong to the spell list of the class
	 * 
	 * @param classe
	 * @return true if the spell belong to the class
	 */
	public Boolean belongTo(String classe) {
		return classesLevel.get(classe) != null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public UnitValue getRange() {
		return range;
	}

	public void setRange(UnitValue range) {
		this.range = range;
	}

	public UnitValue getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(UnitValue castingTime) {
		this.castingTime = castingTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComposant() {
		return composant;
	}

	public void setComposant(String composant) {
		this.composant = composant;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public HashMap<String, Integer> getClassesLevel() {
		return classesLevel;
	}

	public void setClassesLevel(HashMap<String, Integer> classesLevel) {
		this.classesLevel = classesLevel;
	}
}
