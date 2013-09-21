package net.alteiar.campaign.player.plugin.external;

import java.util.ArrayList;

public interface IPlugin {

	void initialize();

	Class<?>[] getClasses();

	ArrayList<DocumentPlugin> getDocuments();

	ArrayList<MapElementPlugin> getMapElements();

}
