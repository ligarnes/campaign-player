package net.alteiar.campaign.player.tools;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;

import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.logger.ExceptionTool;

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
	private static final String KEY_CREATE_IP_LOCAL = "create.ipLocal";
	private static final String KEY_CREATE_PORT = "create.port";

	private static final String KEY_JOIN_IP_LOCAL = "join.ipLocal";
	private static final String KEY_JOIN_IP_SERVER = "join.ipServer";
	private static final String KEY_JOIN_PORT = "join.port";

	private static final String KEY_LOAD_IP_LOCAL = "load.ipLocal";
	private static final String KEY_LOAD_PORT = "load.port";
	private static final String KEY_LOAD_CAMPAIGN = "load.campaign";

	private static final String KEY_CHOOSE_PLAYER = "load.player";

	private static final String KEY_PATH_IMAGES = "path.image";
	private static final String KEY_PATH_AUDIO = "path.audio";

	private static final String KEY_LANGUAGE = "language";
	private static final String KEY_COUNTRY = "country";

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
			ExceptionTool.showWarning(e,
					"Impossible de sauvegarder les propietes");
		}
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

	public String getCreateIpLocal() {
		return property.getValue(KEY_CREATE_IP_LOCAL, "127.0.0.1");
	}

	public void setCreateIpLocal(String ipLocal) {
		property.setValue(KEY_CREATE_IP_LOCAL, ipLocal);
	}

	public String getCreatePort() {
		return property.getValue(KEY_CREATE_PORT, "1099");
	}

	public void setCreatePort(String port) {
		property.setValue(KEY_CREATE_PORT, port);
	}

	public String getJoinIpLocal() {
		return property.getValue(KEY_JOIN_IP_LOCAL, "127.0.0.1");
	}

	public void setJoinIpLocal(String ipLocal) {
		property.setValue(KEY_JOIN_IP_LOCAL, ipLocal);
	}

	public String getJoinIpServer() {
		return property.getValue(KEY_JOIN_IP_SERVER, "127.0.0.1");
	}

	public void setJoinIpServer(String ipServer) {
		property.setValue(KEY_JOIN_IP_SERVER, ipServer);
	}

	public String getJoinPort() {
		return property.getValue(KEY_JOIN_PORT, "1099");
	}

	public void setJoinPort(String port) {
		property.setValue(KEY_JOIN_PORT, port);
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
	public String getLoadIpLocal() {
		return property.getValue(KEY_LOAD_IP_LOCAL, "127.0.0.1");
	}

	public void setLoadIpLocal(String ipLocal) {
		property.setValue(KEY_LOAD_IP_LOCAL, ipLocal);
	}

	public String getLoadPort() {
		return property.getValue(KEY_LOAD_PORT, "1099");
	}

	public void setLoadPort(Integer port) {
		property.setValue(KEY_LOAD_PORT, port.toString());
	}

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
