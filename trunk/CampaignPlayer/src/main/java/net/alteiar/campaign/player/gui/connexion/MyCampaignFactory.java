package net.alteiar.campaign.player.gui.connexion;

import net.alteiar.campaign.CampaignFactoryNew;
import net.alteiar.campaign.player.infos.HelpersPath;
import net.alteiar.campaign.player.plugin.PluginSystem;
import net.alteiar.campaign.player.tools.NetworkProperties;

import org.apache.log4j.Logger;

public class MyCampaignFactory {

	public static void startNewCampaign() {
		NetworkProperties networkProp = new NetworkProperties();

		try {
			CampaignFactoryNew.startNewCampaign(networkProp.getServerIp(),
					networkProp.getServerPort(), HelpersPath.PATH_SAVE,
					PluginSystem.getInstance().getPluginBeans(), PluginSystem
							.getInstance().getKryo());
		} catch (Exception e) {
			Logger.getLogger(MyCampaignFactory.class).error(
					"Impossible de créer la partie", e);
		}
	}

	public static void joinCampaign() {
		NetworkProperties networkProp = new NetworkProperties();

		try {
			CampaignFactoryNew.connectToServer(networkProp.getServerIp(),
					networkProp.getServerPort(), HelpersPath.PATH_SAVE,
					PluginSystem.getInstance().getPluginBeans(), PluginSystem
							.getInstance().getKryo());
		} catch (Exception e) {
			Logger.getLogger(MyCampaignFactory.class).error(
					"Impossible de rejoindre la partie", e);
		}
	}

	public static void loadCampaign(String campaignPath) {
		NetworkProperties networkProp = new NetworkProperties();

		try {
			CampaignFactoryNew.loadCampaign(networkProp.getServerIp(),
					networkProp.getServerPort(), HelpersPath.PATH_SAVE,
					PluginSystem.getInstance().getPluginBeans(), campaignPath,
					PluginSystem.getInstance().getKryo());
		} catch (Exception e) {
			Logger.getLogger(MyCampaignFactory.class).error(
					"Impossible de charger la partie", e);
		}
	}
}
