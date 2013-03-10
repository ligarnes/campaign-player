package net.alteiar.server.document.character;

import java.awt.image.BufferedImage;

public interface ICharacter {

	int getIntValue(String var);

	float getFloatValue(String var);

	String getStringValue(String var);

	BufferedImage getImageValue(String var);

	void setValue(String var, int val);

	void setValue(String var, float val);

	void setValue(String var, String val);

	void setValue(String var, BufferedImage val);
}
