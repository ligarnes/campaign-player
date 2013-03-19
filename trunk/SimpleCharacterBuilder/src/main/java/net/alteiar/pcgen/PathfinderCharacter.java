package net.alteiar.pcgen;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

import net.alteiar.CharacterIO;
import net.alteiar.ExceptionTool;

@XmlRootElement
public class PathfinderCharacter implements PathfinderCharacterFacade {
	@Attribute
	private static final long serialVersionUID = 1L;

	@Element
	private String name;

	@Element
	private Integer hp;
	
	@Element
	private Integer currentHp;

	@Element
	private Float width;
	
	@Element
	private Float height;

	@Element
	private Integer ac;
	
	@Element
	private Integer acFlatFooted;
	
	@Element
	private Integer acTouch;

	@Element
	private Integer initMod;

	@ElementArray
	private byte[] file;

	@Element
	private String htmlCharacterSheet;

	public PathfinderCharacter() {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	@Override
	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	@Override
	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer pv) {
		this.hp = pv;
	}

	@Override
	public Integer getAc() {
		return ac;
	}

	public void setAc(Integer ac) {
		this.ac = ac;
	}

	@Override
	public Integer getAcFlatFooted() {
		return acFlatFooted;
	}

	public void setAcFlatFooted(Integer acFlatFooted) {
		this.acFlatFooted = acFlatFooted;
	}

	@Override
	public Integer getAcTouch() {
		return acTouch;
	}

	public void setAcTouch(Integer acTouch) {
		this.acTouch = acTouch;
	}

	@Override
	public Integer getCurrentHp() {
		return currentHp;
	}

	@Override
	public void setCurrentHp(Integer currentHp) {
		this.currentHp = currentHp;
	}

	@Override
	public byte[] getImage() {
		return this.file;
	}

	public void setImage(byte[] img) {
		this.file = img;
	}

	public String getFile() {
		return null;
	}

	public void setFile(String path) {
		try {
			this.file = CharacterIO.getBytesFromFile(new File(path));
		} catch (Exception e) {
			ExceptionTool.showWarning(e,
					"Impossible de lire l'image de votre personnage");
		}
	}

	@Override
	public Integer getInitMod() {
		return initMod;
	}

	public void setInitMod(Integer initMod) {
		this.initMod = initMod;
	}

	@Override
	public String getHtmlCharacter() {
		return htmlCharacterSheet;
	}

	public void setHtmlCharacter(String htmlCharacterSheet) {
		this.htmlCharacterSheet = htmlCharacterSheet;
	}

	@Override
	public PathfinderCharacterFacade FastCopy(String newName) {
		PathfinderCharacter character = new PathfinderCharacter();
		character.setName(newName);
		character.ac = ac;
		character.acFlatFooted = acFlatFooted;
		character.acTouch = acTouch;
		character.hp = hp;
		character.currentHp = currentHp;
		character.width = width;
		character.height = height;
		character.initMod = initMod;
		return character;
	}
}
