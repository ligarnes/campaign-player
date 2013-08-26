package net.alteiar.campaign.player.infos;

import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {

	// TODO FIXME do not like that
	private static Languages languages = new Languages(Helpers
			.getGlobalProperties().getLanguage(), Helpers.getGlobalProperties()
			.getCountry());

	public static String getText(String text) {
		return languages.getMessage(text);
	}

	private final Locale currentLocale;

	private final ResourceBundle messages;

	public Languages(String language, String country) {
		currentLocale = new Locale(language, country);

		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	}

	public String getMessage(String text) {
		return messages.getString(text);
	}

}
