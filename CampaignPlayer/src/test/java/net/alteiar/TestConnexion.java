package net.alteiar;

import java.rmi.RMISecurityManager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.alteiar.campaign.player.gui.MainFrame;
import net.alteiar.client.shared.campaign.CampaignClient;
import net.alteiar.server.shared.campaign.ServerCampaign;

import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecAdapter;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;

public class TestConnexion extends UISpecTestCase {

	static {
		UISpec4J.init();
	}

	@Override
	protected void setUp() throws Exception {
		// System.out.println("setup");
		setAdapter(new testAdapter());
	}

	public void testSomething() {
		Window mainWindow = getMainWindow();

		// mainWindow.getButton("Ajouter").click();

		// mainWindow.getButton("Connexion").click();

		// ...
		// mainWindow.getTree().contentEquals(...);
	}

	private class testAdapter implements UISpecAdapter {

		@Override
		public Window getMainWindow() {
			System.setProperty("java.security.policy",
					"./ressources/security.txt");
			// Create and install a security manager
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}

			try {
				// Set cross-platform Java L&F (also called "Metal")
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			String localAdress = "127.0.0.1";
			String address = "127.0.0.1";
			String port = "1099";

			String name = "Test";
			Boolean isMj = true;

			Boolean isServer = true;

			if (isServer) {
				ServerCampaign.startServer(address, Integer.valueOf(port));
			}

			CampaignClient.connect(localAdress, address, port, name, isMj);

			MainFrame.FRAME.initComponent();
			CampaignClient.createClientCampaign(5, MainFrame.FRAME);
			// MainFrame.FRAME.setVisible(true);

			return new Window(MainFrame.FRAME);
		}

	}
}