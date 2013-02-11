/**
 * 
 * Copyright (C) 2011 Cody Stoutenburg. All rights reserved.
 *
 *       This program is free software; you can redistribute it and/or
 *       modify it under the terms of the GNU Lesser General Public License
 *       as published by the Free Software Foundation; either version 2.1
 *       of the License, or (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program; if not, write to the Free Software
 *       Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package net.alteiar.campaign.player.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.ExceptionTool;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.campaign.player.gui.tools.PanelAlwaysValidOkCancel;

public class PanelConnexion extends PanelAlwaysValidOkCancel {
	private static final long serialVersionUID = 6892353388010135512L;

	private final JTextField pseudoTextField;
	private final JCheckBox isMj;

	private final DefaultComboBoxModel model;
	private final JComboBox comboboxLocalIp;
	private final JTextField addressIpServer;
	private final JTextField port;

	private final JCheckBox isServer;

	public PanelConnexion() {
		pseudoTextField = new JTextField(10);

		model = new DefaultComboBoxModel();
		comboboxLocalIp = new JComboBox(model);
		addressIpServer = new JTextField(10);
		port = new JTextField(5);

		isServer = new JCheckBox("créer le serveur ?");

		isMj = new JCheckBox("MJ");
		initGui();

	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		pseudoTextField.setText(globalProp.getPseudo());

		addressIpServer.setText(globalProp.getIpServer());
		port.setText(globalProp.getPort());

		isMj.setSelected(globalProp.isMj());
		isServer.setSelected(globalProp.isServer());
		isServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isServerChanged();
			}
		});

		// pseudo
		JPanel identite = new JPanel(new FlowLayout());
		identite.setBorder(BorderFactory.createTitledBorder("Votre identité"));

		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("votre pseudo:"));
		pseudo.add(pseudoTextField);

		identite.add(pseudo);
		identite.add(isMj);

		JPanel server = new JPanel();
		server.setLayout(new BoxLayout(server, BoxLayout.Y_AXIS));
		server.setBorder(BorderFactory.createTitledBorder("Connection"));

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
									model.addElement(address);
								}
							} else if (inet instanceof Inet6Address) {
								// for ipV6 in futur version
							}
						}
					}
				}
			}
		} catch (SocketException ex) {
			ExceptionTool.showError(ex, "Problème d'acces à la carte réseaux");
		} catch (Exception ex) {
			ExceptionTool.showError(ex, "Problème d'acces à la carte réseaux");
		}

		if (allAdresses.contains(globalProp.getIpLocal())) {
			model.setSelectedItem(globalProp.getIpLocal());
		}

		JPanel localAddressIpPanel = new JPanel(new FlowLayout());
		localAddressIpPanel.add(new JLabel("Adresse ip local"));
		localAddressIpPanel.add(comboboxLocalIp);

		JPanel addressIpPanel = new JPanel(new FlowLayout());
		addressIpPanel.add(new JLabel("Adresse ip du serveur"));
		addressIpPanel.add(addressIpServer);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel("Port"));
		portLabel.add(port);

		server.add(localAddressIpPanel);
		server.add(addressIpPanel);
		server.add(portLabel);

		this.add(identite);
		this.add(server);
		this.add(isServer);

		isServerChanged();
	}

	private void isServerChanged() {
		addressIpServer.setEnabled(!isServer());
		this.revalidate();
	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	public String getLocalAdressIP() {
		return (String) comboboxLocalIp.getSelectedItem();
	}

	public String getServerAddressIp() {
		String address = addressIpServer.getText();
		if (isServer()) {
			address = getLocalAdressIP();
		}
		return address;
	}

	public String getPort() {
		return port.getText();
	}

	public Boolean isServer() {
		return isServer.isSelected();
	}

	public Boolean isMj() {
		return isMj.isSelected();
	}
}
