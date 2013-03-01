package net.alteiar.server.document.character;

import java.io.Serializable;

public interface IPathfinderCharacter extends Serializable {

	void setName(String name);

	String getName();

	Integer getHp();

	Integer getCurrentHp();

	void setCurrentHp(Integer hp);

	Integer getAc();

	Integer getAcFlatFooted();

	Integer getAcTouch();

	Integer getInitMod();

	byte[] getImage();

	Float getWidth();

	Float getHeight();
}
