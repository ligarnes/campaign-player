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
package net.alteiar.campaign.player.gui.connection;

import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.panel.PanelSelectColor;
import net.alteiar.shared.ExceptionTool;

public class PanelCreateGame extends PanelStartGameDialog {

	private static final long serialVersionUID = 1L;

	private JTextField gameNameTextField;

	private JTextField pseudoTextField;
	private PanelSelectColor playerColorSelector;

	private DefaultComboBoxModel<String> localIpComboBoxModel;
	private JComboBox<String> localIpComboBox;
	private JTextField portTextField;

	public PanelCreateGame(StartGameDialog startGameDialog,
			PanelStartGameDialog previous) {
		super(startGameDialog, previous);
		initGui();
	}

	@Override
	protected PanelStartGameDialog getNext() {
		return null;
	}

	private final void initGui() {

		GlobalProperties globalProp = Helpers.getGlobalProperties();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize all the component of the panel
		gameNameTextField = new JTextField(10);

		pseudoTextField = new JTextField(10);
		playerColorSelector = new PanelSelectColor();

		localIpComboBoxModel = new DefaultComboBoxModel<String>();
		localIpComboBox = new JComboBox<String>(localIpComboBoxModel);
		portTextField = new JTextField(5);

		// Set some values to the values stocked in global properties
		pseudoTextField.setText(globalProp.getCreatePseudo());
		playerColorSelector.setColor(globalProp.getCreateColor());
		portTextField.setText(globalProp.getCreatePort());

		// Build inner panel

		// Game Info Panel
		JPanel gamePanel = new JPanel(new FlowLayout());
		gamePanel
				.setBorder(BorderFactory.createTitledBorder("Nouvelle partie"));
		JPanel gameNamePanel = new JPanel(new FlowLayout());
		gameNamePanel.add(new JLabel("Nom de la partie:"));
		gameNamePanel.add(gameNameTextField);
		gamePanel.add(gameNamePanel);

		// Player Info Panel
		JPanel identityPanel = new JPanel(new FlowLayout());
		identityPanel.setBorder(BorderFactory
				.createTitledBorder("Votre identit\u00E9"));
		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("Pseudo:"));
		pseudo.add(pseudoTextField);
		identityPanel.add(pseudo);

		identityPanel.add(playerColorSelector);

		// Server Info Panel
		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
		serverPanel.setBorder(BorderFactory.createTitledBorder("Connection"));

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
									localIpComboBoxModel.addElement(address);
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

		if (allAdresses.contains(globalProp.getCreateIpLocal())) {
			localIpComboBoxModel.setSelectedItem(globalProp.getCreateIpLocal());
		}

		JPanel localAddressIpPanel = new JPanel(new FlowLayout());
		localAddressIpPanel.add(new JLabel("Adresse ip local"));
		localAddressIpPanel.add(localIpComboBox);

		JPanel portLabel = new JPanel(new FlowLayout());
		portLabel.add(new JLabel("Port"));
		portLabel.add(portTextField);

		serverPanel.add(localAddressIpPanel);
		serverPanel.add(portLabel);

		// Button Panel
		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton createButton = new JButton("Connection");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				create();
			}
		});

		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				previousState();
			}
		});

		buttonPanel.add(cancelButton);
		buttonPanel.add(createButton);

		// Add all the panel to this panel
		this.add(gamePanel);
		this.add(identityPanel);
		this.add(serverPanel);
		this.add(buttonPanel);
	}

	public String getPseudo() {
		return pseudoTextField.getText();
	}

	public Color getPlayerColor() {
		return playerColorSelector.getColor();
	}

	public String getGameName() {
		return gameNameTextField.getText();
	}

	public String getLocalAdressIP() {
		return (String) localIpComboBox.getSelectedItem();
	}

	public String getServerAddressIp() {
		return getLocalAdressIP();
	}

	public String getPort() {
		return portTextField.getText();
	}

	public void create() {
		GlobalProperties globalProp = Helpers.getGlobalProperties();

		// Create the game
		CampaignClient.startNewCampaignServer(getServerAddressIp(), getPort(),
				getGameName());

		// Create one player NOTE: here we force the player who
		// creates the game to be the Game Master
		CampaignClient.getInstance().createPlayer(getPseudo(), true,
				getPlayerColor());

		globalProp.setCreateColor(getPlayerColor());
		globalProp.setCreatePseudo(getPseudo());
		globalProp.setCreateIpLocal(getLocalAdressIP());
		globalProp.setCreatePort(getPort());
		globalProp.setCreateIpServer(getServerAddressIp());
		try {
			globalProp.save();
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}

		nextState();
	}
}
