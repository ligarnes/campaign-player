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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.shared.ExceptionTool;

public class PanelLoad extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String PATH = "./ressources/sauvegarde/";

	JPanel previous;

	MainPanelStartGame mainPanelStartGame;

	JList<String> savedGameList;

	private final DefaultComboBoxModel<String> model;
	private final JComboBox<String> comboboxServerIp;
	private final JTextField port;

	public PanelLoad(MainPanelStartGame mainPanelStartGame, JPanel previous) {
		this.previous = previous;
		this.mainPanelStartGame = mainPanelStartGame;

		model = new DefaultComboBoxModel<String>();
		comboboxServerIp = new JComboBox<String>(model);
		port = new JTextField(5);

		initGui();

	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GlobalProperties globalProp = Helpers.getGlobalProperties();

		port.setText(globalProp.getPort());

		JPanel server = new JPanel();
		server.setLayout(new BoxLayout(server, BoxLayout.Y_AXIS));
		server.setBorder(BorderFactory.createTitledBorder("Serveur"));

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

		JPanel serverAddressIpPanel = new JPanel(new FlowLayout());
		serverAddressIpPanel.add(new JLabel("Adresse ip"));
		serverAddressIpPanel.add(comboboxServerIp);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel("Port"));
		portLabel.add(port);

		server.add(serverAddressIpPanel);
		server.add(portLabel);

		this.add(server);

		Vector<String> savedGames = getSavedGames();

		savedGameList = new JList<String>(savedGames);

		savedGameList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		savedGameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		savedGameList.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(savedGameList);
		listScroller.setPreferredSize(new Dimension(250, 80));

		this.add(listScroller);

		JButton createButton = new JButton("Charger");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				load(PATH + PanelLoad.this.savedGameList.getSelectedValue());

			}
		});
		this.add(createButton);

		JButton loadButton = new JButton("Annuler");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelLoad.this.mainPanelStartGame.changeState(previous);
			}
		});
		this.add(loadButton);
	}

	public String getServerAddressIp() {
		return (String) comboboxServerIp.getSelectedItem();
	}

	public String getPort() {
		return port.getText();
	}

	public void load(String campaign) {

		String address = getServerAddressIp();
		String port = getPort();

		CampaignClient.loadCampaignServer(address, address, port, campaign);

		PanelLoad.this.mainPanelStartGame.changeState(new PanelChoosePlayer(
				mainPanelStartGame, this));
	}

	public Vector<String> getSavedGames() {

		Vector<String> savedGames = new Vector<String>();

		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				savedGames.add(listOfFiles[i].getName());
			}
		}
		return savedGames;
	}
}
