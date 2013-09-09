package net.alteiar.campaign.player.tools;

import java.io.IOException;
import java.io.Serializable;

import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.logger.ExceptionTool;

/**
 * 
 * @author Cody Stoutenburg
 * 
 */

public class NetworkProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String KEY_SERVER_IP = "server.ip";
	private static final String KEY_SERVER_PORT = "server.port";

	private final PropertieBase property;

	public NetworkProperties() {
		super();
		property = new PropertieBase(
				HelpersPath.getPathProperties("Network.prop"));
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

	public String getServerIp() {
		return property.getValue(KEY_SERVER_IP, "127.0.0.1");
	}

	public int getServerPort() {
		return property.getIntegerValue(KEY_SERVER_PORT, 4545);
	}

}
