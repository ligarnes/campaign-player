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
	private String source;
	@Element
	private String url;
	@ElementMap
	private HashMap<String, Integer> classesLevel;

	public Spell() {
	}

	public Spell(String name, String school, String source, String url,
			HashMap<String, Integer> classesLevel) {
		super();
		this.name = name;
		this.school = school;
		this.source = source;
		this.url = url;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected HashMap<String, Integer> getClassesLevel() {
		return classesLevel;
	}

	public void setClassesLevel(HashMap<String, Integer> classesLevel) {
		this.classesLevel = classesLevel;
	}

	@Override
	public String toString() {
		return "SpellBean [name=" + name + ", school=" + school + ", source="
				+ source + ", url=" + url + ", classesLevel=" + classesLevel
				+ "]";
	}

}
