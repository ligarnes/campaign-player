package net.alteiar;

import net.alteiar.newversion.server.ServerDocuments;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = Integer.valueOf(args[0]);
		String name = args[1];

		// Initialize log4j
		DOMConfigurator.configure("./ressources/log/log4j.xml");

		Logger.getLogger(MainServer.class).info(
				"Start server at " + port + " | name: " + name);

		try {
			new ServerDocuments(port, name);
		} catch (Exception e) {
			Logger.getLogger(MainServer.class).error("Erreur dans le serveur",
					e);
		}
	}
}
