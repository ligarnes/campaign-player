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
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.alteiar.CampaignClient;
import net.alteiar.campaign.player.GlobalProperties;
import net.alteiar.campaign.player.Helpers;
import net.alteiar.shared.ExceptionTool;

public class PanelCreateGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_PLAYER_COLOR = Color.BLUE;

	JPanel previous;
	StartGameDialog startGameDialog;

	private JTextField gameNameTextField;

	private JTextField pseudoTextField;
	private JButton playerColorButton;
	private Color playerColor;
	private JCheckBox isMj;

	private DefaultComboBoxModel<String> localIpComboBoxModel;
	private JComboBox<String> localIpComboBox;
	private JTextField portTextField;

	public PanelCreateGame(StartGameDialog startGameDialog, JPanel previous) {
		this.previous = previous;
		this.startGameDialog = startGameDialog;
		this.playerColor = DEFAULT_PLAYER_COLOR;

		initGui();
	}

	private final void initGui() {

		GlobalProperties globalProp = Helpers.getGlobalProperties();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize all the component of the panel
		gameNameTextField = new JTextField(10);

		pseudoTextField = new JTextField(10);

		localIpComboBoxModel = new DefaultComboBoxModel<String>();
		localIpComboBox = new JComboBox<String>(localIpComboBoxModel);
		portTextField = new JTextField(5);

		isMj = new JCheckBox("MJ");

		// Set some values to the values stocked in global properties

		pseudoTextField.setText(globalProp.getPseudo());
		portTextField.setText(globalProp.getPort());
		isMj.setSelected(globalProp.isMj());

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
				.createTitledBorder("Votre identité"));
		JPanel pseudo = new JPanel(new FlowLayout());
		pseudo.add(new JLabel("Pseudo:"));
		pseudo.add(pseudoTextField);
		playerColorButton = new JButton("Couleur");
		// playerColorButton.setForeground(Color.BLUE);
		playerColorButton.setBackground(playerColor);
		playerColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Color chosenColor = JColorChooser
						.showDialog(PanelCreateGame.this,
								"Choisissez la couleur de votre personnage",
								Color.BLUE);
				if (chosenColor != null) {
					// playerColorButton.setForeground(playerColor);
					playerColor = chosenColor;
					playerColorButton.setBackground(chosenColor);
				}
			}
		});
		identityPanel.add(pseudo);
		identityPanel.add(playerColorButton);
		identityPanel.add(isMj);

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
			ExceptionTool.showError(ex, "Problème d'acces à la carte réseaux");
		} catch (Exception ex) {
			ExceptionTool.showError(ex, "Problème d'acces à la carte réseaux");
		}

		if (allAdresses.contains(globalProp.getIpLocal())) {
			localIpComboBoxModel.setSelectedItem(globalProp.getIpLocal());
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
				PanelCreateGame.this.startGameDialog.changeState(previous);
			}
		});

		buttonPanel.add(createButton);
		buttonPanel.add(cancelButton);

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
		return playerColor;
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

	public Boolean isMj() {
		return isMj.isSelected();
	}

	public void create() {
		GlobalProperties globalProp = Helpers.getGlobalProperties();

		// Create the game
		CampaignClient.startNewCampaignServer(getServerAddressIp(), getPort(),
				getGameName());

		// Create one player
		CampaignClient.getInstance().createPlayer(getPseudo(), isMj(),
				getPlayerColor());

		// TODO : add color to the properties?
		globalProp.setPseudo(getPseudo());
		globalProp.setIsMj(isMj());
		globalProp.setIpLocal(getLocalAdressIP());
		globalProp.setPort(getPort());
		globalProp.setIpServer(getServerAddressIp());
		globalProp.setIsServer(true);
		try {
			globalProp.save();
		} catch (IOException ex) {
			ExceptionTool.showError(ex);
		}

		startGameDialog.startApplication();
	}
}
