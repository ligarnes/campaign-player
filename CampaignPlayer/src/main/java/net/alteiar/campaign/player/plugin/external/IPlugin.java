package net.alteiar.campaign.player.plugin.external;

import java.util.ArrayList;

public interface IPlugin {
	Class<?>[] getClasses();

	ArrayList<DocumentPlugin> getDocuments();

	ArrayList<MapElementPlugin> getMapElements();

}
