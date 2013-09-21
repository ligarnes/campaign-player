package net.alteiar.campaign.player.infos;

public class HelpersPath {

	public static final String PATH_ICONS = "./ressources/icons/";
	public static final String PATH_TEXTURE = "./ressources/texture/";
	public static final String PATH_PROPERTIES = "./ressources/data/";
	public static final String PATH_SAVE = "./save/";
	public static final String PATH_PLUGIN = "./ressources/plugin/";

	public static String getPathTexture(String name) {
		return PATH_TEXTURE + name;
	}

	public static String getPathIcons(String name) {
		return PATH_ICONS + name;
	}

	public static String getPathProperties(String name) {
		return PATH_PROPERTIES + name;
	}
}
