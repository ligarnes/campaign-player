package net.alteiar.campaign.player;

import java.io.IOException;
import java.io.Serializable;

import net.alteiar.shared.ExceptionTool;

/**
 * 
 * @author Cody Stoutenburg
 * 
 */

public class GlobalProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String KEY_PSEUDO = "pseudo";
	private static final String KEY_IS_MJ = "isMj";
	private static final String KEY_IP_LOCAL = "ipLocal";
	private static final String KEY_IP_SERVER = "ipServer";
	private static final String KEY_PORT = "port";
	private static final String KEY_IS_SERVER = "isServer";

	private static final String KEY_MAP_PATH = "mapPath";
	private static final String KEY_CHARACTER_PATH = "characterPath";

	private final PropertieBase property;

	public GlobalProperties() {
		super();
		property = new PropertieBase(
				Helpers.getPathProperties("GlobalProperties.prop"));
		try {
			property.load();
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}
	}

	public void save() throws IOException {
		property.save();
	}

	public String getPseudo() {
		return property.getValue(KEY_PSEUDO, "pseudo");
	}

	public void setPseudo(String pseudo) {
		property.setValue(KEY_PSEUDO, pseudo);
	}

	public Boolean isMj() {
		return property.getBooleanValue(KEY_IS_MJ);
	}

	public void setIsMj(Boolean isMj) {
		property.setValue(KEY_IS_MJ, isMj.toString());
	}

	public String getIpLocal() {
		return property.getValue(KEY_IP_LOCAL, "127.0.0.1");
	}

	public void setIpLocal(String ipLocal) {
		property.setValue(KEY_IP_LOCAL, ipLocal);
	}

	public String getIpServer() {
		return property.getValue(KEY_IP_SERVER, "127.0.0.1");
	}

	public void setIpServer(String ipServer) {
		property.setValue(KEY_IP_SERVER, ipServer);
	}

	public String getPort() {
		return property.getValue(KEY_PORT, "1099");
	}

	public void setPort(String port) {
		property.setValue(KEY_PORT, port);
	}

	public Boolean isServer() {
		return property.getBooleanValue(KEY_IS_SERVER);
	}

	public void setIsServer(Boolean isServer) {
		property.setValue(KEY_IS_SERVER, isServer.toString());
	}

	public String getMapPath() {
		return this.property.getValue(KEY_MAP_PATH, ".");
	}

	public void setMapPath(String path) {
		property.setValue(KEY_MAP_PATH, path);
	}

	public String getCharacterPath() {
		return this.property.getValue(KEY_CHARACTER_PATH, ".");
	}

	public void setCharacterPath(String path) {
		property.setValue(KEY_CHARACTER_PATH, path);
	}
}
