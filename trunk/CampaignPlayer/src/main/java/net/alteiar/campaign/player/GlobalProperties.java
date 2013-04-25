package net.alteiar.campaign.player;

import java.awt.Color;
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

	private static final String KEY_CREATE_PSEUDO = "create.pseudo";
	private static final String KEY_CREATE_COLOR_RED = "create.color.red";
	private static final String KEY_CREATE_COLOR_GREEN = "create.color.green";
	private static final String KEY_CREATE_COLOR_BLUE = "create.color.blue";
	private static final String KEY_CREATE_IP_LOCAL = "create.ipLocal";
	private static final String KEY_CREATE_IP_SERVER = "create.ipServer";
	private static final String KEY_CREATE_PORT = "create.port";
	
	private static final String KEY_JOIN_IP_LOCAL = "join.ipLocal";
	private static final String KEY_JOIN_IP_SERVER = "join.ipServer";
	private static final String KEY_JOIN_PORT = "join.port";
	
	private static final String KEY_LOAD_IP_LOCAL = "load.ipLocal";
	private static final String KEY_LOAD_IP_SERVER = "load.ipServer";
	private static final String KEY_LOAD_PORT = "load.port";
	
//	private static final String KEY_IS_MJ = "isMj";
//	private static final String KEY_IP_LOCAL = "ipLocal";
//	private static final String KEY_IP_SERVER = "ipServer";
//	private static final String KEY_PORT = "port";
//	private static final String KEY_IS_SERVER = "isServer";

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
		property.setValue(KEY_CREATE_COLOR_GREEN, String.valueOf(color.getGreen()));
		property.setValue(KEY_CREATE_COLOR_BLUE, String.valueOf(color.getBlue()));
	}


//	public Boolean isMj() {
//		return property.getBooleanValue(KEY_IS_MJ);
//	}
//
//	public void setIsMj(Boolean isMj) {
//		property.setValue(KEY_IS_MJ, isMj.toString());
//	}

	public String getCreateIpLocal() {
		return property.getValue(KEY_CREATE_IP_LOCAL, "127.0.0.1");
	}

	public void setCreateIpLocal(String ipLocal) {
		property.setValue(KEY_CREATE_IP_LOCAL, ipLocal);
	}

	public String getCreateIpServer() {
		return property.getValue(KEY_CREATE_IP_SERVER, "127.0.0.1");
	}

	public void setCreateIpServer(String ipServer) {
		property.setValue(KEY_CREATE_IP_SERVER, ipServer);
	}

	public String getCreatePort() {
		return property.getValue(KEY_CREATE_PORT, "1099");
	}

	public void setCreatePort(String port) {
		property.setValue(KEY_CREATE_PORT, port);
	}

//	public Boolean isServer() {
//		return property.getBooleanValue(KEY_IS_SERVER);
//	}
//
//	public void setIsServer(Boolean isServer) {
//		property.setValue(KEY_IS_SERVER, isServer.toString());
//	}
	
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
	
	public String getLoadIpLocal() {
		return property.getValue(KEY_LOAD_IP_LOCAL, "127.0.0.1");
	}

	public void setLoadIpLocal(String ipLocal) {
		property.setValue(KEY_LOAD_IP_LOCAL, ipLocal);
	}

	public String getLoadIpServer() {
		return property.getValue(KEY_LOAD_IP_SERVER, "127.0.0.1");
	}

	public void setLoadIpServer(String ipServer) {
		property.setValue(KEY_LOAD_IP_SERVER, ipServer);
	}

	public String getLoadPort() {
		return property.getValue(KEY_LOAD_PORT, "1099");
	}

	public void setLoadPort(String port) {
		property.setValue(KEY_LOAD_PORT, port);
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
