package net.alteiar.campaign.player.gui.connexion;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;

import net.alteiar.campaign.player.infos.Helpers;
import net.alteiar.campaign.player.logger.ExceptionTool;

public abstract class PanelStartGameDialog extends JPanel {
	private static final long serialVersionUID = -6625096424819076011L;

	private final StartGameDialog startGameDialog;
	private final PanelStartGameDialog previous;

	public PanelStartGameDialog(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		this.startGameDialog = startGameDialog;
		this.previous = previous;
	}

	protected StartGameDialog getDialog() {
		return startGameDialog;
	}

	protected abstract PanelStartGameDialog getNext();

	protected void previousState() {
		if (previous != null) {
			this.startGameDialog.changeState(previous);
		} else {
			stopApplication();
		}
	}

	protected void nextState() {
		PanelStartGameDialog next = getNext();
		if (next != null) {
			this.startGameDialog.changeState(next);
		} else {
			// save properties before run
			Helpers.getGlobalProperties().save();
			this.startApplication();
		}
	}

	private void stopApplication() {
		this.startGameDialog.quitApplication();
	}

	private void startApplication() {
		this.startGameDialog.startApplication();
	}

	protected static List<String> getAddress() {
		List<String> allAdresses = new ArrayList<String>();

		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface network = interfaces.nextElement();

				if (network.isUp() && !network.isLoopback()) {
					Enumeration<InetAddress> addr = network.getInetAddresses();

					while (addr.hasMoreElements()) {
						InetAddress inet = addr.nextElement();

						if (!inet.isLoopbackAddress()) {
							if (inet instanceof Inet4Address) {
								String address = inet.getHostAddress();
								if (!allAdresses.contains(address)) {
									allAdresses.add(address);

								}
							} else if (inet instanceof Inet6Address) {
								// for ipV6 in futur version
							}
						}
					}
				}
			}
		} catch (SocketException ex) {
			ExceptionTool.showError(ex,
					"Probl\u00E8me d'acces à la carte r\u00E9seaux");
		} catch (Exception ex) {
			ExceptionTool.showError(ex,
					"Probl\u00E8me d'acces à la carte r\u00E9seaux");
		}
		return allAdresses;
	}

}
