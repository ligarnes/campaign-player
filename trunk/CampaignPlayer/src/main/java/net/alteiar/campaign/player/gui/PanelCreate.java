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
import java.io.IOException;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.shared.ExceptionTool;

public class PanelCreate extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JPanel previous;
	
	MainPanelStartGame mainPanelStartGame;

	//TODO: ajouter un text field pour le nom
	
	private final JTextField pseudoTextField;
	private final JCheckBox isMj;

	private final DefaultComboBoxModel<String> model;
	private final JComboBox<String> comboboxLocalIp;
	private final JTextField port;

	public PanelCreate(MainPanelStartGame mainPanelStartGame, JPanel previous) {
		this.previous = previous;
		this.mainPanelStartGame = mainPanelStartGame;
		
		pseudoTextField = new JTextField(10);

		model = new DefaultComboBoxModel<String>();
		comboboxLocalIp = new JComboBox<String>(model);
		port = new JTextField(5);

		isMj = new JCheckBox("MJ");
		initGui();

	}

	private final void initGui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GlobalProperties globalProp = Helpers.getGlobalProperties();
		pseudoTextField.setText(globalProp.getPseudo());

		port.setText(globalProp.getPort());

		isMj.setSelected(globalProp.isMj());

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

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel("Port"));
		portLabel.add(port);

		server.add(localAddressIpPanel);
		server.add(portLabel);

		this.add(identite);
		this.add(server);
		
		JButton createButton = new JButton("Connexion");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				create();
				
			}
		});
		this.add(createButton);
		
		JButton loadButton = new JButton("Annuler");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelCreate.this.mainPanelStartGame.changeState(previous);
			}
		});
		this.add(loadButton);
	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	public String getLocalAdressIP() {
		return (String) comboboxLocalIp.getSelectedItem();
	}

	public String getServerAddressIp() {
		return getLocalAdressIP();
	}

	public String getPort() {
		return port.getText();
	}

	public Boolean isMj() {
		return isMj.isSelected();
	}
	
	public void create(){
		
			GlobalProperties globalProp = Helpers.getGlobalProperties();
			String localAdress = getLocalAdressIP();
			String address = getServerAddressIp();
			String port = getPort();

			String name = getPseudo();
			Boolean isMj = isMj();


			CampaignClient.startNewCampaignServer(localAdress, address,
						port, "campaign path");

			CampaignClient.getInstance().createPlayer(name, isMj);

			globalProp.setPseudo(name);
			globalProp.setIsMj(isMj);
			globalProp.setIpLocal(localAdress);
			globalProp.setPort(port);
			globalProp.setIpServer(address);
			globalProp.setIsServer(true);
			try {
				globalProp.save();
			} catch (IOException ex) {
				ExceptionTool.showError(ex);
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MainFrame.FRAME.initComponent();
					// CampaignClient.createClientCampaign(5, MainFrame.FRAME);
					MainFrame.FRAME.setVisible(true);
				}
			});
	}
}
