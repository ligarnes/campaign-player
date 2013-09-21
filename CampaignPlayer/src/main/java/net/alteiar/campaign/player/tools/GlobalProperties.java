package net.alteiar.campaign.player.tools;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;

import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.logger.ExceptionTool;

import org.apache.log4j.Logger;

/**
 * 
 * @author Cody Stoutenburg
 * 
 */

public class GlobalProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String KEY_CREATE_PSEUDO = "create.pseudo";
	private static final String KEY_CREATE_COLOR_RED = "create.color.red";
	private static final String KEY_CREATE_COLOR_GREEN = "create.color.green";
	private static final String KEY_CREATE_COLOR_BLUE = "create.color.blue";

	private static final String KEY_LOAD_CAMPAIGN = "load.campaign";

	private static final String KEY_CHOOSE_PLAYER = "load.player";

	private static final String KEY_PATH_IMAGES = "path.image";
	private static final String KEY_PATH_AUDIO = "path.audio";

	private static final String KEY_LANGUAGE = "language";
	private static final String KEY_COUNTRY = "country";

	private static final String KEY_GAME_SYSTEM = "game.system";

	private final PropertieBase property;

	public GlobalProperties() {
		super();
		property = new PropertieBase(
				HelpersPath.getPathProperties("GlobalProperties.prop"));
		try {
			property.load();
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public void save() {
		try {
			property.save();
		} catch (IOException e) {
			// if we fail to save just too bad, properties are here to help
			// user
			Logger.getLogger(getClass()).debug(e);
		}
	}

	public String getGameSystem() {
		return property.getValue(KEY_GAME_SYSTEM, "Inconnu");
	}

	public void setGameSystem(String gameSystem) {
		this.property.setValue(KEY_GAME_SYSTEM, gameSystem);
	}

	public String getCreatePseudo() {
		return property.getValue(KEY_CREATE_PSEUDO, "votre pseudo");
	}

	public void setCreatePseudo(String pseudo) {
		property.setValue(KEY_CREATE_PSEUDO, pseudo);
	}

	public Color getCreateColor() {
		int red = property.getIntegerValue(KEY_CREATE_COLOR_RED, 0);
		int green = property.getIntegerValue(KEY_CREATE_COLOR_GREEN, 0);
		int blue = property.getIntegerValue(KEY_CREATE_COLOR_BLUE, 255);
		return new Color(red, green, blue);
	}

	public void setCreateColor(Color color) {
		property.setValue(KEY_CREATE_COLOR_RED, String.valueOf(color.getRed()));
		property.setValue(KEY_CREATE_COLOR_GREEN,
				String.valueOf(color.getGreen()));
		property.setValue(KEY_CREATE_COLOR_BLUE,
				String.valueOf(color.getBlue()));
	}

	public String getPathImages() {
		return this.property.getValue(KEY_PATH_IMAGES, ".");
	}

	public void setPathImages(String path) {
		property.setValue(KEY_PATH_IMAGES, path);
	}

	public String getPathAudio() {
		return this.property.getValue(KEY_PATH_AUDIO, ".");
	}

	public void setPathAudio(String path) {
		property.setValue(KEY_PATH_AUDIO, path);
	}

	public String getCountry() {
		return property.getValue(KEY_COUNTRY, "FR");
	}

	public void setCountry(String country) {
		property.setValue(KEY_COUNTRY, country);
	}

	public String getLanguage() {
		return property.getValue(KEY_LANGUAGE, "fr");
	}

	public void setLanguage(String language) {
		property.setValue(KEY_LANGUAGE, language);
	}

	/**
	 * Load methods
	 */
	public String getLoadCampaign() {
		return property.getValue(KEY_LOAD_CAMPAIGN, "");
	}

	public void setLoadCampaign(String campaignPath) {
		property.setValue(KEY_LOAD_CAMPAIGN, campaignPath);
	}

	public String getPlayerChoose() {
		return property.getValue(KEY_CHOOSE_PLAYER, "");
	}

	public void setPlayerChoose(String playerName) {
		property.setValue(KEY_CHOOSE_PLAYER, playerName);
	}

}
