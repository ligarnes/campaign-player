package net.alteiar.server.document.character;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PathfinderCharacter implements IPathfinderCharacter {
	private static final long serialVersionUID = 1L;

	private String name;

	private Integer hp;
	private Integer currentHp;

	private Float width;
	private Float height;

	private Integer ac;
	private Integer acFlatFooted;
	private Integer acTouch;

	private Integer initMod;

	private byte[] file;

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

	@Override
	public Integer getInitMod() {
		return initMod;
	}

	public void setInitMod(Integer initMod) {
		this.initMod = initMod;
	}
}
